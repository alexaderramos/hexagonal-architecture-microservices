package com.pruebatecnica.cuentas.application.service;

import com.pruebatecnica.cuentas.application.port.in.ReporteUseCase;
import com.pruebatecnica.cuentas.application.port.out.ClienteServicePort;
import com.pruebatecnica.cuentas.application.port.out.CuentaRepositoryPort;
import com.pruebatecnica.cuentas.application.port.out.MovimientoRepositoryPort;
import com.pruebatecnica.cuentas.domain.model.Cuenta;
import com.pruebatecnica.cuentas.domain.model.Movimiento;
import com.pruebatecnica.cuentas.domain.model.ReporteRow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ReporteService implements ReporteUseCase {

    private final CuentaRepositoryPort cuentaRepositoryPort;
    private final MovimientoRepositoryPort movimientoRepositoryPort;
    private final ClienteServicePort clienteServicePort;

    @Override
    @Transactional(readOnly = true)
    public List<ReporteRow> generarReporte(String clienteId, LocalDate fechaInicio, LocalDate fechaFin) {

        CompletableFuture<String> clienteNombreFuture = clienteServicePort.getClienteNombreAsync(clienteId);

        List<Cuenta> cuentas = cuentaRepositoryPort.findByClienteId(clienteId);

        List<ReporteRow> reporte = new ArrayList<>();
        LocalDateTime start = fechaInicio.atStartOfDay();
        LocalDateTime end = fechaFin.atTime(23, 59, 59);

        for (Cuenta cuenta : cuentas) {
            List<Movimiento> movimientos = movimientoRepositoryPort
                    .findByNumeroCuentaAndFechaBetween(cuenta.getNumeroCuenta(), start, end);

            for (Movimiento mov : movimientos) {
                ReporteRow row = ReporteRow.builder()
                        .fecha(mov.getFecha().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                        .numeroCuenta(cuenta.getNumeroCuenta())
                        .tipo(cuenta.getTipoCuenta().name())
                        .saldoInicial(mov.getSaldoInicial())
                        .estado(cuenta.getEstado())
                        .movimiento(mov.getValor())
                        .saldoDisponible(mov.getSaldo())
                        .build();
                reporte.add(row);
            }
        }

        String clienteNombre;
        try {
            clienteNombre = clienteNombreFuture.join();
        } catch (Exception e) {
            clienteNombre = "Cliente Desconocido (" + clienteId + ")";
        }

        for (ReporteRow row : reporte) {
            row.setCliente(clienteNombre);
        }

        return reporte;
    }
}
