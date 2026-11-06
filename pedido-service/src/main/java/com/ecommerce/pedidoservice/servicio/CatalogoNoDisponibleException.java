package com.ecommerce.pedidoservice.servicio;

public class CatalogoNoDisponibleException extends RuntimeException {

    public CatalogoNoDisponibleException(Throwable causa) {
        super("producto-service no esta disponible en este momento", causa);
    }
}
