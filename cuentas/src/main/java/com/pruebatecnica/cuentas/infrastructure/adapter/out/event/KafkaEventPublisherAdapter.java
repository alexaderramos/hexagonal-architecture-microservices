package com.pruebatecnica.cuentas.infrastructure.adapter.out.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pruebatecnica.cuentas.application.port.out.MovimientoEventPublisherPort;
import com.pruebatecnica.cuentas.domain.model.Movimiento;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaEventPublisherAdapter implements MovimientoEventPublisherPort {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaEventPublisherAdapter(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void publishMovimientoCreadoEvent(Movimiento movimiento, String clienteId) {
        MovimientoEvent event = MovimientoEvent.builder()
                .idMovimiento(String.valueOf(movimiento.getId()))
                .fecha(movimiento.getFecha())
                .tipoMovimiento(movimiento.getTipoMovimiento() != null ? movimiento.getTipoMovimiento().name() : null)
                .valor(movimiento.getValor())
                .saldo(movimiento.getSaldo())
                .numeroCuenta(movimiento.getNumeroCuenta())
                .clienteId(clienteId)
                .build();

        try {
            String eventJson = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("movimientos-topic", event.getIdMovimiento(), eventJson);
            log.info("Evento de Movimiento publicado en Kafka para cuenta {}: {}", event.getNumeroCuenta(), eventJson);
        } catch (JsonProcessingException e) {
            log.error("Error al serializar el evento de movimiento a JSON", e);
        }
    }
}
