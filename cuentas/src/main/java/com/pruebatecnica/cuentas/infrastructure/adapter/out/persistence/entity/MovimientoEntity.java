package com.pruebatecnica.cuentas.infrastructure.adapter.out.persistence.entity;

import com.pruebatecnica.cuentas.domain.model.TipoMovimiento;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimiento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimientoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimiento tipoMovimiento;

    @Column(nullable = false, updatable = false)
    private BigDecimal valor;

    @Column(nullable = false, updatable = false)
    private BigDecimal saldo;

    @Column(nullable = false, updatable = false)
    private BigDecimal saldoInicial;

    @Column(nullable = false)
    private String numeroCuenta;

    @Column(nullable = false)
    @Builder.Default
    private Boolean estado = true;

    @PrePersist
    protected void onCreate() {
        if (this.estado == null) {
            this.estado = true;
        }
    }
}
