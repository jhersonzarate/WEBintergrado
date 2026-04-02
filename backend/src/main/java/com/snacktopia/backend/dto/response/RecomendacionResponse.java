package com.snacktopia.backend.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class RecomendacionResponse {
    private Long productoId;
    private String nombre;
    private String imagenUrl;
    private BigDecimal precio;
    private String motivo;
    private String categoriaNombre;
}