package com.assignment.account.management.config;

import io.swagger.v3.oas.models.OpenAPI;
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
                        .title("Account transaction management API")
                        .version("1.0")
                        .description("account transaction management service.")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
