package com.pruebatecnica.clientes.application.port.in;

import com.pruebatecnica.clientes.domain.model.Cliente;
import java.util.List;

public interface ClienteUseCase {
    Cliente createCliente(Cliente cliente);
    Cliente updateCliente(String clienteId, Cliente cliente);
    Cliente getClienteById(String clienteId);
    List<Cliente> getAllClientes();
    void deleteCliente(String clienteId);
}
