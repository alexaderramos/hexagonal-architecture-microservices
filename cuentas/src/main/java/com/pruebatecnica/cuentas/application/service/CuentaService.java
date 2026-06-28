package com.pruebatecnica.cuentas.application.service;

import com.pruebatecnica.cuentas.application.port.in.CuentaUseCase;
import com.pruebatecnica.cuentas.application.port.out.CuentaRepositoryPort;
import com.pruebatecnica.cuentas.domain.exception.BusinessException;
import com.pruebatecnica.cuentas.domain.exception.ResourceNotFoundException;
import com.pruebatecnica.cuentas.domain.model.Cuenta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CuentaService implements CuentaUseCase {

    private final CuentaRepositoryPort cuentaRepositoryPort;

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
        existingCuenta.setEstado(cuenta.getEstado());
        
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

    @Override
    @Transactional
    public void deleteCuenta(String numeroCuenta) {
        getCuentaByNumeroCuenta(numeroCuenta); // Verify exists
        cuentaRepositoryPort.deleteByNumeroCuenta(numeroCuenta);
    }
}
