package com.dbsgapi.dbsgapi.global.infra.api.kakao;

import com.dbsgapi.dbsgapi.api.auth.dto.KakaoErrorDto;
import com.dbsgapi.dbsgapi.api.auth.dto.KakaoProfileDto;
import com.dbsgapi.dbsgapi.api.auth.dto.KakaoTokenInfoDto;
import com.dbsgapi.dbsgapi.api.auth.exception.KakaoApiErrorCode;
import com.dbsgapi.dbsgapi.api.auth.exception.KakaoApiException;
import com.dbsgapi.dbsgapi.global.configuration.properties.SocialProperty;
import com.dbsgapi.dbsgapi.global.configuration.properties.type.ApiProvider;
import com.dbsgapi.dbsgapi.global.configuration.properties.type.ApiRequest;
import com.dbsgapi.dbsgapi.global.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoApiService {
    private final WebClientUtil webClientUtil;
    private final SocialProperty socialProperty;

    public Mono<KakaoTokenInfoDto> getTokenInformation(String kakaoAccessToken) {
        KakaoApiProvider apiProvider = socialProperty.getKakao();
        ApiRequest apiRequest = apiProvider.getToken();

        Consumer<HttpHeaders> headersConsumer = headers -> {
            headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + kakaoAccessToken);
        };

        return getKakaoMono(apiProvider, apiRequest, KakaoTokenInfoDto.class, headersConsumer);
    }

    public Mono<KakaoProfileDto> getProfile(String kakaoAccessToken) {
        KakaoApiProvider apiProvider = socialProperty.getKakao();
        ApiRequest apiRequest = apiProvider.getProfile();

        Consumer<HttpHeaders> headersConsumer = headers -> {
            headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + kakaoAccessToken);
        };

        return getKakaoMono(apiProvider, apiRequest, KakaoProfileDto.class, headersConsumer);
    }

    private <T> Mono<T> getKakaoMono(ApiProvider apiProvider, ApiRequest apiRequest, Class<T> dtoType, Consumer<HttpHeaders> headers) {
        WebClient.RequestHeadersSpec<?> requestHeadersSpec = webClientUtil.newRequestWebClient(apiProvider, apiRequest, headers);

        return requestHeadersSpec.retrieve()
                .onStatus(HttpStatus::is4xxClientError, response ->
                        response.bodyToMono(KakaoErrorDto.class)
                                .flatMap(errorDto -> Mono.error(new KakaoApiException(KakaoApiErrorCode.from(errorDto))))
                )
                .onStatus(HttpStatus::is5xxServerError, response -> {
                    throw new KakaoApiException(KakaoApiErrorCode.SYSTEM_CHECK_ERROR);
                })
                .bodyToMono(dtoType);
    }
}
