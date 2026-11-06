package com.ecommerce.pedidoservice.cliente;

import com.ecommerce.pedidoservice.servicio.CatalogoNoDisponibleException;
import com.ecommerce.pedidoservice.servicio.ProductoInexistenteException;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

/** Consulta producto-service por su nombre en Eureka; el balanceador reparte entre las replicas. */
@Component
public class ClienteProductos {

    private final RestClient cliente;

    public ClienteProductos(@LoadBalanced RestClient.Builder builder) {
        this.cliente = builder.baseUrl("http://producto-service").build();
    }

    public ProductoRemoto obtener(Long id) {
        try {
            return cliente.get().uri("/api/productos/{id}", id).retrieve().body(ProductoRemoto.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ProductoInexistenteException(id);
        } catch (RestClientException e) {
            throw new CatalogoNoDisponibleException(e);
        }
    }
}
