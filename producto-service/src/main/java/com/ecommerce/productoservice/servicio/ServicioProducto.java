package com.ecommerce.productoservice.servicio;

import com.ecommerce.productoservice.dto.RespuestaProducto;
import com.ecommerce.productoservice.dto.SolicitudProducto;
import com.ecommerce.productoservice.modelo.Producto;
import com.ecommerce.productoservice.repositorio.RepositorioProducto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicioProducto {

    private final RepositorioProducto repositorio;

    public ServicioProducto(RepositorioProducto repositorio) {
        this.repositorio = repositorio;
    }

    @Transactional(readOnly = true)
    public List<RespuestaProducto> listar() {
        return repositorio.findAll().stream().map(RespuestaProducto::desde).toList();
    }

    @Transactional(readOnly = true)
    public RespuestaProducto obtener(Long id) {
        return RespuestaProducto.desde(buscar(id));
    }

    @Transactional
    public RespuestaProducto crear(SolicitudProducto solicitud) {
        Producto producto = new Producto(
                solicitud.nombre(), solicitud.descripcion(), solicitud.precio(), solicitud.stock());
        return RespuestaProducto.desde(repositorio.save(producto));
    }

    @Transactional
    public RespuestaProducto actualizar(Long id, SolicitudProducto solicitud) {
        Producto producto = buscar(id);
        producto.actualizar(solicitud.nombre(), solicitud.descripcion(), solicitud.precio(), solicitud.stock());
        return RespuestaProducto.desde(producto);
    }

    @Transactional
    public void eliminar(Long id) {
        repositorio.delete(buscar(id));
    }

    private Producto buscar(Long id) {
        return repositorio.findById(id).orElseThrow(() -> new ProductoNoEncontradoException(id));
    }
}
