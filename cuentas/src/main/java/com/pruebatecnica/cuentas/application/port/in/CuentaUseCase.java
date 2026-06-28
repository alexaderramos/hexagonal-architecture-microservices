package com.pruebatecnica.cuentas.application.port.in;

import com.pruebatecnica.cuentas.domain.model.Cuenta;
import java.util.List;

public interface CuentaUseCase {
    Cuenta createCuenta(Cuenta cuenta);
    Cuenta updateCuenta(String numeroCuenta, Cuenta cuenta);
    Cuenta getCuentaByNumeroCuenta(String numeroCuenta);
    List<Cuenta> getAllCuentas();
    void deleteCuenta(String numeroCuenta);
}
