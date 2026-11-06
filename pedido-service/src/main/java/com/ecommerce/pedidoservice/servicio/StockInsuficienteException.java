package com.ecommerce.pedidoservice.servicio;

public class StockInsuficienteException extends RuntimeException {

    public StockInsuficienteException(String producto, int disponible, int solicitado) {
        super("Stock insuficiente de '%s': disponible %d, solicitado %d".formatted(producto, disponible, solicitado));
    }
}
