package com.pruebatecnica.cuentas.infrastructure.adapter.in.web;

import com.pruebatecnica.cuentas.application.port.in.MovimientoUseCase;
import com.pruebatecnica.cuentas.domain.model.Movimiento;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoUseCase movimientoUseCase;

    @PostMapping
    public ResponseEntity<Movimiento> createMovimiento(@Valid @RequestBody Movimiento movimiento) {
        Movimiento created = movimientoUseCase.createMovimiento(movimiento);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movimiento> getMovimiento(@PathVariable Long id) {
        return ResponseEntity.ok(movimientoUseCase.getMovimientoById(id));
    }

    @GetMapping
    public ResponseEntity<List<Movimiento>> getAllMovimientos() {
        return ResponseEntity.ok(movimientoUseCase.getAllMovimientos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movimiento> updateMovimiento(@PathVariable Long id, @Valid @RequestBody Movimiento movimiento) {
        return ResponseEntity.ok(movimientoUseCase.updateMovimiento(id, movimiento));
    }
}
