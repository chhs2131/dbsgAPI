package com.dbsgapi.dbsgapi.api.auth.service;

import com.dbsgapi.dbsgapi.api.auth.dto.KakaoProfileDto;
import com.dbsgapi.dbsgapi.api.auth.dto.KakaoTokenInfoDto;
import com.dbsgapi.dbsgapi.api.auth.mapper.AuthMapper;
import com.dbsgapi.dbsgapi.global.configuration.properties.SocialProperty;
import io.netty.handler.codec.http.HttpMethod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoOauthServiceImpl implements KakaoOauthService {
    private final SocialProperty socialProperty;
    private final AuthMapper authMapper;

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

    @Override
    public KakaoProfileDto getProfile(String kakaoAccessToken) {
        String baseUrl = socialProperty.getKakao().getBaseUrl();
        String path = socialProperty.getKakao().getProfile().getPath();
        String method = socialProperty.getKakao().getProfile().getMethod();

        Mono<KakaoProfileDto> mono = WebClient.create()
                .get()
                .uri(baseUrl + path)
                .header("Authorization", "Bearer " + kakaoAccessToken)
                .retrieve()
                .bodyToMono(KakaoProfileDto.class);

        return mono.block();
    }
}
