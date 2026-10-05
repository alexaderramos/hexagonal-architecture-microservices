package com.pruebatecnica.cuentas.infrastructure.adapter.in.web;

import com.pruebatecnica.cuentas.application.port.in.ReporteUseCase;
import com.pruebatecnica.cuentas.domain.model.ReporteRow;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@RestController
@RequestMapping("/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteUseCase reporteUseCase;

    @GetMapping(produces = org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ReporteRow> generarReporte(
            @RequestParam("fecha-rango") String fechaRango,
            @RequestParam("cliente") String clienteId) {

        String[] dates = fechaRango.split(",");
        LocalDate start = LocalDate.parse(dates[0]);
        LocalDate end = dates.length > 1 ? LocalDate.parse(dates[1]) : start;

        return reporteUseCase.generarReporte(clienteId, start, end);
    }
}
