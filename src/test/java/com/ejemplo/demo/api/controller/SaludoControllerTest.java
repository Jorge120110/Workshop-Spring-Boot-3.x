package com.ejemplo.demo.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SaludoController.class)
class SaludoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Debe responder health del workshop")
    void debeResponderHealthDelWorkshop() throws Exception {
        mockMvc.perform(get("/api/v1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("ok"));
    }

    /*
    PASO 6 (EJERCICIO):
    Cuando habilites los endpoints de /api/v1/saludos, crea estas pruebas:

    1) GET /api/v1/saludos?nombre=Ana -> 200 y mensaje correcto
    2) POST /api/v1/saludos con {"nombre":""} -> 400 y codigo VALIDATION_ERROR
    */
   
    @org.springframework.boot.test.mock.mockito.MockBean
    private com.ejemplo.demo.domain.service.SaludoService saludoService;

    @Test
    @DisplayName("Debe retornar saludo exitoso con nombre normalizado")
    void debeRetornarSaludoExitoso() throws Exception {
        // Configuramos el simulacro (Mock)
        org.mockito.Mockito.when(saludoService.crearSaludo("Ana"))
                .thenReturn(new com.ejemplo.demo.api.dto.SaludoResponse("Hola, Ana. Bienvenido a Spring Boot 3!", java.time.Instant.now()));

        mockMvc.perform(get("/api/v1/saludos").param("nombre", "Ana"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value(org.hamcrest.Matchers.containsString("Ana")));
    }

    @Test
    @DisplayName("Debe retornar 400 cuando el nombre en el POST es vacío")
    void debeRetornarErrorEnPostInvalido() throws Exception {
        String jsonInvalido = "{\"nombre\": \"\"}";

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/v1/saludos")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content(jsonInvalido))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    @DisplayName("Debe calcular préstamo correctamente")
    void debeCalcularPrestamo() throws Exception {
        // 1. Preparamos el objeto de respuesta simulado
        var respuestaSimulada = new com.ejemplo.demo.api.dto.PrestamoResponse(
                new java.math.BigDecimal("100.00"),
                new java.math.BigDecimal("50.00"),
                new java.math.BigDecimal("1050.00")
        );

        // 2. Configuramos el Mock (IMPORTANTE)
        org.mockito.Mockito.when(saludoService.calcularPrestamo(org.mockito.Mockito.any()))
                .thenReturn(respuestaSimulada);

        String jsonPrestamo = """
            {
              "monto": 1000,
              "tasaAnual": 10,
              "meses": 12
            }
            """;

        mockMvc.perform(post("/api/v1/prestamos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPrestamo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cuotaMensual").exists());
    }
}
