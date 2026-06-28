package com.pruebatecnica.cuentas.application.port.out;

import com.pruebatecnica.cuentas.domain.model.Cuenta;
import java.util.List;
import java.util.Optional;

public interface CuentaRepositoryPort {
    Cuenta save(Cuenta cuenta);
    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);
    List<Cuenta> findAll();
    List<Cuenta> findByClienteId(String clienteId);
    void deleteByNumeroCuenta(String numeroCuenta);
    boolean existsByNumeroCuenta(String numeroCuenta);
}
