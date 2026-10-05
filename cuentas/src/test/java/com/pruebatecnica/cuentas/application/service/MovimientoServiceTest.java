package com.pruebatecnica.cuentas.application.service;

import com.pruebatecnica.cuentas.application.port.out.CuentaRepositoryPort;
import com.pruebatecnica.cuentas.application.port.out.MovimientoRepositoryPort;
import com.pruebatecnica.cuentas.domain.exception.BusinessException;
import com.pruebatecnica.cuentas.domain.model.Cuenta;
import com.pruebatecnica.cuentas.domain.model.Movimiento;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovimientoServiceTest {

    @Mock
    private MovimientoRepositoryPort movimientoRepositoryPort;

    @Mock
    private CuentaRepositoryPort cuentaRepositoryPort;

    @Mock
    private com.pruebatecnica.cuentas.application.port.out.MovimientoEventPublisherPort movimientoEventPublisherPort;

    @InjectMocks
    private MovimientoService movimientoService;

    @Test
    void createMovimiento_ShouldThrowException_WhenSaldoIsInsufficient() {
        Cuenta cuenta = Cuenta.builder()
                .numeroCuenta("123")
                .saldoInicial(new BigDecimal("100"))
                .build();

        Movimiento retiro = Movimiento.builder()
                .numeroCuenta("123")
                .valor(new BigDecimal("-150"))
                .build();

        when(cuentaRepositoryPort.findByNumeroCuenta("123")).thenReturn(Optional.of(cuenta));
        when(movimientoRepositoryPort.findFirstByNumeroCuentaOrderByFechaDesc("123")).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            movimientoService.createMovimiento(retiro);
        });

        assertEquals("Saldo no disponible", exception.getMessage());
        verify(movimientoRepositoryPort, never()).save(any());
    }

    @Test
    void createMovimiento_ShouldSucceed_WhenSaldoIsSufficient() {
        Cuenta cuenta = Cuenta.builder()
                .numeroCuenta("123")
                .saldoInicial(new BigDecimal("100"))
                .build();

        Movimiento retiro = Movimiento.builder()
                .numeroCuenta("123")
                .valor(new BigDecimal("-50"))
                .build();

        when(cuentaRepositoryPort.findByNumeroCuenta("123")).thenReturn(Optional.of(cuenta));
        when(movimientoRepositoryPort.findFirstByNumeroCuentaOrderByFechaDesc("123")).thenReturn(Optional.empty());
        when(movimientoRepositoryPort.save(any(Movimiento.class))).thenAnswer(i -> i.getArguments()[0]);

        Movimiento saved = movimientoService.createMovimiento(retiro);

        assertNotNull(saved);
        assertEquals(new BigDecimal("50"), saved.getSaldo());
    }
}
