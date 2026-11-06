package com.ecommerce.productoservice.dto;

import com.ecommerce.productoservice.modelo.Producto;

import java.math.BigDecimal;

public record RespuestaProducto(
        Long id,
        String nombre,
        String descripcion,
        BigDecimal precio,
        int stock) {

    public static RespuestaProducto desde(Producto producto) {
        return new RespuestaProducto(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getStock());
    }
}
