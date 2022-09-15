package com.dbsgapi.dbsgapi.global.configuration.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "swagger")
public final class SwaggerProperty {
    private final String title;
    private final String version;
    private final String description;
}
