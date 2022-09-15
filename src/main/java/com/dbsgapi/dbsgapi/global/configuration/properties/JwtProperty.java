package com.dbsgapi.dbsgapi.global.configuration.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "jwt")
public final class JwtProperty {
    private final String secret;
    private final long tokenValidTime;
    private final String authoritiesKey;
    private final String header;
}
