package com.snacktopia.backend.controller;

import com.snacktopia.backend.dto.request.CarritoItemRequest;
import com.snacktopia.backend.dto.response.CarritoResponse;
import com.snacktopia.backend.service.CarritoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrito")
@RequiredArgsConstructor
public class CarritoController {

    private final CarritoService carritoService;

    @GetMapping
    public ResponseEntity<CarritoResponse> obtener(Authentication auth) {
        return ResponseEntity.ok(carritoService.obtenerCarrito(auth.getName()));
    }

    @PostMapping("/items")
    public ResponseEntity<CarritoResponse> agregar(Authentication auth,
                                                    @Valid @RequestBody CarritoItemRequest request) {
        return ResponseEntity.ok(carritoService.agregarItem(auth.getName(), request));
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<CarritoResponse> actualizar(Authentication auth,
                                                       @PathVariable Long itemId,
                                                       @RequestParam Integer cantidad) {
        return ResponseEntity.ok(carritoService.actualizarItem(auth.getName(), itemId, cantidad));
    }

    @DeleteMapping
    public ResponseEntity<Void> vaciar(Authentication auth) {
        carritoService.vaciarCarrito(auth.getName());
        return ResponseEntity.noContent().build();
    }
}