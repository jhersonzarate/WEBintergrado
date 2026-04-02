package com.snacktopia.backend.service;

import com.snacktopia.backend.dto.response.RecomendacionResponse;
import com.snacktopia.backend.entity.*;
import com.snacktopia.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class RecomendacionService {

    private final UsuarioRepository usuarioRepository;
    private final PedidoDetalleRepository pedidoDetalleRepository;
    private final ProductoRepository productoRepository;

    public List<RecomendacionResponse> recomendar(String email) {
        var usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new com.snacktopia.backend.exception.ResourceNotFoundException("Usuario no encontrado"));

        // Historial de compras
        List<PedidoDetalle> historial = pedidoDetalleRepository.findAllByUsuarioId(usuario.getId());

        // Contar categorías más compradas
        Map<Long, Long> categoriaCount = historial.stream()
            .collect(Collectors.groupingBy(
                d -> d.getProducto().getCategoria().getId(),
                Collectors.counting()
            ));

        // Productos ya comprados
        Set<Long> comprados = historial.stream()
            .map(d -> d.getProducto().getId())
            .collect(Collectors.toSet());

        // Preferencia del usuario
        String preferencia = usuario.getPreferencia() != null ?
            usuario.getPreferencia().name() : "MIXTO";

        // Candidatos: productos activos no comprados
        List<Producto> candidatos = productoRepository.findByActivoTrue().stream()
            .filter(p -> !comprados.contains(p.getId()))
            .toList();

        // Puntuar candidatos
        return candidatos.stream()
            .map(p -> {
                int score = 0;
                String motivo = "Sugerido para ti";

                // Por preferencia
                String tipoCat = p.getCategoria().getTipo().name();
                if (tipoCat.equals(preferencia)) {
                    score += 10;
                    motivo = "Basado en tu preferencia " + preferencia.toLowerCase();
                }

                // Por categoría del historial
                Long catId = p.getCategoria().getId();
                if (categoriaCount.containsKey(catId)) {
                    score += (int) (categoriaCount.get(catId) * 5);
                    motivo = "Basado en tu historial de compras";
                }

                RecomendacionResponse r = new RecomendacionResponse();
                r.setProductoId(p.getId());
                r.setNombre(p.getNombre());
                r.setImagenUrl(p.getImagenUrl());
                r.setPrecio(p.getPrecio());
                r.setMotivo(motivo);
                r.setCategoriaNombre(p.getCategoria().getNombre());
                return Map.entry(score, r);
            })
            .sorted((a, b) -> b.getKey().compareTo(a.getKey()))
            .limit(6)
            .map(Map.Entry::getValue)
            .toList();
    }
}