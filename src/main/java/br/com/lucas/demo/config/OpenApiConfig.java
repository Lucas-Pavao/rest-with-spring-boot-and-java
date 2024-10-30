package br.com.lucas.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPi(){
        return new OpenAPI()
                .info(new Info()
                        .title("RestFull Api with java")
                        .version("v1")
                        .description("RestFull Api with java")
                        .termsOfService("https://github.com/Lucas-Pavao")
                        .license(new License().name("Apache 2.0")
                                .url("https://github.com/Lucas-Pavao")
                        ));
    }
}
