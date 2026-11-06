package com.ecommerce.pedidoservice.servicio;

import com.ecommerce.pedidoservice.cliente.ClienteProductos;
import com.ecommerce.pedidoservice.cliente.ProductoRemoto;
import com.ecommerce.pedidoservice.dto.RespuestaPedido;
import com.ecommerce.pedidoservice.dto.SolicitudPedido;
import com.ecommerce.pedidoservice.modelo.Pedido;
import com.ecommerce.pedidoservice.repositorio.RepositorioPedido;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ServicioPedido {

    private final RepositorioPedido repositorio;
    private final ClienteProductos clienteProductos;

    public ServicioPedido(RepositorioPedido repositorio, ClienteProductos clienteProductos) {
        this.repositorio = repositorio;
        this.clienteProductos = clienteProductos;
    }

    @Transactional
    public RespuestaPedido crear(SolicitudPedido solicitud) {
        ProductoRemoto producto = clienteProductos.obtener(solicitud.productoId());
        if (producto.stock() < solicitud.cantidad()) {
            throw new StockInsuficienteException(producto.nombre(), producto.stock(), solicitud.cantidad());
        }
        BigDecimal total = producto.precio().multiply(BigDecimal.valueOf(solicitud.cantidad()));
        Pedido pedido = new Pedido(producto.id(), producto.nombre(), solicitud.cantidad(), total);
        return RespuestaPedido.desde(repositorio.save(pedido));
    }

    @Transactional(readOnly = true)
    public List<RespuestaPedido> listar() {
        return repositorio.findAll().stream().map(RespuestaPedido::desde).toList();
    }

    @Transactional(readOnly = true)
    public RespuestaPedido obtener(Long id) {
        return repositorio.findById(id)
                .map(RespuestaPedido::desde)
                .orElseThrow(() -> new PedidoNoEncontradoException(id));
    }
}
