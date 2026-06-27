package com.pruebatecnica.clientes.infrastructure.adapter.in.web;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pruebatecnica.clientes.domain.model.Cliente;
import com.pruebatecnica.clientes.domain.model.Genero;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ClienteControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        this.cliente = Cliente.builder()
                .nombre("Jose Lema")
                .genero(Genero.M)
                .edad(30)
                .identificacion("1234567890")
                .direccion("Otavalo sn y principal")
                .telefono("098254785")
                .contrasena("1234")
                .estado(true)
                .build();
    }

    @Test
    void shouldCreateClienteAndReturnCreated() throws Exception {
        Cliente cliente = Cliente.builder()
                .nombre("Jose Lema")
                .genero(Genero.M)
                .edad(30)
                .identificacion("1712345678")
                .direccion("Otavalo sn y principal")
                .telefono("098254785")
                .contrasena("1234")
                .estado(true)
                .build();

        Gson gson = new Gson();
        String clienteJson = gson.toJson(cliente);

        mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clienteJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Jose Lema"))
                .andExpect(jsonPath("$.clienteId").isString())
                .andExpect(jsonPath("$.contrasena").doesNotExist());
    }
}

