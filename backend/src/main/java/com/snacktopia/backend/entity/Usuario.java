package com.snacktopia.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data @Entity @Table(name = "usuario")
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol = Rol.USER;

    @Enumerated(EnumType.STRING)
    private Preferencia preferencia;

    @Column(name = "creado_en")
    private LocalDateTime creadoEn = LocalDateTime.now();

    public enum Rol { USER, ADMIN }
    public enum Preferencia { DULCE, SALADO, MIXTO }
}