package com.pruebatecnica.cuentas.infrastructure.adapter.in.web;

import com.google.gson.Gson;
import com.pruebatecnica.cuentas.domain.model.Cuenta;
import com.pruebatecnica.cuentas.domain.model.TipoCuenta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class CuentaControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void shouldCreateCuenta() throws Exception {
        Cuenta cuenta = Cuenta.builder()
                .tipoCuenta(TipoCuenta.AHORROS)
                .saldoInicial(new BigDecimal("2000"))
                .estado(true)
                .clienteId("Jose Lema ID")
                .build();

        Gson gson = new Gson();
        String json = gson.toJson(cuenta);

        mockMvc.perform(post("/cuentas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numeroCuenta").exists())
                .andExpect(jsonPath("$.numeroCuenta").isString());
    }
}
