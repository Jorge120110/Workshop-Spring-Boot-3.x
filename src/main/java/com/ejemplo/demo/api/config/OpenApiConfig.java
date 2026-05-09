package com.ejemplo.demo.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI workshopOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Workshop Spring Boot API")
                        .version("1.0.0")
                        .description("API del taller Spring Boot 3.x con validacion, manejo de errores, Swagger, JPA y contrato OpenAPI.")
                        .contact(new Contact()
                                .name("Jorge Mario Cano Cobon")
                                .email("jcanoc5@miumg.edu.gt")));
    }
}
