package com.ejemplo.demo.domain.service;

public class EstadoManualService {

    private int valorActual;

    public int actualizar(int valor) {
        this.valorActual = valor;
        return valorActual;
    }

    public int obtener() {
        return valorActual;
    }
}
