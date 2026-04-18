package com.ejemplo.demo.domain.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "categorias") // Así se llamará la tabla en Postgres
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID autoincrementable (1, 2, 3...)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    private String descripcion;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    private List<Producto> productos;

    // Getters y Setters (obligatorios para que JPA funcione)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}