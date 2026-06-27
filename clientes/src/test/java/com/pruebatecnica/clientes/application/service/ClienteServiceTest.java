package com.pruebatecnica.clientes.application.service;

import com.pruebatecnica.clientes.application.port.out.ClienteRepositoryPort;
import com.pruebatecnica.clientes.domain.exception.ResourceNotFoundException;
import com.pruebatecnica.clientes.domain.model.Cliente;
import com.pruebatecnica.clientes.domain.model.Genero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepositoryPort clienteRepositoryPort;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = Cliente.builder()
                .id(1L)
                .nombre("Jose Lema")
                .genero(Genero.M)
                .edad(30)
                .identificacion("1234567890")
                .direccion("Otavalo sn y principal")
                .telefono("098254785")
                .clienteId("123e4567-e89b-12d3-a456-426614174000")
                .contrasena("1234")
                .estado(true)
                .build();
    }

    @Test
    void createCliente_ShouldReturnSavedCliente() {
        when(clienteRepositoryPort.save(any(Cliente.class))).thenReturn(cliente);

        Cliente result = clienteService.createCliente(cliente);

        assertNotNull(result);
        assertEquals("Jose Lema", result.getNombre());
        verify(clienteRepositoryPort, times(1)).save(cliente);
    }

    @Test
    void getClienteById_WhenExists_ShouldReturnCliente() {
        when(clienteRepositoryPort.findByClienteId("123e4567-e89b-12d3-a456-426614174000")).thenReturn(Optional.of(cliente));

        Cliente result = clienteService.getClienteById("123e4567-e89b-12d3-a456-426614174000");

        assertNotNull(result);
        assertEquals("123e4567-e89b-12d3-a456-426614174000", result.getClienteId());
        verify(clienteRepositoryPort, times(1)).findByClienteId("123e4567-e89b-12d3-a456-426614174000");
    }

    @Test
    void getClienteById_WhenNotExists_ShouldThrowResourceNotFoundException() {
        when(clienteRepositoryPort.findByClienteId("non-existent-id")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clienteService.getClienteById("non-existent-id"));
        verify(clienteRepositoryPort, times(1)).findByClienteId("non-existent-id");
    }
}
