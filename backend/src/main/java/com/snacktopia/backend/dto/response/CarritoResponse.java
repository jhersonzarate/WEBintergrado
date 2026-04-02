package com.snacktopia.backend.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CarritoResponse {
    private Long id;
    private List<ItemResponse> items;
    private BigDecimal total;

    @Data
    public static class ItemResponse {
        private Long id;
        private Long productoId;
        private String productoNombre;
        private String imagenUrl;
        private BigDecimal precioUnitario;
        private Integer cantidad;
        private BigDecimal subtotal;
    }
}