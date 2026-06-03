package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.api.contract.CategoriasApi;
import com.ejemplo.demo.api.dto.CategoriaRequest;
import com.ejemplo.demo.api.dto.CategoriaResponse;
import com.ejemplo.demo.domain.model.Categoria;
import com.ejemplo.demo.domain.service.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoriaController implements CategoriasApi {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @Override
    public ResponseEntity<List<CategoriaResponse>> listarCategorias() {
        List<CategoriaResponse> categorias = categoriaService.listarTodas().stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(categorias);
    }

    @Override
    public ResponseEntity<CategoriaResponse> obtenerCategoria(Long id) {
        return ResponseEntity.ok(toResponse(categoriaService.buscarPorId(id)));
    }

    @Override
    public ResponseEntity<CategoriaResponse> crearCategoria(CategoriaRequest categoriaRequest) {
        Categoria guardada = categoriaService.guardar(toEntity(categoriaRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(guardada));
    }

    @Override
    public ResponseEntity<CategoriaResponse> actualizarCategoria(Long id, CategoriaRequest categoriaRequest) {
        return ResponseEntity.ok(toResponse(categoriaService.actualizar(id, toEntity(categoriaRequest))));
    }

    @Override
    public ResponseEntity<Void> eliminarCategoria(Long id) {
        categoriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private Categoria toEntity(CategoriaRequest request) {
        Categoria categoria = new Categoria();
        categoria.setNombre(request.nombre());
        categoria.setDescripcion(request.descripcion());
        return categoria;
    }

    private CategoriaResponse toResponse(Categoria categoria) {
        return new CategoriaResponse(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion()
        );
    }
}
