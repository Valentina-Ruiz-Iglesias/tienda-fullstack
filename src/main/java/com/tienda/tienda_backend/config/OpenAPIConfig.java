package com.tienda.tienda_backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI tiendaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Tienda API - Backend")
                        .description("API REST para autenticación, productos y órdenes de compra.")
                        .version("1.0.0")
                );
    }
}
