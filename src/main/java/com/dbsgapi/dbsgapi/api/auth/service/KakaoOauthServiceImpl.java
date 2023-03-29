package com.dbsgapi.dbsgapi.api.auth.service;

import com.dbsgapi.dbsgapi.api.auth.dto.KakaoProfileDto;
import com.dbsgapi.dbsgapi.api.auth.dto.MemberOauthAccountDto;
import com.dbsgapi.dbsgapi.api.auth.mapper.AuthMapper;
import com.dbsgapi.dbsgapi.api.member.dto.MemberDto;
import com.dbsgapi.dbsgapi.api.member.mapper.MemberMapper;
import com.dbsgapi.dbsgapi.global.authentication.AuthResponse;
import com.dbsgapi.dbsgapi.global.authentication.MemberPermission;
import com.dbsgapi.dbsgapi.global.authentication.OauthType;
import com.dbsgapi.dbsgapi.global.infra.api.kakao.KakaoApiService;
import com.dbsgapi.dbsgapi.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

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
        KakaoProfileDto kakaoProfile = kakaoApiService.getProfile(kakaoAccessToken).block();

        MemberDto member = authMapper.getMemberByOauthAccount(
                MemberOauthAccountDto.builder()
                .oauthType(OauthType.KAKAO)
                .oauthId(String.valueOf(kakaoProfile.getId()))
                .build()
        );
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
}
