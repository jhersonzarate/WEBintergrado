package com.snacktopia.backend.service;

import com.snacktopia.backend.dto.request.CarritoItemRequest;
import com.snacktopia.backend.dto.response.CarritoResponse;
import com.snacktopia.backend.entity.*;
import com.snacktopia.backend.exception.*;
import com.snacktopia.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service @RequiredArgsConstructor
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final CarritoItemRepository carritoItemRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public CarritoResponse obtenerCarrito(String email) {
        Carrito carrito = getOrCreateCarrito(email);
        return toResponse(carrito);
    }

    @Transactional
    public CarritoResponse agregarItem(String email, CarritoItemRequest request) {
        Carrito carrito = getOrCreateCarrito(email);
        Producto producto = productoRepository.findById(request.getProductoId())
            .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        var existingItem = carritoItemRepository
            .findByCarritoIdAndProductoId(carrito.getId(), producto.getId());

        if (existingItem.isPresent()) {
            CarritoItem item = existingItem.get();
            item.setCantidad(item.getCantidad() + request.getCantidad());
            carritoItemRepository.save(item);
        } else {
            CarritoItem item = new CarritoItem();
            item.setCarrito(carrito);
            item.setProducto(producto);
            item.setCantidad(request.getCantidad());
            carritoItemRepository.save(item);
        }

        return toResponse(carritoRepository.findById(carrito.getId()).orElseThrow());
    }

    @Transactional
    public CarritoResponse actualizarItem(String email, Long itemId, Integer cantidad) {
        Carrito carrito = getOrCreateCarrito(email);
        CarritoItem item = carritoItemRepository.findById(itemId)
            .orElseThrow(() -> new ResourceNotFoundException("Item no encontrado"));

        if (!item.getCarrito().getId().equals(carrito.getId())) {
            throw new BadRequestException("Item no pertenece al carrito");
        }

        if (cantidad <= 0) {
            carritoItemRepository.delete(item);
        } else {
            item.setCantidad(cantidad);
            carritoItemRepository.save(item);
        }

        return toResponse(carritoRepository.findById(carrito.getId()).orElseThrow());
    }

    @Transactional
    public void vaciarCarrito(String email) {
        Carrito carrito = getOrCreateCarrito(email);
        carrito.getItems().clear();
        carritoRepository.save(carrito);
    }

    private Carrito getOrCreateCarrito(String email) {
        var usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        return carritoRepository.findByUsuarioId(usuario.getId())
            .orElseGet(() -> {
                Carrito c = new Carrito();
                c.setUsuario(usuario);
                return carritoRepository.save(c);
            });
    }

    private CarritoResponse toResponse(Carrito carrito) {
        CarritoResponse r = new CarritoResponse();
        r.setId(carrito.getId());

        List<CarritoResponse.ItemResponse> items = carrito.getItems().stream().map(item -> {
            CarritoResponse.ItemResponse ir = new CarritoResponse.ItemResponse();
            ir.setId(item.getId());
            ir.setProductoId(item.getProducto().getId());
            ir.setProductoNombre(item.getProducto().getNombre());
            ir.setImagenUrl(item.getProducto().getImagenUrl());
            ir.setPrecioUnitario(item.getProducto().getPrecio());
            ir.setCantidad(item.getCantidad());
            ir.setSubtotal(item.getProducto().getPrecio().multiply(BigDecimal.valueOf(item.getCantidad())));
            return ir;
        }).toList();

        r.setItems(items);
        r.setTotal(items.stream().map(CarritoResponse.ItemResponse::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add));
        return r;
    }
}