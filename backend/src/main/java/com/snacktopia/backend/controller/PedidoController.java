package com.snacktopia.backend.controller;

import com.snacktopia.backend.dto.response.PedidoResponse;
import com.snacktopia.backend.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping("/checkout")
    public ResponseEntity<PedidoResponse> checkout(Authentication auth) {
        return ResponseEntity.ok(pedidoService.checkout(auth.getName()));
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> listar(Authentication auth) {
        return ResponseEntity.ok(pedidoService.listarPorUsuario(auth.getName()));
    }
}