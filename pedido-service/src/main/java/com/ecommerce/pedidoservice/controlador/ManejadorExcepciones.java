package com.ecommerce.pedidoservice.controlador;

import com.ecommerce.pedidoservice.servicio.CatalogoNoDisponibleException;
import com.ecommerce.pedidoservice.servicio.PedidoNoEncontradoException;
import com.ecommerce.pedidoservice.servicio.ProductoInexistenteException;
import com.ecommerce.pedidoservice.servicio.StockInsuficienteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ManejadorExcepciones {

    @ExceptionHandler({PedidoNoEncontradoException.class, ProductoInexistenteException.class})
    public ProblemDetail noEncontrado(RuntimeException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(StockInsuficienteException.class)
    public ProblemDetail stockInsuficiente(StockInsuficienteException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(CatalogoNoDisponibleException.class)
    public ProblemDetail catalogoNoDisponible(CatalogoNoDisponibleException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
    }
}
