package com.snacktopia.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class AuthResponse {
    private String token;
    private String email;
    private String nombre;
    private String rol;
}