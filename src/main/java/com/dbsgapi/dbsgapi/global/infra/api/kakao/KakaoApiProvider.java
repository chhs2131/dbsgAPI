package com.dbsgapi.dbsgapi.global.infra.api.kakao;

import com.dbsgapi.dbsgapi.global.configuration.properties.type.ApiProvider;
import com.dbsgapi.dbsgapi.global.configuration.properties.type.ApiRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class KakaoApiProvider extends ApiProvider {
    private ApiRequest token;
    private ApiRequest profile;
}
