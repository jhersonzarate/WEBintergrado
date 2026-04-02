package com.snacktopia.backend.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductoResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer stock;
    private String imagenUrl;
    private String marca;
    private String categoriaNombre;
    private String categoriaTipo;
    private Long categoriaId;
    private Boolean activo;
}