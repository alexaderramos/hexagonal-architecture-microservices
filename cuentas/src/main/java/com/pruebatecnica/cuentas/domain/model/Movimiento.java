package com.pruebatecnica.cuentas.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movimiento {
    private Long id;
    private LocalDateTime fecha;
    private TipoMovimiento tipoMovimiento;
    private BigDecimal valor;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal saldo;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal saldoInicial;
    private String numeroCuenta;
    @Builder.Default
    private Boolean estado = true;
}
