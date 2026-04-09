package com.ejemplo.demo.api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record PrestamoRequest(
    @NotNull @DecimalMin("100.0") BigDecimal monto,
    @NotNull @DecimalMin("0.1") BigDecimal tasaAnual,
    @Min(1) int meses
) {}