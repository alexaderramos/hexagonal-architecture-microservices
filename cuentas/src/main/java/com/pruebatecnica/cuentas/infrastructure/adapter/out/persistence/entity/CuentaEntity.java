package com.pruebatecnica.cuentas.infrastructure.adapter.out.persistence.entity;

import com.pruebatecnica.cuentas.domain.model.TipoCuenta;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@Table(name = "cuenta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuentaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private String numeroCuenta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCuenta tipoCuenta;

    @Column(nullable = false)
    private BigDecimal saldoInicial;

    @Column(nullable = false)
    private Boolean estado;

    @Column(nullable = false)
    private String clienteId;

    @PrePersist
    protected void onCreate() {
        if (this.numeroCuenta == null || this.numeroCuenta.isEmpty()) {
            // Genera un número aleatorio de 10 dígitos
            long randomNum = ThreadLocalRandom.current().nextLong(1000000000L, 10000000000L);
            this.numeroCuenta = String.valueOf(randomNum);
        }
    }
}
