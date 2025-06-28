package com.lucascostabr.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Helpdesk API")
                        .description("API desenvolvida para gestão de chamados de suporte")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("lucascosta-br")
                                .email("lucascosta@gmail.com")
                                .url("https://github.com/lucascosta-br"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org"))
                );
    }

}
