package com.pruebatecnica.cuentas.infrastructure.adapter.out.persistence.mapper;

import com.pruebatecnica.cuentas.domain.model.Cuenta;
import com.pruebatecnica.cuentas.infrastructure.adapter.out.persistence.entity.CuentaEntity;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class CuentaMapper {
    public Cuenta toDomain(CuentaEntity entity) {
        if (entity == null)
            return null;
        return Cuenta.builder()
                .numeroCuenta(entity.getNumeroCuenta())
                .tipoCuenta(entity.getTipoCuenta())
                .saldoInicial(entity.getSaldoInicial())
                .estado(entity.getEstado())
                .clienteId(entity.getClienteId())
                .build();
    }

    public CuentaEntity toEntity(Cuenta domain) {
        if (domain == null)
            return null;
        return CuentaEntity.builder()
                .numeroCuenta(domain.getNumeroCuenta())
                .tipoCuenta(domain.getTipoCuenta())
                .saldoInicial(domain.getSaldoInicial())
                .estado(domain.getEstado())
                .clienteId(domain.getClienteId())
                .build();
    }

    public List<Cuenta> toDomainList(List<CuentaEntity> entities) {
        return entities.stream().map(this::toDomain).toList();
    }
}
