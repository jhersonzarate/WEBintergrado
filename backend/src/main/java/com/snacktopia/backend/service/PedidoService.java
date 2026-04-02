package com.snacktopia.backend.service;

import com.snacktopia.backend.dto.response.PedidoResponse;
import com.snacktopia.backend.entity.*;
import com.snacktopia.backend.exception.ResourceNotFoundException;
import com.snacktopia.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service @RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final CarritoRepository carritoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public PedidoResponse checkout(String email) {
        var usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Carrito carrito = carritoRepository.findByUsuarioId(usuario.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Carrito vacío"));

        if (carrito.getItems().isEmpty()) {
            throw new com.snacktopia.backend.exception.BadRequestException("El carrito está vacío");
        }

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);

        List<PedidoDetalle> detalles = carrito.getItems().stream().map(item -> {
            PedidoDetalle d = new PedidoDetalle();
            d.setPedido(pedido);
            d.setProducto(item.getProducto());
            d.setCantidad(item.getCantidad());
            d.setPrecioUnitario(item.getProducto().getPrecio());
            return d;
        }).toList();

        BigDecimal total = detalles.stream()
            .map(d -> d.getPrecioUnitario().multiply(BigDecimal.valueOf(d.getCantidad())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        pedido.setTotal(total);
        pedido.setDetalles(detalles);
        pedidoRepository.save(pedido);

        carrito.getItems().clear();
        carritoRepository.save(carrito);

        return toResponse(pedido);
    }

    public List<PedidoResponse> listarPorUsuario(String email) {
        var usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        return pedidoRepository.findByUsuarioIdOrderByCreadoEnDesc(usuario.getId())
            .stream().map(this::toResponse).toList();
    }

    private PedidoResponse toResponse(Pedido p) {
        PedidoResponse r = new PedidoResponse();
        r.setId(p.getId());
        r.setTotal(p.getTotal());
        r.setEstado(p.getEstado().name());
        r.setCreadoEn(p.getCreadoEn());

        r.setDetalles(p.getDetalles().stream().map(d -> {
            PedidoResponse.DetalleResponse dr = new PedidoResponse.DetalleResponse();
            dr.setProductoId(d.getProducto().getId());
            dr.setProductoNombre(d.getProducto().getNombre());
            dr.setCantidad(d.getCantidad());
            dr.setPrecioUnitario(d.getPrecioUnitario());
            dr.setSubtotal(d.getPrecioUnitario().multiply(BigDecimal.valueOf(d.getCantidad())));
            return dr;
        }).toList());

        return r;
    }
}