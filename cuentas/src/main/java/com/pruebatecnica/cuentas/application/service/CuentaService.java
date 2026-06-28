package com.pruebatecnica.cuentas.application.service;

import com.pruebatecnica.cuentas.application.port.in.CuentaUseCase;
import com.pruebatecnica.cuentas.application.port.out.CuentaRepositoryPort;
import com.pruebatecnica.cuentas.application.port.out.MovimientoRepositoryPort;
import com.pruebatecnica.cuentas.domain.exception.BusinessException;
import com.pruebatecnica.cuentas.domain.exception.ResourceNotFoundException;
import com.pruebatecnica.cuentas.domain.model.Cuenta;
import com.pruebatecnica.cuentas.domain.model.Movimiento;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CuentaService implements CuentaUseCase {

    private final CuentaRepositoryPort cuentaRepositoryPort;
    private final MovimientoRepositoryPort movimientoRepositoryPort;

    @Override
    @Transactional
    public Cuenta createCuenta(Cuenta cuenta) {
        if (cuentaRepositoryPort.existsByNumeroCuenta(cuenta.getNumeroCuenta())) {
            throw new BusinessException("Ya existe una cuenta con el número: " + cuenta.getNumeroCuenta());
        }
        return cuentaRepositoryPort.save(cuenta);
    }

    @Override
    @Transactional
    public Cuenta updateCuenta(String numeroCuenta, Cuenta cuenta) {
        Cuenta existingCuenta = getCuentaByNumeroCuenta(numeroCuenta);

        existingCuenta.setTipoCuenta(cuenta.getTipoCuenta());

        if (cuenta.getEstado() != null && !cuenta.getEstado() && existingCuenta.getEstado().booleanValue()) {
            BigDecimal saldoActual = movimientoRepositoryPort.findFirstByNumeroCuentaOrderByFechaDesc(numeroCuenta)
                    .map(Movimiento::getSaldo)
                    .orElse(existingCuenta.getSaldoInicial());

            if (saldoActual.compareTo(BigDecimal.ZERO) != 0) {
                throw new BusinessException(
                        "No se puede desactivar la cuenta porque tiene un saldo diferente de cero.");
            }
        }

        if (cuenta.getEstado() != null) {
            existingCuenta.setEstado(cuenta.getEstado());
        }

        return cuentaRepositoryPort.save(existingCuenta);
    }

    @Override
    @Transactional(readOnly = true)
    public Cuenta getCuentaByNumeroCuenta(String numeroCuenta) {
        return cuentaRepositoryPort.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con número: " + numeroCuenta));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cuenta> getAllCuentas() {
        return cuentaRepositoryPort.findAll();
    }
}
