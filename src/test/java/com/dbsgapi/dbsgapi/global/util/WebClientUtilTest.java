package com.dbsgapi.dbsgapi.global.util;

import com.dbsgapi.dbsgapi.api.auth.dto.KakaoProfileDto;
import com.dbsgapi.dbsgapi.api.auth.dto.KakaoTokenInfoDto;
import com.dbsgapi.dbsgapi.global.configuration.properties.SocialProperty;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

class WebClientUtilTest {
    @Test
    public void 카카오API_카카오토큰정보_정상() {
        String kakaoAccessToken = "H_ynK86-Kq1oYxNDb5pgyLZLICJYUxRmW9B9nfWGCj10mQAAAYY-boZa";
        String baseUrl = "https://kapi.kakao.com";
        String path = "/v1/user/access_token_info";
        // String method = "GET";

        Mono<String> mono = WebClient.create()
                .get()
                .uri(baseUrl + path)
                .header("Authorization", "Bearer " + kakaoAccessToken)
                .retrieve()
                .bodyToMono(String.class);

        String result = mono.block();
        System.out.println("result: " + result);
        // endsWith: "id":2060431901,"expires_in":38033,"app_id":658763,"appId":658763}
    }

    @Test
    public void 카카오API_카카오계정정보RawString_정상() {
        String kakaoAccessToken = "H_ynK86-Kq1oYxNDb5pgyLZLICJYUxRmW9B9nfWGCj10mQAAAYY-boZa";
        String baseUrl = "https://kapi.kakao.com";
        String path = "/v2/user/me";

        Mono<String> mono = WebClient.create()
                .get()
                .uri(baseUrl + path)
                .header("Authorization", "Bearer " + kakaoAccessToken)
                .retrieve()
                .bodyToMono(String.class);

        String result = mono.block();
        System.out.println("result: " + result);
        System.out.println("=============================================================");
    }

    @Test
    public void 카카오API_카카오계정정보toKakaoProfileDto_정상() {
        String kakaoAccessToken = "H_ynK86-Kq1oYxNDb5pgyLZLICJYUxRmW9B9nfWGCj10mQAAAYY-boZa";
        String baseUrl = "https://kapi.kakao.com";
        String path = "/v2/user/me";

        Mono<KakaoProfileDto> mono = WebClient.create()
                .get()
                .uri(baseUrl + path)
                .header("Authorization", "Bearer " + kakaoAccessToken)
                .retrieve()
                .bodyToMono(KakaoProfileDto.class);

        KakaoProfileDto result = mono.block();
        assert result != null;
        System.out.println("result: " + result.toString());
        System.out.println("=============================================================");
    }
}