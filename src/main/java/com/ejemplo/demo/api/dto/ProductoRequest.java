package com.ejemplo.demo.api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ProductoRequest(
    @NotBlank(message = "El nombre del producto es obligatorio")
    String nombre,
    
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    BigDecimal precio,
    
    @NotNull(message = "Debe especificar el ID de una categoria")
    Long categoriaId
) {}