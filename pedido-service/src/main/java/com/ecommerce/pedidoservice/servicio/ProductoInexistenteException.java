package com.ecommerce.pedidoservice.servicio;

public class ProductoInexistenteException extends RuntimeException {

    public ProductoInexistenteException(Long productoId) {
        super("No existe el producto con id " + productoId);
    }
}
