package com.pruebatecnica.cuentas.application.port.out;

import com.pruebatecnica.cuentas.domain.model.Movimiento;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MovimientoRepositoryPort {
    Movimiento save(Movimiento movimiento);
    Optional<Movimiento> findById(Long id);
    List<Movimiento> findAll();
    List<Movimiento> findByNumeroCuentaAndFechaBetween(String numeroCuenta, LocalDateTime startDate, LocalDateTime endDate);
    Optional<Movimiento> findFirstByNumeroCuentaOrderByFechaDesc(String numeroCuenta);
}
