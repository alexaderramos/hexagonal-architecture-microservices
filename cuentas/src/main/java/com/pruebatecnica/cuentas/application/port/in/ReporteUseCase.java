package com.pruebatecnica.cuentas.application.port.in;

import com.pruebatecnica.cuentas.domain.model.ReporteRow;
import java.time.LocalDate;
import reactor.core.publisher.Flux;

public interface ReporteUseCase {
    Flux<ReporteRow> generarReporte(String clienteId, LocalDate fechaInicio, LocalDate fechaFin);
}
