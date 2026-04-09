package com.ejemplo.demo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import com.ejemplo.demo.api.dto.SaludoResponse;
import com.ejemplo.demo.domain.service.SaludoService;
import org.springframework.web.bind.annotation.RequestParam;

import com.ejemplo.demo.api.dto.PrestamoRequest;
import com.ejemplo.demo.api.dto.PrestamoResponse;
import com.ejemplo.demo.api.dto.SaludoRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@RestController
@RequestMapping("/api/v1")
public class SaludoController {

    @GetMapping
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
                "estado", "ok",
                "mensaje", "Workshop Spring Boot activo"
        ));
    }

    private final SaludoService saludoService;

    public SaludoController(SaludoService saludoService) {
        this.saludoService = saludoService;
    }

    @GetMapping("/saludos")
    public ResponseEntity<SaludoResponse> saludar(
            @RequestParam(defaultValue = "Mundo") String nombre
    ) {
        return ResponseEntity.ok(saludoService.crearSaludo(nombre));
    }

    @PostMapping("/saludos")
    public ResponseEntity<SaludoResponse> saludarPost(@Valid @RequestBody SaludoRequest request) {
        return ResponseEntity.ok(saludoService.crearSaludo(request.nombre()));
    }

    @PostMapping("/prestamos")
    public ResponseEntity<PrestamoResponse> simularPrestamo(
            @jakarta.validation.Valid @RequestBody PrestamoRequest request
    ) {
        return ResponseEntity.ok(saludoService.calcularPrestamo(request));
    }
}
