package com.snacktopia.backend.repository;
import com.snacktopia.backend.entity.PedidoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
public interface PedidoDetalleRepository extends JpaRepository<PedidoDetalle, Long> {
    @Query("SELECT pd FROM PedidoDetalle pd WHERE pd.pedido.usuario.id = :usuarioId")
    List<PedidoDetalle> findAllByUsuarioId(Long usuarioId);
}