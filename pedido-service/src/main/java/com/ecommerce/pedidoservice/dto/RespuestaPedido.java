package com.ecommerce.pedidoservice.dto;

import com.ecommerce.pedidoservice.modelo.Pedido;

import java.math.BigDecimal;
import java.time.Instant;

public record RespuestaPedido(
        Long id,
        Long productoId,
        String producto,
        int cantidad,
        BigDecimal total,
        Instant creadoEn) {

    public static RespuestaPedido desde(Pedido pedido) {
        return new RespuestaPedido(
                pedido.getId(),
                pedido.getProductoId(),
                pedido.getNombreProducto(),
                pedido.getCantidad(),
                pedido.getTotal(),
                pedido.getCreadoEn());
    }
}
