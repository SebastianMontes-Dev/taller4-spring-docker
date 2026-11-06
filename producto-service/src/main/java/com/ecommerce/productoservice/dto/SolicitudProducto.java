package com.ecommerce.productoservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record SolicitudProducto(
        @NotBlank String nombre,
        String descripcion,
        @NotNull @Positive BigDecimal precio,
        @PositiveOrZero int stock) {
}
