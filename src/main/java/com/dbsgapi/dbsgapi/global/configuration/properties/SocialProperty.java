package com.dbsgapi.dbsgapi.global.configuration.properties;

import com.dbsgapi.dbsgapi.global.configuration.properties.type.ApiProvider;
import com.dbsgapi.dbsgapi.global.configuration.properties.type.ApiRequest;
import com.dbsgapi.dbsgapi.global.infra.api.kakao.KakaoApiProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "social")
public class SocialProperty {
    private final KakaoApiProvider kakao;
}
