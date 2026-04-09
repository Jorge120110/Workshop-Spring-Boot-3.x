package com.ejemplo.demo.api.dto;

import java.math.BigDecimal;

public record PrestamoResponse(
    BigDecimal cuotaMensual,
    BigDecimal totalIntereses,
    BigDecimal totalAPagar
) {}