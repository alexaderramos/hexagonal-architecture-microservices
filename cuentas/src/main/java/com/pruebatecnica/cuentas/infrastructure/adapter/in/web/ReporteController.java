package com.pruebatecnica.cuentas.infrastructure.adapter.in.web;

import com.pruebatecnica.cuentas.application.port.in.ReporteUseCase;
import com.pruebatecnica.cuentas.domain.model.ReporteRow;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteUseCase reporteUseCase;

    // F4 Reporte: /reportes?fecha-rango={fechas}&cliente
    @GetMapping
    public ResponseEntity<List<ReporteRow>> generarReporte(
            @RequestParam("fecha-rango") String fechaRango,
            @RequestParam("cliente") String clienteId) {
        
        // Handling range formatted as "yyyy-MM-dd,yyyy-MM-dd" or single date
        String[] dates = fechaRango.split(",");
        LocalDate start = LocalDate.parse(dates[0]);
        LocalDate end = dates.length > 1 ? LocalDate.parse(dates[1]) : start;

        List<ReporteRow> reporte = reporteUseCase.generarReporte(clienteId, start, end);
        return ResponseEntity.ok(reporte);
    }
}
