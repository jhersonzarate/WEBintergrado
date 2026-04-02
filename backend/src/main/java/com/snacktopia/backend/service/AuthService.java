package com.snacktopia.backend.service;

import com.snacktopia.backend.dto.request.LoginRequest;
import com.snacktopia.backend.dto.request.RegisterRequest;
import com.snacktopia.backend.dto.response.AuthResponse;
import com.snacktopia.backend.entity.Usuario;
import com.snacktopia.backend.exception.BadRequestException;
import com.snacktopia.backend.repository.UsuarioRepository;
import com.snacktopia.backend.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse login(LoginRequest request) {
        var usuario = usuarioRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new BadRequestException("Credenciales inválidas"));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new BadRequestException("Credenciales inválidas");
        }

        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRol().name());
        return new AuthResponse(token, usuario.getEmail(), usuario.getNombre(), usuario.getRol().name());
    }

    public AuthResponse register(RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("El email ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(Usuario.Rol.USER);

        if (request.getPreferencia() != null) {
            usuario.setPreferencia(Usuario.Preferencia.valueOf(request.getPreferencia().toUpperCase()));
        }

        usuarioRepository.save(usuario);
        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRol().name());
        return new AuthResponse(token, usuario.getEmail(), usuario.getNombre(), usuario.getRol().name());
    }
}