package com.ecommerce.productoservice.servicio;

import com.ecommerce.productoservice.dto.RespuestaProducto;
import com.ecommerce.productoservice.dto.SolicitudProducto;
import com.ecommerce.productoservice.modelo.Producto;
import com.ecommerce.productoservice.repositorio.RepositorioProducto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServicioProductoTest {

    @Mock
    RepositorioProducto repositorio;

    @InjectMocks
    ServicioProducto servicio;

    @Test
    void creaProductoYDevuelveSuRepresentacion() {
        when(repositorio.save(any(Producto.class))).thenAnswer(inv -> inv.getArgument(0));

        RespuestaProducto respuesta = servicio.crear(
                new SolicitudProducto("Teclado", "Mecanico", new BigDecimal("120000.00"), 10));

        assertEquals("Teclado", respuesta.nombre());
        assertEquals(10, respuesta.stock());
    }

    @Test
    void listaTodosLosProductos() {
        when(repositorio.findAll()).thenReturn(List.of(
                new Producto("Mouse", null, new BigDecimal("45000"), 5)));

        assertEquals(1, servicio.listar().size());
    }

    @Test
    void obtenerLanzaExcepcionSiNoExiste() {
        when(repositorio.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ProductoNoEncontradoException.class, () -> servicio.obtener(99L));
    }

    @Test
    void eliminarBorraElProductoExistente() {
        Producto producto = new Producto("Monitor", null, new BigDecimal("800000"), 2);
        when(repositorio.findById(1L)).thenReturn(Optional.of(producto));

        servicio.eliminar(1L);

        verify(repositorio).delete(producto);
    }
}
