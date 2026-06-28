package com.pruebatecnica.cuentas.application.port.in;

import com.pruebatecnica.cuentas.domain.model.Movimiento;
import java.util.List;

public interface MovimientoUseCase {
    Movimiento createMovimiento(Movimiento movimiento);
    Movimiento updateMovimiento(Long id, Movimiento movimiento);
    Movimiento getMovimientoById(Long id);
    List<Movimiento> getAllMovimientos();
}
