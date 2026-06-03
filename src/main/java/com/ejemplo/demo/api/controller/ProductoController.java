package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.api.contract.ProductosApi;
import com.ejemplo.demo.api.dto.ProductoRequest;
import com.ejemplo.demo.api.dto.ProductoResponse;
import com.ejemplo.demo.domain.model.Producto;
import com.ejemplo.demo.domain.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductoController implements ProductosApi {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @Override
    public ResponseEntity<List<ProductoResponse>> listarProductos() {
        List<ProductoResponse> productos = productoService.listarTodos().stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(productos);
    }

    @Override
    public ResponseEntity<ProductoResponse> obtenerProducto(Long id) {
        return ResponseEntity.ok(toResponse(productoService.buscarPorId(id)));
    }

    @Override
    public ResponseEntity<ProductoResponse> crearProducto(ProductoRequest productoRequest) {
        Producto guardado = productoService.guardar(toEntity(productoRequest), productoRequest.categoriaId());
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(guardado));
    }

    @Override
    public ResponseEntity<ProductoResponse> actualizarProducto(Long id, ProductoRequest productoRequest) {
        return ResponseEntity.ok(toResponse(productoService.actualizar(id, toEntity(productoRequest), productoRequest.categoriaId())));
    }

    @Override
    public ResponseEntity<Void> eliminarProducto(Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
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
