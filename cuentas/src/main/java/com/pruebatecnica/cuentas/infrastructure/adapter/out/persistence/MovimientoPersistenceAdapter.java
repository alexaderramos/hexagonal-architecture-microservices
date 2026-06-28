package com.pruebatecnica.cuentas.infrastructure.adapter.out.persistence;

import com.pruebatecnica.cuentas.application.port.out.MovimientoRepositoryPort;
import com.pruebatecnica.cuentas.domain.model.Movimiento;
import com.pruebatecnica.cuentas.infrastructure.adapter.out.persistence.entity.MovimientoEntity;
import com.pruebatecnica.cuentas.infrastructure.adapter.out.persistence.mapper.MovimientoMapper;
import com.pruebatecnica.cuentas.infrastructure.adapter.out.persistence.repository.MovimientoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MovimientoPersistenceAdapter implements MovimientoRepositoryPort {

    private final MovimientoJpaRepository movimientoRepository;
    private final MovimientoMapper movimientoMapper;

    @Override
    public Movimiento save(Movimiento movimiento) {
        MovimientoEntity entity = movimientoMapper.toEntity(movimiento);
        return movimientoMapper.toDomain(movimientoRepository.save(entity));
    }

    @Override
    public Optional<Movimiento> findById(Long id) {
        return movimientoRepository.findById(id).map(movimientoMapper::toDomain);
    }

    @Override
    public List<Movimiento> findAll() {
        return movimientoMapper.toDomainList(movimientoRepository.findAll());
    }

    @Override
    public List<Movimiento> findByNumeroCuentaAndFechaBetween(String numeroCuenta, LocalDateTime startDate, LocalDateTime endDate) {
        return movimientoMapper.toDomainList(movimientoRepository.findByNumeroCuentaAndFechaBetween(numeroCuenta, startDate, endDate));
    }

    @Override
    public Optional<Movimiento> findFirstByNumeroCuentaOrderByFechaDesc(String numeroCuenta) {
        return movimientoRepository.findFirstByNumeroCuentaOrderByFechaDesc(numeroCuenta).map(movimientoMapper::toDomain);
    }
}
