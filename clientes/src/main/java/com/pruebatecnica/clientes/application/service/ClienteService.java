package com.pruebatecnica.clientes.application.service;

import com.pruebatecnica.clientes.application.port.in.ClienteUseCase;
import com.pruebatecnica.clientes.application.port.out.ClienteRepositoryPort;
import com.pruebatecnica.clientes.domain.exception.BusinessException;
import com.pruebatecnica.clientes.domain.exception.ResourceNotFoundException;
import com.pruebatecnica.clientes.domain.model.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClienteService implements ClienteUseCase {

    private final ClienteRepositoryPort clienteRepositoryPort;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional
    public Cliente createCliente(Cliente cliente) {
        if (clienteRepositoryPort.existsByIdentificacion(cliente.getIdentificacion())) {
            throw new BusinessException("Ya existe un cliente con la identificación: " + cliente.getIdentificacion());
        }

        cliente.setClienteId(UUID.randomUUID().toString());
        if (cliente.getContrasena() != null) {
            cliente.setContrasena(passwordEncoder.encode(cliente.getContrasena()));
        }
        return clienteRepositoryPort.save(cliente);
    }

    @Override
    @Transactional
    public Cliente updateCliente(String clienteId, Cliente cliente) {
        Cliente existingCliente = getClienteById(clienteId);
        
        if (!existingCliente.getIdentificacion().equals(cliente.getIdentificacion()) 
                && clienteRepositoryPort.existsByIdentificacion(cliente.getIdentificacion())) {
            throw new BusinessException("Ya existe otro cliente con la identificación: " + cliente.getIdentificacion());
        }
        
        // Update fields
        existingCliente.setNombre(cliente.getNombre());
        existingCliente.setGenero(cliente.getGenero());
        existingCliente.setEdad(cliente.getEdad());
        existingCliente.setIdentificacion(cliente.getIdentificacion());
        existingCliente.setDireccion(cliente.getDireccion());
        existingCliente.setTelefono(cliente.getTelefono());
        if (cliente.getContrasena() != null && !cliente.getContrasena().isEmpty()) {
            existingCliente.setContrasena(passwordEncoder.encode(cliente.getContrasena()));
        }
        existingCliente.setEstado(cliente.getEstado());
        
        return clienteRepositoryPort.save(existingCliente);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente getClienteById(String clienteId) {
        return clienteRepositoryPort.findByClienteId(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + clienteId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> getAllClientes() {
        return clienteRepositoryPort.findAll();
    }

    @Override
    @Transactional
    public void deleteCliente(String clienteId) {
        // verify exists first
        getClienteById(clienteId);
        clienteRepositoryPort.deleteByClienteId(clienteId);
    }
}
