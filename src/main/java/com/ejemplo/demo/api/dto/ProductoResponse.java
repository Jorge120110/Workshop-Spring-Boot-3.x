package com.ejemplo.demo.api.dto;

import java.math.BigDecimal;

public record ProductoResponse(
    Long id,
    String nombre,
    BigDecimal precio,
    String nombreCategoria // Es buena practica devolver el nombre de la categoria, no solo el ID
) {}