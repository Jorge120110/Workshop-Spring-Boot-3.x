package com.ejemplo.demo.domain.service;

import org.springframework.stereotype.Service;

@Service
public class EstadoSingletonService {

    private int valorActual;

    public int actualizar(int valor) {
        this.valorActual = valor;
        return valorActual;
    }

    public int obtener() {
        return valorActual;
    }

    public int reiniciar() {
        this.valorActual = 0;
        return valorActual;
    }
}
