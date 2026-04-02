package com.snacktopia.backend.repository;
import com.snacktopia.backend.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByUsuarioIdOrderByCreadoEnDesc(Long usuarioId);
}
