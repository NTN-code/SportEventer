package com.spring.server.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(
                new Info()
                    .title("Open-API documentation for BSTU EVENT")
                    .description("Моя работа над проектом информационного портала, p.s сделал @AntonTkachou")
                    .version("7.7.7")
                    .contact(
                        new Contact()
                            .email("belstu2lab@gmail.com")
                            .url("https://github.com/NTN-code")
                            .name("Anton Tkachou")
                    )
            );
    }
}