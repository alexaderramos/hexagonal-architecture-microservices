package com.pruebatecnica.cuentas.infrastructure.adapter.out.rest;

import com.pruebatecnica.cuentas.application.port.out.ClienteServicePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ClienteExternalAdapter implements ClienteServicePort {

    private final WebClient webClient;

    public ClienteExternalAdapter(@Value("${clientes.service.url:http://localhost:8080}") String clientesServiceUrl) {
        this.webClient = WebClient.builder().baseUrl(clientesServiceUrl).build();
    }

    @Override
    public Mono<String> getClienteNombreAsync(String clienteId) {
        return webClient.get()
                .uri("/clientes/{clienteId}", clienteId)
                .retrieve()
                .bodyToMono(ClienteDto.class)
                .map(ClienteDto::getNombre)
                .defaultIfEmpty("Cliente Desconocido")
                .onErrorReturn("Cliente Desconocido (" + clienteId + ")");
    }
}
