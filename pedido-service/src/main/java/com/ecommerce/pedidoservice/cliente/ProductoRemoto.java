package com.ecommerce.pedidoservice.cliente;

import java.math.BigDecimal;

/** Vista minima de un producto tal como lo expone producto-service. */
public record ProductoRemoto(Long id, String nombre, BigDecimal precio, int stock) {
}
