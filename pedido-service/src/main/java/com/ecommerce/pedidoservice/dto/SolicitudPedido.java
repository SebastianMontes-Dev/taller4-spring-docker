package com.ecommerce.pedidoservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SolicitudPedido(
        @NotNull Long productoId,
        @Positive int cantidad) {
}
