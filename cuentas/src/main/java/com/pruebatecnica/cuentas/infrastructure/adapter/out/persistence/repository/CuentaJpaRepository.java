package com.pruebatecnica.cuentas.infrastructure.adapter.out.persistence.repository;

import com.pruebatecnica.cuentas.infrastructure.adapter.out.persistence.entity.CuentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuentaJpaRepository extends JpaRepository<CuentaEntity, Long> {
    Optional<CuentaEntity> findByNumeroCuenta(String numeroCuenta);
    boolean existsByNumeroCuenta(String numeroCuenta);
    void deleteByNumeroCuenta(String numeroCuenta);
    List<CuentaEntity> findByClienteId(String clienteId);
}
