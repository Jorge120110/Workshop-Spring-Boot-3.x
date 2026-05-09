package com.ejemplo.demo.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplo.demo.api.contract.SimulacionesApi;
import com.ejemplo.demo.api.contract.WorkshopApi;
import com.ejemplo.demo.api.dto.SaludoResponse;
import com.ejemplo.demo.api.dto.WorkshopHealthResponse;
import com.ejemplo.demo.domain.service.SaludoService;

import com.ejemplo.demo.api.dto.PrestamoRequest;
import com.ejemplo.demo.api.dto.PrestamoResponse;
import com.ejemplo.demo.api.dto.SaludoRequest;
import jakarta.validation.Valid;

@RestController
public class SaludoController implements WorkshopApi, SimulacionesApi {

    @Override
    public ResponseEntity<WorkshopHealthResponse> getWorkshopHealth() {
        return ResponseEntity.ok(new WorkshopHealthResponse("ok", "Workshop Spring Boot activo"));
    }

    private final SaludoService saludoService;

    public SaludoController(SaludoService saludoService) {
        this.saludoService = saludoService;
    }

    @Override
    public ResponseEntity<SaludoResponse> saludarPorGet(String nombre) {
        return ResponseEntity.ok(saludoService.crearSaludo(nombre));
    }

    @Override
    public ResponseEntity<SaludoResponse> saludarPorPost(@Valid SaludoRequest saludoRequest) {
        return ResponseEntity.ok(saludoService.crearSaludo(saludoRequest.nombre()));
    }

    @Override
    public ResponseEntity<PrestamoResponse> simularPrestamo(@Valid PrestamoRequest prestamoRequest) {
        return ResponseEntity.ok(saludoService.calcularPrestamo(prestamoRequest));
    }
}
