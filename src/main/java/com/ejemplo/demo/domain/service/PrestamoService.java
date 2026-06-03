package com.ejemplo.demo.domain.service;

import com.ejemplo.demo.api.dto.PrestamoRequest;
import com.ejemplo.demo.api.dto.PrestamoResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PrestamoService {

    public PrestamoResponse calcularPrestamo(PrestamoRequest request) {
        double monto = request.monto().doubleValue();
        double tasaMensual = request.tasaAnual().doubleValue() / 12 / 100;
        int meses = request.meses();

        double factor = Math.pow(1 + tasaMensual, meses);
        double cuota = monto * (tasaMensual * factor) / (factor - 1);
        double totalPagar = cuota * meses;
        double interesTotal = totalPagar - monto;

        return new PrestamoResponse(
                redondear(cuota),
                redondear(interesTotal),
                redondear(totalPagar)
        );
    }

    private BigDecimal redondear(double valor) {
        return BigDecimal.valueOf(valor).setScale(2, RoundingMode.HALF_UP);
    }
}
