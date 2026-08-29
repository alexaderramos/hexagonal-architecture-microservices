package com.pruebatecnica.clientes.infrastructure.adapter.in.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaMovimientoEventListener {

    private final ObjectMapper objectMapper;

    public KafkaMovimientoEventListener() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @KafkaListener(topics = "movimientos-topic", groupId = "clientes-group")
    public void listenMovimientos(String message) {
        try {
            MovimientoEvent event = objectMapper.readValue(message, MovimientoEvent.class);
            log.info("🔔 [NOTIFICACIÓN] - Enviando notificación al cliente (ID: {})", event.getClienteId());
            log.info("Detalle de la transacción: {} - Valor: {} - Saldo actual: {}", 
                    event.getTipoMovimiento(), event.getValor(), event.getSaldo());
        } catch (JsonProcessingException e) {
            log.error("Error al procesar el mensaje de Kafka: {}", message, e);
        }
    }
}
