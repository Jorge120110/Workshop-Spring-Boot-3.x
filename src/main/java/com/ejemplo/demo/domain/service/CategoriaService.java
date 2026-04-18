package com.ejemplo.demo.domain.service;

import com.ejemplo.demo.domain.model.Categoria;
import com.ejemplo.demo.domain.repository.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CategoriaService {
    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Categoria> listarTodas() { return repository.findAll(); }

    @Transactional
    public Categoria guardar(Categoria c) { return repository.save(c); }

    @Transactional(readOnly = true)
    public Categoria buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Categoria no encontrada"));
    }
    
    @Transactional
    public void eliminar(Long id) {
        if(!repository.existsById(id)) throw new EntityNotFoundException("ID no existe");
        repository.deleteById(id);
    }
}