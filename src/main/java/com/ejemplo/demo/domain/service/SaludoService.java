package com.ejemplo.demo.domain.service;

import com.ejemplo.demo.api.dto.PrestamoRequest;
import com.ejemplo.demo.api.dto.PrestamoResponse;
import com.ejemplo.demo.api.dto.SaludoResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@Service
public class SaludoService {

    public SaludoResponse crearSaludo(String nombre) {
        String nombreNormalizado = normalizarNombre(nombre);
        String mensaje = "Hola, %s. Bienvenido a Spring Boot 3!".formatted(nombreNormalizado);
        return new SaludoResponse(mensaje, Instant.now());
    }

    /*
    PASO 4 (EJERCICIO):
    - Modifica esta logica para personalizar el formato del nombre.
    - Ideas:
      1) Primera letra mayuscula y resto minuscula.
      2) Rechazar nombres con numeros.
      3) Agregar prefijo "Estudiante".
    */
    public String normalizarNombre(String nombre) {
    	if (nombre.matches(".*\\d.*")) { // Si contiene algún número
    	    throw new IllegalArgumentException("El nombre no puede contener números");
    	}
    	if (nombre == null || nombre.isBlank()) {
            return "Invitado";
        }

        String[] palabras = nombre.trim().split("\\s+"); // Divide por espacios
        StringBuilder resultado = new StringBuilder();

        for (String palabra : palabras) {
            if (!palabra.isEmpty()) {
                resultado.append(palabra.substring(0, 1).toUpperCase())
                         .append(palabra.substring(1).toLowerCase())
                         .append(" ");
            }
        }

        return resultado.toString().trim(); // Quita el último espacio
    }
    
    public PrestamoResponse calcularPrestamo(PrestamoRequest request) {
        double P = request.monto().doubleValue();
        double i = (request.tasaAnual().doubleValue() / 100) / 12; // Interés mensual
        int n = request.meses();

        // Fórmula: Cuota = [P * i * (1 + i)^n] / [(1 + i)^n - 1]
        double cuota = (P * i * Math.pow(1 + i, n)) / (Math.pow(1 + i, n) - 1);
        double totalPagar = cuota * n;
        double intereses = totalPagar - P;

        return new PrestamoResponse(
            BigDecimal.valueOf(cuota).setScale(2, java.math.RoundingMode.HALF_UP),
            BigDecimal.valueOf(intereses).setScale(2, java.math.RoundingMode.HALF_UP),
            BigDecimal.valueOf(totalPagar).setScale(2, java.math.RoundingMode.HALF_UP)
        );
    }
}
