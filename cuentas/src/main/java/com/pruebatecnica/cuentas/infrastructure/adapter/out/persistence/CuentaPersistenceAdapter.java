package com.pruebatecnica.cuentas.infrastructure.adapter.out.persistence;

import com.pruebatecnica.cuentas.application.port.out.CuentaRepositoryPort;
import com.pruebatecnica.cuentas.domain.model.Cuenta;
import com.pruebatecnica.cuentas.infrastructure.adapter.out.persistence.entity.CuentaEntity;
import com.pruebatecnica.cuentas.infrastructure.adapter.out.persistence.mapper.CuentaMapper;
import com.pruebatecnica.cuentas.infrastructure.adapter.out.persistence.repository.CuentaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CuentaPersistenceAdapter implements CuentaRepositoryPort {

    private final CuentaJpaRepository cuentaRepository;
    private final CuentaMapper cuentaMapper;

    @Override
    public Cuenta save(Cuenta cuenta) {
        CuentaEntity entity = cuentaMapper.toEntity(cuenta);
        // Verificamos el numero de cuenta
        cuentaRepository.findByNumeroCuenta(cuenta.getNumeroCuenta())
                .ifPresent(existing -> entity.setId(existing.getId()));

        CuentaEntity saved = cuentaRepository.save(entity);
        return cuentaMapper.toDomain(saved);
    }

    @Override
    public Optional<Cuenta> findByNumeroCuenta(String numeroCuenta) {
        return cuentaRepository.findByNumeroCuenta(numeroCuenta).map(cuentaMapper::toDomain);
    }

    @Override
    public List<Cuenta> findAll() {
        return cuentaMapper.toDomainList(cuentaRepository.findAll());
    }

    @Override
    public List<Cuenta> findByClienteId(String clienteId) {
        return cuentaMapper.toDomainList(cuentaRepository.findByClienteId(clienteId));
    }

    @Override
    public void deleteByNumeroCuenta(String numeroCuenta) {
        cuentaRepository.deleteByNumeroCuenta(numeroCuenta);
    }

    @Override
    public boolean existsByNumeroCuenta(String numeroCuenta) {
        return cuentaRepository.existsByNumeroCuenta(numeroCuenta);
    }
}
