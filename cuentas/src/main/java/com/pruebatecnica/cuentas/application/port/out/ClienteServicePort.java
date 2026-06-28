package com.pruebatecnica.cuentas.application.port.out;

import java.util.concurrent.CompletableFuture;

public interface ClienteServicePort {
    CompletableFuture<String> getClienteNombreAsync(String clienteId);
}
