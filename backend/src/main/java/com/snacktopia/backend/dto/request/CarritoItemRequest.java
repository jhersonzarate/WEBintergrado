package com.snacktopia.backend.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CarritoItemRequest {
    @NotNull
    private Long productoId;
    @Min(1)
    private Integer cantidad = 1;
}