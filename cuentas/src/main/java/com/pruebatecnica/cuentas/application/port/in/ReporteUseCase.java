package com.pruebatecnica.cuentas.application.port.in;

import com.pruebatecnica.cuentas.domain.model.ReporteRow;
import java.time.LocalDate;
import java.util.List;

public interface ReporteUseCase {
    List<ReporteRow> generarReporte(String clienteId, LocalDate fechaInicio, LocalDate fechaFin);
}
