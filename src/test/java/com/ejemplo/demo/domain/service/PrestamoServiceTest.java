package com.ejemplo.demo.domain.service;

import com.ejemplo.demo.api.dto.PrestamoRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class PrestamoServiceTest {

    private final PrestamoService prestamoService = new PrestamoService();

    @Test
    @DisplayName("Debe calcular cuota, intereses y total a pagar")
    void debeCalcularPrestamo() {
        var request = new PrestamoRequest(
                new BigDecimal("1000"),
                new BigDecimal("12"),
                12
        );

        var response = prestamoService.calcularPrestamo(request);

        assertThat(response.cuotaMensual()).isEqualByComparingTo("88.85");
        assertThat(response.interesTotal()).isEqualByComparingTo("66.19");
        assertThat(response.totalPagar()).isEqualByComparingTo("1066.19");
    }
}
