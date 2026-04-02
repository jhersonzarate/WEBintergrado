package com.snacktopia.backend.service;

import com.snacktopia.backend.dto.request.ProductoRequest;
import com.snacktopia.backend.dto.response.ProductoResponse;
import com.snacktopia.backend.entity.Producto;
import com.snacktopia.backend.exception.ResourceNotFoundException;
import com.snacktopia.backend.repository.CategoriaRepository;
import com.snacktopia.backend.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service @RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public List<ProductoResponse> listarTodos() {
        return productoRepository.findByActivoTrue().stream()
            .map(this::toResponse).toList();
    }

    public List<ProductoResponse> listarPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoriaIdAndActivoTrue(categoriaId).stream()
            .map(this::toResponse).toList();
    }

    public List<ProductoResponse> listarPorMarca(String marca) {
        return productoRepository.findByMarcaContainingIgnoreCaseAndActivoTrue(marca).stream()
            .map(this::toResponse).toList();
    }

    public ProductoResponse obtenerPorId(Long id) {
        return toResponse(findOrThrow(id));
    }

    public ProductoResponse crear(ProductoRequest request) {
        Producto producto = toEntity(request, new Producto());
        return toResponse(productoRepository.save(producto));
    }

    public ProductoResponse actualizar(Long id, ProductoRequest request) {
        Producto producto = findOrThrow(id);
        return toResponse(productoRepository.save(toEntity(request, producto)));
    }

    public void eliminar(Long id) {
        Producto producto = findOrThrow(id);
        producto.setActivo(false);
        productoRepository.save(producto);
    }

    private Producto findOrThrow(Long id) {
        return productoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
    }

    private Producto toEntity(ProductoRequest req, Producto p) {
        var categoria = categoriaRepository.findById(req.getCategoriaId())
            .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        p.setNombre(req.getNombre());
        p.setDescripcion(req.getDescripcion());
        p.setPrecio(req.getPrecio());
        p.setStock(req.getStock());
        p.setImagenUrl(req.getImagenUrl());
        p.setMarca(req.getMarca());
        p.setCategoria(categoria);
        p.setActivo(req.getActivo());
        return p;
    }

    public ProductoResponse toResponse(Producto p) {
        ProductoResponse r = new ProductoResponse();
        r.setId(p.getId());
        r.setNombre(p.getNombre());
        r.setDescripcion(p.getDescripcion());
        r.setPrecio(p.getPrecio());
        r.setStock(p.getStock());
        r.setImagenUrl(p.getImagenUrl());
        r.setMarca(p.getMarca());
        r.setActivo(p.getActivo());
        r.setCategoriaId(p.getCategoria().getId());
        r.setCategoriaNombre(p.getCategoria().getNombre());
        r.setCategoriaTipo(p.getCategoria().getTipo().name());
        return r;
    }
}