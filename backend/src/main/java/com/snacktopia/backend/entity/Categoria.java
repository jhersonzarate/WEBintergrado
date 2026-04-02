package com.snacktopia.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data @Entity @Table(name = "categoria")
public class Categoria {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCategoria tipo;

    public enum TipoCategoria { DULCE, SALADO, MIXTO }
}