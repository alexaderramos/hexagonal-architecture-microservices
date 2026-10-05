package com.pruebatecnica.cuentas.application.port.out;

import reactor.core.publisher.Mono;

public interface ClienteServicePort {
    Mono<String> getClienteNombreAsync(String clienteId);
}
