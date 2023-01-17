package com.dbsgapi.dbsgapi.api.auth.service;

import com.dbsgapi.dbsgapi.api.auth.dto.KakaoTokenInfoDto;
import com.dbsgapi.dbsgapi.global.configuration.properties.SocialProperty;
import io.netty.handler.codec.http.HttpMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class KakaoOauthServiceImpl implements KakaoOauthService {
    private final SocialProperty socialProperty;

    @Override
    public KakaoTokenInfoDto getTokenInformation(String kakaoAccessToken) {
        String baseUrl = socialProperty.getKakao().getBaseUrl();
        String path = socialProperty.getKakao().getToken().getPath();
        String method = socialProperty.getKakao().getToken().getMethod();

        Mono<KakaoTokenInfoDto> mono = WebClient.create()
                .get()
                .uri(baseUrl + path)
                .header("Authorization", "Bearer " + kakaoAccessToken)
                .retrieve()
                .bodyToMono(KakaoTokenInfoDto.class);

        return mono.block();
    }
}
