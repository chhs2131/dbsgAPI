package com.dbsgapi.dbsgapi.global.configuration;

import com.dbsgapi.dbsgapi.global.configuration.properties.SwaggerProperty;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfiguration {
    final SwaggerProperty swaggerProperty;

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI().info(new Info()
                        .title(swaggerProperty.getTitle())
                        .description(swaggerProperty.getDescription())
                        .version(swaggerProperty.getVersion())
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
