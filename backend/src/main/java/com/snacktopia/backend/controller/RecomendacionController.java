package com.snacktopia.backend.controller;

import com.snacktopia.backend.dto.response.RecomendacionResponse;
import com.snacktopia.backend.service.RecomendacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/recomendaciones")
@RequiredArgsConstructor
public class RecomendacionController {

    private final RecomendacionService recomendacionService;

    @GetMapping
    public ResponseEntity<List<RecomendacionResponse>> recomendar(Authentication auth) {
        return ResponseEntity.ok(recomendacionService.recomendar(auth.getName()));
    }
}