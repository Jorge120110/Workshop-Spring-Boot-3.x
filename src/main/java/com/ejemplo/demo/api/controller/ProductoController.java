package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.api.dto.ProductoRequest;
import com.ejemplo.demo.domain.model.Producto;
import com.ejemplo.demo.domain.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos") // Ruta diferente a la de categorias
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<Producto> listar() {
        return productoService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody @Valid ProductoRequest request) {
        Producto p = new Producto();
        p.setNombre(request.nombre());
        p.setPrecio(request.precio());
        
        Producto guardado = productoService.guardar(p, request.categoriaId());
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
    }
}