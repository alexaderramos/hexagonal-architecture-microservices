package com.pruebatecnica.clientes.infrastructure.adapter.out.persistence;

import com.pruebatecnica.clientes.application.port.out.ClienteRepositoryPort;
import com.pruebatecnica.clientes.domain.model.Cliente;
import com.pruebatecnica.clientes.infrastructure.adapter.out.persistence.entity.ClienteEntity;
import com.pruebatecnica.clientes.infrastructure.adapter.out.persistence.mapper.ClienteMapper;
import com.pruebatecnica.clientes.infrastructure.adapter.out.persistence.repository.ClienteJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClientePersistenceAdapter implements ClienteRepositoryPort {

    private final ClienteJpaRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    @Override
    public Cliente save(Cliente cliente) {
        ClienteEntity entity = clienteMapper.toEntity(cliente);
        ClienteEntity savedEntity = clienteRepository.save(entity);
        return clienteMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Cliente> findByClienteId(String clienteId) {
        return clienteRepository.findByClienteId(clienteId)
                .map(clienteMapper::toDomain);
    }

    @Override
    public List<Cliente> findAll() {
        return clienteMapper.toDomainList(clienteRepository.findAll());
    }

    @Override
    public void deleteByClienteId(String clienteId) {
        clienteRepository.deleteByClienteId(clienteId);
    }
}
