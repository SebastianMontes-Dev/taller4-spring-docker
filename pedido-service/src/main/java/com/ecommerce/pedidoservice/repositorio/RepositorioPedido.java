package com.ecommerce.pedidoservice.repositorio;

import com.ecommerce.pedidoservice.modelo.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioPedido extends JpaRepository<Pedido, Long> {
}
