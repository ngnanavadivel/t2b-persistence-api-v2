package com.t2b.api.persistence.cfg;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                                                      .apis(RequestHandlerSelectors.basePackage("com.t2b.api.persistence.web"))
                                                      .paths(PathSelectors.any())
                                                      .build()
                                                      .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("T2B Persistence API",
                           " API that does all CRUD operations on t2b business entities.",
                           "1.0",
                           "", // Terms of Service URL
                           contact(),
                           "", // License
                           "", // License URL
                           Collections.emptyList());
    }

    private Contact contact() {
        return new Contact("API Development Team",
                           "www.ticket2beauty.com",
                           "contact@t2b.com");
    }
}
