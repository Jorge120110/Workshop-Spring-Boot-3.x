package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.api.dto.ProductoRequest;
import com.ejemplo.demo.api.dto.ProductoResponse;
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
    public List<ProductoResponse> listar() {
        return productoService.listarTodos().stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ProductoResponse obtener(@PathVariable Long id) {
        return toResponse(productoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProductoResponse> crear(@RequestBody @Valid ProductoRequest request) {
        Producto guardado = productoService.guardar(toEntity(request), request.categoriaId());
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(guardado));
    }

    @PutMapping("/{id}")
    public ProductoResponse actualizar(@PathVariable Long id, @RequestBody @Valid ProductoRequest request) {
        return toResponse(productoService.actualizar(id, toEntity(request), request.categoriaId()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
    }

    private Producto toEntity(ProductoRequest request) {
        Producto producto = new Producto();
        producto.setNombre(request.nombre());
        producto.setPrecio(request.precio());
        return producto;
    }

    private ProductoResponse toResponse(Producto producto) {
        return new ProductoResponse(
                producto.getId(),
                producto.getNombre(),
                producto.getPrecio(),
                producto.getCategoria().getNombre()
        );
    }
}
