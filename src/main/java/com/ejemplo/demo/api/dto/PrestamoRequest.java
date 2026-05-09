package com.ejemplo.demo.api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record PrestamoRequest(
    @NotNull @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0") BigDecimal monto,
    @NotNull @DecimalMin(value = "0.01", message = "La tasa anual debe ser mayor a 0") BigDecimal tasaAnual,
    @Min(value = 1, message = "Los meses deben ser al menos 1")
    @Max(value = 360, message = "Los meses no pueden exceder 360")
    int meses
) {}
