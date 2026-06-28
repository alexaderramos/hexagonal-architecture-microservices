package com.pruebatecnica.cuentas.infrastructure.adapter.out.rest;

import com.pruebatecnica.cuentas.application.port.out.ClienteServicePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.concurrent.CompletableFuture;

@Component
public class ClienteExternalAdapter implements ClienteServicePort {

    private final RestClient restClient;

    public ClienteExternalAdapter(@Value("${clientes.service.url:http://localhost:8080}") String clientesServiceUrl) {
        this.restClient = RestClient.builder().baseUrl(clientesServiceUrl).build();
    }

    @Override
    @Async
    public CompletableFuture<String> getClienteNombreAsync(String clienteId) {
        try {
            ClienteDto cliente = restClient.get()
                    .uri("/clientes/{clienteId}", clienteId)
                    .retrieve()
                    .body(ClienteDto.class);
            return CompletableFuture.completedFuture(cliente != null ? cliente.getNombre() : "Cliente Desconocido");
        } catch (Exception e) {
            return CompletableFuture.completedFuture("Cliente Desconocido (" + clienteId + ")");
        }
    }
}
