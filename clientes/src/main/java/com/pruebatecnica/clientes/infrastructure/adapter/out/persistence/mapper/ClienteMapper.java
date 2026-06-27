package com.pruebatecnica.clientes.infrastructure.adapter.out.persistence.mapper;

import com.pruebatecnica.clientes.domain.model.Cliente;
import com.pruebatecnica.clientes.infrastructure.adapter.out.persistence.entity.ClienteEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClienteMapper {

    public Cliente toDomain(ClienteEntity entity) {
        if (entity == null) {
            return null;
        }
        return Cliente.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .genero(entity.getGenero())
                .edad(entity.getEdad())
                .identificacion(entity.getIdentificacion())
                .direccion(entity.getDireccion())
                .telefono(entity.getTelefono())
                .clienteId(entity.getClienteId())
                .contrasena(entity.getContrasena())
                .estado(entity.getEstado())
                .build();
    }

    public ClienteEntity toEntity(Cliente domain) {
        if (domain == null) {
            return null;
        }
        return ClienteEntity.builder()
                .id(domain.getId())
                .nombre(domain.getNombre())
                .genero(domain.getGenero())
                .edad(domain.getEdad())
                .identificacion(domain.getIdentificacion())
                .direccion(domain.getDireccion())
                .telefono(domain.getTelefono())
                .clienteId(domain.getClienteId())
                .contrasena(domain.getContrasena())
                .estado(domain.getEstado())
                .build();
    }

    public List<Cliente> toDomainList(List<ClienteEntity> entities) {
        return entities.stream().map(this::toDomain).collect(Collectors.toList());
    }
}
