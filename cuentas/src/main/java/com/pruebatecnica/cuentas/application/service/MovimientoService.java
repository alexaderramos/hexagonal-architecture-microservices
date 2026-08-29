package com.pruebatecnica.cuentas.application.service;

import com.pruebatecnica.cuentas.application.port.in.MovimientoUseCase;
import com.pruebatecnica.cuentas.application.port.out.CuentaRepositoryPort;
import com.pruebatecnica.cuentas.application.port.out.MovimientoRepositoryPort;
import com.pruebatecnica.cuentas.domain.exception.BusinessException;
import com.pruebatecnica.cuentas.domain.exception.ResourceNotFoundException;
import com.pruebatecnica.cuentas.domain.model.Cuenta;
import com.pruebatecnica.cuentas.domain.model.Movimiento;
import com.pruebatecnica.cuentas.domain.model.TipoMovimiento;
import com.pruebatecnica.cuentas.application.port.out.MovimientoEventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimientoService implements MovimientoUseCase {

    private final MovimientoRepositoryPort movimientoRepositoryPort;
    private final CuentaRepositoryPort cuentaRepositoryPort;
    private final MovimientoEventPublisherPort movimientoEventPublisherPort;

    @Override
    @Transactional
    public Movimiento createMovimiento(Movimiento movimiento) {

        Cuenta cuenta = cuentaRepositoryPort.findByNumeroCuenta(movimiento.getNumeroCuenta())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cuenta no encontrada con número: " + movimiento.getNumeroCuenta()));

        BigDecimal saldoDisponible = movimientoRepositoryPort
                .findFirstByNumeroCuentaOrderByFechaDesc(cuenta.getNumeroCuenta())
                .map(Movimiento::getSaldo)
                .orElse(cuenta.getSaldoInicial());

        if (movimiento.getValor() == null || movimiento.getValor().compareTo(BigDecimal.ZERO) == 0) {
            throw new BusinessException("El valor del movimiento no puede ser cero.");
        }

        BigDecimal nuevoSaldo = saldoDisponible.add(movimiento.getValor());

        if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("Saldo no disponible");
        }

        movimiento.setSaldoInicial(saldoDisponible);
        movimiento.setSaldo(nuevoSaldo);
        if (movimiento.getFecha() == null) {
            movimiento.setFecha(LocalDateTime.now());
        }
        movimiento.setTipoMovimiento(
                movimiento.getValor().compareTo(BigDecimal.ZERO) > 0 ? TipoMovimiento.DEPOSITO : TipoMovimiento.RETIRO);

        movimiento.setEstado(true);

        Movimiento savedMovimiento = movimientoRepositoryPort.save(movimiento);
        
        // Publicar evento en Kafka
        movimientoEventPublisherPort.publishMovimientoCreadoEvent(savedMovimiento, cuenta.getClienteId());

        return savedMovimiento;
    }

    @Override
    @Transactional
    public Movimiento updateMovimiento(Long id, Movimiento movimiento) {
        Movimiento existing = getMovimientoById(id);

        if (movimiento.getFecha() != null) {
            existing.setFecha(movimiento.getFecha());
        }

        // Verificacion de estado de movimientos
        if (movimiento.getEstado() != null && !movimiento.getEstado() && existing.getEstado().booleanValue()) {
            existing.setEstado(false);

            // Se crea un registro reverso para mantener la integridad de datos
            Movimiento reverso = Movimiento.builder()
                    .fecha(LocalDateTime.now())
                    .valor(existing.getValor().negate())
                    .numeroCuenta(existing.getNumeroCuenta())
                    .estado(true)
                    .build();

            createMovimiento(reverso);
        }

        return movimientoRepositoryPort.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public Movimiento getMovimientoById(Long id) {
        return movimientoRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movimiento no encontrado con id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Movimiento> getAllMovimientos() {
        return movimientoRepositoryPort.findAll();
    }
}
