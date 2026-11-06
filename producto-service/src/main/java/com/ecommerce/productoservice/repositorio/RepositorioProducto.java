package com.ecommerce.productoservice.repositorio;

import com.ecommerce.productoservice.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioProducto extends JpaRepository<Producto, Long> {
}
