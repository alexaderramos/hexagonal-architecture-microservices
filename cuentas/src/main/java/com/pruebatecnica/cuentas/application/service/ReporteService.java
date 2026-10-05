package com.pruebatecnica.cuentas.application.service;

import com.pruebatecnica.cuentas.application.port.in.ReporteUseCase;
import com.pruebatecnica.cuentas.application.port.out.ClienteServicePort;
import com.pruebatecnica.cuentas.application.port.out.CuentaRepositoryPort;
import com.pruebatecnica.cuentas.application.port.out.MovimientoRepositoryPort;
import com.pruebatecnica.cuentas.domain.model.ReporteRow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ReporteService implements ReporteUseCase {

    private final CuentaRepositoryPort cuentaRepositoryPort;
    private final MovimientoRepositoryPort movimientoRepositoryPort;
    private final ClienteServicePort clienteServicePort;

    @Override
    public Flux<ReporteRow> generarReporte(String clienteId, LocalDate fechaInicio, LocalDate fechaFin) {

        LocalDateTime start = fechaInicio.atStartOfDay();
        LocalDateTime end = fechaFin.atTime(23, 59, 59);

        // 1. Obtener el nombre del cliente asíncronamente
        Mono<String> clienteNombreMono = clienteServicePort.getClienteNombreAsync(clienteId);

        // 2. Obtener cuentas bloqueantes envueltas en un scheduler asíncrono
        Mono<java.util.List<com.pruebatecnica.cuentas.domain.model.Cuenta>> cuentasMono = Mono.fromCallable(() -> 
                cuentaRepositoryPort.findByClienteId(clienteId)
        ).subscribeOn(Schedulers.boundedElastic());

        // 3. Orquestación funcional
        return clienteNombreMono.flatMapMany(nombre ->
                cuentasMono.flatMapMany(Flux::fromIterable)
                        .flatMap(cuenta ->
                                // Buscar movimientos bloqueantes envueltos en un scheduler
                                Mono.fromCallable(() -> movimientoRepositoryPort.findByNumeroCuentaAndFechaBetween(cuenta.getNumeroCuenta(), start, end))
                                        .subscribeOn(Schedulers.boundedElastic())
                                        .flatMapMany(Flux::fromIterable)
                                        .map(mov -> ReporteRow.builder()
                                                .fecha(mov.getFecha().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                                                .cliente(nombre)
                                                .numeroCuenta(cuenta.getNumeroCuenta())
                                                .tipo(cuenta.getTipoCuenta().name())
                                                .saldoInicial(mov.getSaldoInicial())
                                                .estado(cuenta.getEstado())
                                                .movimiento(mov.getValor())
                                                .saldoDisponible(mov.getSaldo())
                                                .build()
                                        )
                        )
        );
    }
}
