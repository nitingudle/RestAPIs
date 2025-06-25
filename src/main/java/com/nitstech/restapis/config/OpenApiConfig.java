package com.nitstech.restapis.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI restApiOpenAPI() {
        return new OpenAPI().info(new Info().title("Product REST API").version("v1")
                .description("CRUD operations for Products").contact(new Contact().name("Nitin Gudle").email("nitin.gudle@gmail.com")));
    }

    @Bean
    public GroupedOpenApi productGroup() {
        return GroupedOpenApi.builder().group("Products").pathsToMatch("/api/v1/products/**").build();
    }
}
