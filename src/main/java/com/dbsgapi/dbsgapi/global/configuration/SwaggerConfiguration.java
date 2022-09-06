package com.dbsgapi.dbsgapi.global.configuration;

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
                        .description("주식알리미 서비스에 사용되는 API 명세입니다.<br/>" +
                                "<a href='https://www.notion.so/8c74d32d5e9846d698757d8b23cb0108?v=065595755f7645028e0a191479d986fa'>서버 변동사항 정리</a>")
                        .version("v0.0.13")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
