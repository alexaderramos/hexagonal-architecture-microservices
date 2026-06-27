package com.pruebatecnica.clientes.application.service;

import com.pruebatecnica.clientes.application.port.in.ClienteUseCase;
import com.pruebatecnica.clientes.application.port.out.ClienteRepositoryPort;
import com.pruebatecnica.clientes.domain.exception.ResourceNotFoundException;
import com.pruebatecnica.clientes.domain.model.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService implements ClienteUseCase {

    private final ClienteRepositoryPort clienteRepositoryPort;

    @Override
    public Cliente createCliente(Cliente cliente) {
        return clienteRepositoryPort.save(cliente);
    }

    @Override
    public Cliente updateCliente(String clienteId, Cliente cliente) {
        Cliente existingCliente = getClienteById(clienteId);
        
        // Update fields
        existingCliente.setNombre(cliente.getNombre());
        existingCliente.setGenero(cliente.getGenero());
        existingCliente.setEdad(cliente.getEdad());
        existingCliente.setIdentificacion(cliente.getIdentificacion());
        existingCliente.setDireccion(cliente.getDireccion());
        existingCliente.setTelefono(cliente.getTelefono());
        existingCliente.setContrasena(cliente.getContrasena());
        existingCliente.setEstado(cliente.getEstado());
        
        return clienteRepositoryPort.save(existingCliente);
    }

    @Override
    public Cliente getClienteById(String clienteId) {
        return clienteRepositoryPort.findByClienteId(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + clienteId));
    }

    @Override
    public List<Cliente> getAllClientes() {
        return clienteRepositoryPort.findAll();
    }

    @Override
    public void deleteCliente(String clienteId) {
        // verify exists first
        getClienteById(clienteId);
        clienteRepositoryPort.deleteByClienteId(clienteId);
    }
}
