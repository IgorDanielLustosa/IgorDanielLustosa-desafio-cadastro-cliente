package com.example.desafio_cadastro_cliente.cadastros.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class WebConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofMillis(3000)) // Define o tempo de conexão
                .setReadTimeout(Duration.ofMillis(3000)) // Define o tempo de leitura
                .build();
    }
}
