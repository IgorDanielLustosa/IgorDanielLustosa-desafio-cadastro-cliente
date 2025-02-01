package com.example.desafio_cadastro_cliente.cadastros.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.desafio_cadastro_cliente"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API Cadastro de Clientes")
                .description("Spring Boot application para cadastro de clientes e funcionários de uma biblioteca")
                .version("v0.0.1")
                .contact(new Contact("Igor Daniel",
                        "https://www.linkedin.com/in/igor-daniel-lustosa-934a33214/",
                        "igordaniel1123@gmail.com"))
                .license("Apache 2.0")
                .licenseUrl("http://springdoc.org")
                .build();
    }

}
