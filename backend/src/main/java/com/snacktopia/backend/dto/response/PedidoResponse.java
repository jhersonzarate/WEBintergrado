package com.snacktopia.backend.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PedidoResponse {
    private Long id;
    private BigDecimal total;
    private String estado;
    private LocalDateTime creadoEn;
    private List<DetalleResponse> detalles;

    @Data
    public static class DetalleResponse {
        private Long productoId;
        private String productoNombre;
        private Integer cantidad;
        private BigDecimal precioUnitario;
        private BigDecimal subtotal;
    }
}