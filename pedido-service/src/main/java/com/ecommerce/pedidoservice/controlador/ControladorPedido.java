package com.ecommerce.pedidoservice.controlador;

import com.ecommerce.pedidoservice.dto.RespuestaPedido;
import com.ecommerce.pedidoservice.dto.SolicitudPedido;
import com.ecommerce.pedidoservice.servicio.ServicioPedido;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class ControladorPedido {

    private final ServicioPedido servicio;

    public ControladorPedido(ServicioPedido servicio) {
        this.servicio = servicio;
    }

    @PostMapping
    public ResponseEntity<RespuestaPedido> crear(@Valid @RequestBody SolicitudPedido solicitud) {
        RespuestaPedido creado = servicio.crear(solicitud);
        return ResponseEntity.created(URI.create("/api/pedidos/" + creado.id())).body(creado);
    }

    @GetMapping
    public List<RespuestaPedido> listar() {
        return servicio.listar();
    }

    @GetMapping("/{id}")
    public RespuestaPedido obtener(@PathVariable Long id) {
        return servicio.obtener(id);
    }
}
