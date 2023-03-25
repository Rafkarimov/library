package com.nv.sberschool.library.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//TODO перестало работать
@Configuration
public class OpenApiConfig {

    //http://localhost:8080/swagger-ui/index.html#/
    @Bean
    public OpenAPI libraryProject() {
        return new OpenAPI()
                .info(new Info()
                        .title("Онлайн библиотека")
                        .description("Сервис, позволяющий арендовать книгу в онлайн библиотеке.")
                        .version("v0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                        .contact(new Contact().name("Nikita Veko")
                                .email("")
                                .url(""))
                );
    }
}

