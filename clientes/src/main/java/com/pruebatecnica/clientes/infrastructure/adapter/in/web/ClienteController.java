package com.pruebatecnica.clientes.infrastructure.adapter.in.web;

import com.pruebatecnica.clientes.application.port.in.ClienteUseCase;
import com.pruebatecnica.clientes.domain.model.Cliente;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteUseCase clienteUseCase;

    @PostMapping
    public ResponseEntity<Cliente> createCliente(@Valid @RequestBody Cliente cliente) {
        Cliente createdCliente = clienteUseCase.createCliente(cliente);
        return new ResponseEntity<>(createdCliente, HttpStatus.CREATED);
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<Cliente> getCliente(@PathVariable String clienteId) {
        Cliente cliente = clienteUseCase.getClienteById(clienteId);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClientes() {
        return ResponseEntity.ok(clienteUseCase.getAllClientes());
    }

    @PutMapping("/{clienteId}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable String clienteId, @Valid @RequestBody Cliente cliente) {
        Cliente updatedCliente = clienteUseCase.updateCliente(clienteId, cliente);
        return ResponseEntity.ok(updatedCliente);
    }

    @DeleteMapping("/{clienteId}")
    public ResponseEntity<Void> deleteCliente(@PathVariable String clienteId) {
        clienteUseCase.deleteCliente(clienteId);
        return ResponseEntity.noContent().build();
    }
}
