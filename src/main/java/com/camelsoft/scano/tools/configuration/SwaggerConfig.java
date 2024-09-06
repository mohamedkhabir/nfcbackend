package com.camelsoft.scano.tools.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/api/**"))
                .apis(RequestHandlerSelectors.basePackage("com.camelsoft"))
                .build()
                .securitySchemes(Arrays.asList(apiKey()))
                .securityContexts(Arrays.asList(securityContext()))
                .apiInfo(apiInfo());
    }

    private ApiKey apiKey() {
        return new  ApiKey("JWT", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth())
                .forPaths(PathSelectors.any()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope(
                "global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT",
                authorizationScopes));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Scano REST API", //title
                "Scano public rest API.", //description
                "Version 1.0", //version
                "Terms of service", //terms of service URL
                new Contact("CAMELSOFT LLC", "www.camel-soft.com", "contact@camel-soft.com"),
                "License of API", "www.camel-soft.com", Collections.emptyList()); // contact info
    }
    //      mongodb+srv://mohamed123:mohamed123@cluster0.b5tct.mongodb.net/consulti?retryWrites=true&w=majority
    //mongodb://127.0.0.1:27017/consulti
}
