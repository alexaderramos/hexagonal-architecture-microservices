package com.pruebatecnica.cuentas.infrastructure.adapter.in.web;

import com.pruebatecnica.cuentas.application.port.in.CuentaUseCase;
import com.pruebatecnica.cuentas.domain.model.Cuenta;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaUseCase cuentaUseCase;

    @PostMapping
    public ResponseEntity<Cuenta> createCuenta(@Valid @RequestBody Cuenta cuenta) {
        Cuenta created = cuentaUseCase.createCuenta(cuenta);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{numeroCuenta}")
    public ResponseEntity<Cuenta> getCuenta(@PathVariable String numeroCuenta) {
        return ResponseEntity.ok(cuentaUseCase.getCuentaByNumeroCuenta(numeroCuenta));
    }

    @GetMapping
    public ResponseEntity<List<Cuenta>> getAllCuentas() {
        return ResponseEntity.ok(cuentaUseCase.getAllCuentas());
    }

    @PutMapping("/{numeroCuenta}")
    public ResponseEntity<Cuenta> updateCuenta(@PathVariable String numeroCuenta, @Valid @RequestBody Cuenta cuenta) {
        return ResponseEntity.ok(cuentaUseCase.updateCuenta(numeroCuenta, cuenta));
    }

}
