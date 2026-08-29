package com.pruebatecnica.cuentas.application.port.out;

import com.pruebatecnica.cuentas.domain.model.Movimiento;

public interface MovimientoEventPublisherPort {
    void publishMovimientoCreadoEvent(Movimiento movimiento, String clienteId);
}
