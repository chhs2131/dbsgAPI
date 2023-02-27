package com.dbsgapi.dbsgapi.global.configuration.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "social")
public class SocialProperty {
    private final socialApi kakao;

    @Getter
    @RequiredArgsConstructor
    public static final class socialApi {
        private final String key;
        private final String baseUrl;
        private final apiUri token;
        private final apiUri profile;
    }

    @Getter
    @RequiredArgsConstructor
    public static final class apiUri {
        private final String path;
        private final String method;
    }
}
