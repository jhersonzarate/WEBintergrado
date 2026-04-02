package com.snacktopia.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data @Entity @Table(name = "recomendacion")
public class Recomendacion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private String motivo;
    private Integer puntuacion;
}