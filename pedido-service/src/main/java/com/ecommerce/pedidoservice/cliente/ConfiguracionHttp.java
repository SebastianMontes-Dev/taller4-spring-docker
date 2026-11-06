package com.ecommerce.pedidoservice.cliente;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ConfiguracionHttp {

    @Bean
    @LoadBalanced
    RestClient.Builder restClientBalanceado() {
        return RestClient.builder();
    }
}
