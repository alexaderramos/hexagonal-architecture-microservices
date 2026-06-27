package com.pruebatecnica.clientes.application.port.out;

import com.pruebatecnica.clientes.domain.model.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteRepositoryPort {
    Cliente save(Cliente cliente);
    Optional<Cliente> findByClienteId(String clienteId);
    List<Cliente> findAll();
    void deleteByClienteId(String clienteId);
}
