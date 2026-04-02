package com.snacktopia.backend.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductoRequest {
    @NotBlank @Size(max = 150)
    private String nombre;
    private String descripcion;
    @NotNull @DecimalMin("0.01")
    private BigDecimal precio;
    @Min(0)
    private Integer stock;
    private String imagenUrl;
    @Size(max = 100)
    private String marca;
    @NotNull
    private Long categoriaId;
    private Boolean activo = true;
}