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
import com.dbsgapi.dbsgapi.global.infra.api.kakao.KakaoApiService;
import com.dbsgapi.dbsgapi.global.util.JwtUtil;
import com.dbsgapi.dbsgapi.global.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoOauthServiceImpl implements KakaoOauthService {
    private final KakaoApiService kakaoApiService;
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
        return kakaoApiService.getTokenInformation(kakaoAccessToken).block();
    }

    private KakaoProfileDto getProfile(String kakaoAccessToken) {
        return kakaoApiService.getProfile(kakaoAccessToken).block();
    }
}
