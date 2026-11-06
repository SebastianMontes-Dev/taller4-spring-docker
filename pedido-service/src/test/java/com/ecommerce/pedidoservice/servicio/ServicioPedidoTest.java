package com.ecommerce.pedidoservice.servicio;

import com.ecommerce.pedidoservice.cliente.ClienteProductos;
import com.ecommerce.pedidoservice.cliente.ProductoRemoto;
import com.ecommerce.pedidoservice.dto.RespuestaPedido;
import com.ecommerce.pedidoservice.dto.SolicitudPedido;
import com.ecommerce.pedidoservice.modelo.Pedido;
import com.ecommerce.pedidoservice.repositorio.RepositorioPedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServicioPedidoTest {

    @Mock
    RepositorioPedido repositorio;

    @Mock
    ClienteProductos clienteProductos;

    @InjectMocks
    ServicioPedido servicio;

    @Test
    void calculaElTotalConElPrecioDelProducto() {
        when(clienteProductos.obtener(1L))
                .thenReturn(new ProductoRemoto(1L, "Teclado", new BigDecimal("100000"), 10));
        when(repositorio.save(any(Pedido.class))).thenAnswer(inv -> inv.getArgument(0));

        RespuestaPedido respuesta = servicio.crear(new SolicitudPedido(1L, 3));

        assertEquals(new BigDecimal("300000"), respuesta.total());
        assertEquals("Teclado", respuesta.producto());
    }

    @Test
    void rechazaElPedidoSiNoHayStockSuficiente() {
        when(clienteProductos.obtener(1L))
                .thenReturn(new ProductoRemoto(1L, "Teclado", new BigDecimal("100000"), 2));

        assertThrows(StockInsuficienteException.class,
                () -> servicio.crear(new SolicitudPedido(1L, 5)));
        verify(repositorio, never()).save(any());
    }

    @Test
    void obtenerLanzaExcepcionSiElPedidoNoExiste() {
        when(repositorio.findById(7L)).thenReturn(Optional.empty());

        assertThrows(PedidoNoEncontradoException.class, () -> servicio.obtener(7L));
    }
}
