package com.ejemplo.demo.domain.service;

import com.ejemplo.demo.domain.model.Producto;
import com.ejemplo.demo.domain.repository.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProductoService {
    private final ProductoRepository repository;
    private final CategoriaService categoriaService;

    public ProductoService(ProductoRepository repository, CategoriaService categoriaService) {
        this.repository = repository;
        this.categoriaService = categoriaService;
    }

    @Transactional(readOnly = true)
    public List<Producto> listarTodos() { return repository.findAll(); }

    @Transactional(readOnly = true)
    public Producto buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
    }

    @Transactional
    public Producto guardar(Producto p, Long categoriaId) {
        p.setCategoria(categoriaService.buscarPorId(categoriaId));
        return repository.save(p);
    }

    @Transactional
    public Producto actualizar(Long id, Producto datos, Long categoriaId) {
        Producto producto = buscarPorId(id);
        producto.setNombre(datos.getNombre());
        producto.setPrecio(datos.getPrecio());
        producto.setCategoria(categoriaService.buscarPorId(categoriaId));
        return repository.save(producto);
    }

    @Transactional
    public void eliminar(Long id) {
        if(!repository.existsById(id)) throw new EntityNotFoundException("Producto no encontrado");
        repository.deleteById(id);
    }
}
