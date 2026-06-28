package com.pruebatecnica.cuentas.infrastructure.adapter.out.persistence.mapper;

import com.pruebatecnica.cuentas.domain.model.Movimiento;
import com.pruebatecnica.cuentas.infrastructure.adapter.out.persistence.entity.MovimientoEntity;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MovimientoMapper {
    public Movimiento toDomain(MovimientoEntity entity) {
        if (entity == null)
            return null;
        return Movimiento.builder()
                .id(entity.getId())
                .fecha(entity.getFecha())
                .tipoMovimiento(entity.getTipoMovimiento())
                .valor(entity.getValor())
                .saldo(entity.getSaldo())
                .saldoInicial(entity.getSaldoInicial())
                .numeroCuenta(entity.getNumeroCuenta())
                .estado(entity.getEstado())
                .build();
    }

    public MovimientoEntity toEntity(Movimiento domain) {
        if (domain == null)
            return null;
        return MovimientoEntity.builder()
                .id(domain.getId())
                .fecha(domain.getFecha())
                .tipoMovimiento(domain.getTipoMovimiento())
                .valor(domain.getValor())
                .saldo(domain.getSaldo())
                .saldoInicial(domain.getSaldoInicial())
                .numeroCuenta(domain.getNumeroCuenta())
                .estado(domain.getEstado())
                .build();
    }

    public List<Movimiento> toDomainList(List<MovimientoEntity> entities) {
        return entities.stream().map(this::toDomain).toList();
    }
}
