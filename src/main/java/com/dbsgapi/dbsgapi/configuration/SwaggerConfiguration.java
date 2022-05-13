package com.dbsgapi.dbsgapi.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("DBSG API")
                        .description("주식알리미 서비스에 사용되는 API 명세입니다.")
                        .version("v0.0.6")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
