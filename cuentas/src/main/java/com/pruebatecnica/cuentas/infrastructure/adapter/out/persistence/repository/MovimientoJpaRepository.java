package com.pruebatecnica.cuentas.infrastructure.adapter.out.persistence.repository;

import com.pruebatecnica.cuentas.infrastructure.adapter.out.persistence.entity.MovimientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovimientoJpaRepository extends JpaRepository<MovimientoEntity, Long> {
    List<MovimientoEntity> findByNumeroCuentaAndFechaBetween(String numeroCuenta, LocalDateTime startDate, LocalDateTime endDate);
    Optional<MovimientoEntity> findFirstByNumeroCuentaOrderByFechaDesc(String numeroCuenta);
}
