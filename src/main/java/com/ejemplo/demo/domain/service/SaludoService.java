package com.ejemplo.demo.domain.service;

import com.ejemplo.demo.api.dto.SaludoResponse;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class SaludoService {

    public SaludoResponse crearSaludo(String nombre) {
        String nombreNormalizado = normalizarNombre(nombre);
        String mensaje = "Hola, %s. Bienvenido a Spring Boot 3!".formatted(nombreNormalizado);
        return new SaludoResponse(mensaje, Instant.now());
    }

    public String normalizarNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return "Invitado";
        }
        if (nombre.matches(".*\\d.*")) {
            throw new IllegalArgumentException("El nombre no puede contener numeros");
        }

        String[] palabras = nombre.trim().split("\\s+");
        StringBuilder resultado = new StringBuilder();

        for (String palabra : palabras) {
            resultado.append(palabra.substring(0, 1).toUpperCase())
                    .append(palabra.substring(1).toLowerCase())
                    .append(" ");
        }

        return resultado.toString().trim();
    }
}
