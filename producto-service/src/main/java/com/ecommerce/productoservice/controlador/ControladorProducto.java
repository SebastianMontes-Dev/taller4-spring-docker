package com.ecommerce.productoservice.controlador;

import com.ecommerce.productoservice.dto.RespuestaProducto;
import com.ecommerce.productoservice.dto.SolicitudProducto;
import com.ecommerce.productoservice.servicio.ServicioProducto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
public class ControladorProducto {

    private final ServicioProducto servicio;

    public ControladorProducto(ServicioProducto servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public List<RespuestaProducto> listar() {
        return servicio.listar();
    }

    @GetMapping("/{id}")
    public RespuestaProducto obtener(@PathVariable Long id) {
        return servicio.obtener(id);
    }

    @PostMapping
    public ResponseEntity<RespuestaProducto> crear(@Valid @RequestBody SolicitudProducto solicitud) {
        RespuestaProducto creado = servicio.crear(solicitud);
        return ResponseEntity.created(URI.create("/api/productos/" + creado.id())).body(creado);
    }

    @PutMapping("/{id}")
    public RespuestaProducto actualizar(@PathVariable Long id, @Valid @RequestBody SolicitudProducto solicitud) {
        return servicio.actualizar(id, solicitud);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    /** Devuelve el contenedor que atendio la peticion; sirve para ver el balanceo entre replicas. */
    @GetMapping("/instancia")
    public Map<String, String> instancia() throws UnknownHostException {
        return Map.of("servicio", "producto-service", "instancia", InetAddress.getLocalHost().getHostName());
    }
}
