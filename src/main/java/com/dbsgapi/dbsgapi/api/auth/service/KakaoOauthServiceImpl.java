package com.dbsgapi.dbsgapi.api.auth.service;

import com.dbsgapi.dbsgapi.api.auth.dto.KakaoProfileDto;
import com.dbsgapi.dbsgapi.api.auth.dto.KakaoTokenInfoDto;
import com.dbsgapi.dbsgapi.api.auth.dto.MemberDto;
import com.dbsgapi.dbsgapi.api.auth.dto.MemberOauthAccountDto;
import com.dbsgapi.dbsgapi.api.auth.mapper.AuthMapper;
import com.dbsgapi.dbsgapi.global.authentication.MemberPermission;
import com.dbsgapi.dbsgapi.global.authentication.OauthType;
import com.dbsgapi.dbsgapi.global.configuration.properties.SocialProperty;
import io.netty.handler.codec.http.HttpMethod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoOauthServiceImpl implements KakaoOauthService {
    private final SocialProperty socialProperty;
    private final AuthMapper authMapper;

    @Override
    public MemberDto login(String kakaoAccessToken) {
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
            authMapper.createMember(member);
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
        return member;
    }

    private KakaoTokenInfoDto getTokenInformation(String kakaoAccessToken) {
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

    private KakaoProfileDto getProfile(String kakaoAccessToken) {
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
