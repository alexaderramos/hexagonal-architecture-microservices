package com.pruebatecnica.clientes.infrastructure.adapter.out.persistence.repository;

import com.pruebatecnica.clientes.infrastructure.adapter.out.persistence.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteJpaRepository extends JpaRepository<ClienteEntity, Long> {
    Optional<ClienteEntity> findByClienteId(String clienteId);
    boolean existsByIdentificacion(String identificacion);
    void deleteByClienteId(String clienteId);
}
