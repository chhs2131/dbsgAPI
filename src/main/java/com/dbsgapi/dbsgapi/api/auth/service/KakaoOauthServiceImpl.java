package com.dbsgapi.dbsgapi.api.auth.service;

import com.dbsgapi.dbsgapi.api.auth.dto.KakaoErrorDto;
import com.dbsgapi.dbsgapi.api.auth.dto.KakaoProfileDto;
import com.dbsgapi.dbsgapi.api.auth.dto.KakaoTokenInfoDto;
import com.dbsgapi.dbsgapi.api.auth.exception.KakaoApiErrorCode;
import com.dbsgapi.dbsgapi.api.auth.exception.KakaoApiException;
import com.dbsgapi.dbsgapi.api.member.dto.MemberDto;
import com.dbsgapi.dbsgapi.api.auth.dto.MemberOauthAccountDto;
import com.dbsgapi.dbsgapi.api.auth.mapper.AuthMapper;
import com.dbsgapi.dbsgapi.api.member.mapper.MemberMapper;
import com.dbsgapi.dbsgapi.global.authentication.AuthResponse;
import com.dbsgapi.dbsgapi.global.authentication.MemberPermission;
import com.dbsgapi.dbsgapi.global.authentication.OauthType;
import com.dbsgapi.dbsgapi.global.configuration.properties.SocialProperty;
import com.dbsgapi.dbsgapi.global.configuration.properties.type.ApiProvider;
import com.dbsgapi.dbsgapi.global.configuration.properties.type.ApiRequest;
import com.dbsgapi.dbsgapi.global.infra.api.kakao.KakaoApiProvider;
import com.dbsgapi.dbsgapi.global.infra.api.kakao.KakaoApiWebClient;
import com.dbsgapi.dbsgapi.global.util.JwtUtil;
import com.dbsgapi.dbsgapi.global.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoOauthServiceImpl implements KakaoOauthService {
    private final KakaoApiWebClient webClientUtil;
    private final SocialProperty socialProperty;
    private final AuthMapper authMapper;
    private final MemberMapper memberMapper;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponse login(String kakaoAccessToken) {
        KakaoProfileDto kakaoProfile = getProfile(kakaoAccessToken);
        if (kakaoProfile == null) {
            throw new IllegalArgumentException("유효하지않은 KakaoAccessToken 입니다.");
        }

        MemberDto member = authMapper.getMemberByOauthAccount(MemberOauthAccountDto.builder()
                .oauthType(OauthType.KAKAO)
                .oauthId(String.valueOf(kakaoProfile.getId()))
                .build());
        if (member == null) {
            // 신규 회원 생성
            member = MemberDto.builder()
                    .uuid(String.valueOf(UUID.randomUUID()))
                    .memberPermission(MemberPermission.USER)
                    .name("RANDOM_NAME" + LocalDateTime.now())
                    .registDatetime(LocalDateTime.now())
                    .build();
            memberMapper.createMember(member);
            log.debug("=>>> {}", member.toString());

            // Oauth 정보 등록
            MemberOauthAccountDto oauthAccount = MemberOauthAccountDto.builder()
                    .oauthId(String.valueOf(kakaoProfile.getId()))
                    .memberUuid(member.getUuid())
                    .oauthType(OauthType.KAKAO)
                    .registDatetime(LocalDateTime.now())
                    .build();
            authMapper.createOauthAccount(oauthAccount);
            log.debug("=>>> {}", oauthAccount.toString());
        }

        return jwtUtil.createAuthResponse(member.getUuid(), member.getMemberPermissionType());
    }

    private KakaoTokenInfoDto getTokenInformation(String kakaoAccessToken) {
        KakaoApiProvider apiProvider = socialProperty.getKakao();
        ApiRequest apiRequest = apiProvider.getToken();

        Consumer<HttpHeaders> headersConsumer = headers -> {
            headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + kakaoAccessToken);
        };

        Mono<KakaoTokenInfoDto> mono = getKakaoMono(apiProvider, apiRequest, KakaoTokenInfoDto.class, headersConsumer);
        return mono.block();
    }

    private KakaoProfileDto getProfile(String kakaoAccessToken) {
        KakaoApiProvider apiProvider = socialProperty.getKakao();
        ApiRequest apiRequest = apiProvider.getProfile();

        Consumer<HttpHeaders> headersConsumer = headers -> {
            headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + kakaoAccessToken);
        };

        Mono<KakaoProfileDto> mono = getKakaoMono(apiProvider, apiRequest, KakaoProfileDto.class, headersConsumer);
        return mono.block();
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
