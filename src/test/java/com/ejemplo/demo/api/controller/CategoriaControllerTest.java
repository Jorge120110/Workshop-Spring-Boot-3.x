package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.domain.model.Categoria;
import com.ejemplo.demo.domain.service.CategoriaService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoriaController.class)
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriaService categoriaService;

    @Test
    @DisplayName("Debe listar categorias")
    void debeListarCategorias() throws Exception {
        when(categoriaService.listarTodas()).thenReturn(List.of(categoria(1L, "Tecnologia", "Productos tech")));

        mockMvc.perform(get("/api/v1/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Tecnologia"));
    }

    @Test
    @DisplayName("Debe crear categoria")
    void debeCrearCategoria() throws Exception {
        when(categoriaService.guardar(any(Categoria.class))).thenReturn(categoria(1L, "Libros", "Lectura"));

        mockMvc.perform(post("/api/v1/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nombre": "Libros",
                                  "descripcion": "Lectura"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Libros"));
    }

    @Test
    @DisplayName("Debe validar categoria invalida")
    void debeValidarCategoriaInvalida() throws Exception {
        mockMvc.perform(post("/api/v1/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nombre": "",
                                  "descripcion": "Sin nombre"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value("VALIDATION_ERROR"));
    }

    @Test
    @DisplayName("Debe retornar 404 cuando la categoria no existe")
    void debeRetornarNotFoundCategoria() throws Exception {
        when(categoriaService.buscarPorId(99L)).thenThrow(new EntityNotFoundException("Categoria no encontrada"));

        mockMvc.perform(get("/api/v1/categorias/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.codigo").value("NOT_FOUND"));
    }

    @Test
    @DisplayName("Debe eliminar categoria")
    void debeEliminarCategoria() throws Exception {
        doNothing().when(categoriaService).eliminar(1L);

        mockMvc.perform(delete("/api/v1/categorias/1"))
                .andExpect(status().isNoContent());
    }

    private Categoria categoria(Long id, String nombre, String descripcion) {
        Categoria categoria = new Categoria();
        categoria.setId(id);
        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);
        return categoria;
    }
}
