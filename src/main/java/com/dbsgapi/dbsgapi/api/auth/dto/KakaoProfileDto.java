package com.dbsgapi.dbsgapi.api.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@NoArgsConstructor
public class KakaoProfileDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("connected_at")
    private String connectedAt;

    //    @JsonProperty("kakao_account")
    //    private KakaoAccount kakaoAccount;
    //    @JsonProperty("properties")
    //    private Properties properties;

    @Data
    @NoArgsConstructor
    public static class KakaoAccount {
        @JsonProperty("profile")
        private Profile profile;
        @JsonProperty("profile_image_needs_agreement")
        private boolean profileImageNeedsAgreement;
        @JsonProperty("profile_nickname_needs_agreement")
        private boolean profileNicknameNeedsAgreement;
    }

    @Data
    @NoArgsConstructor
    public static class Profile {
        @JsonProperty("is_default_image")
        private boolean isDefaultImage;
        @JsonProperty("profile_image_url")
        private String profileImageUrl;
        @JsonProperty("thumbnail_image_url")
        private String thumbnailImageUrl;
        @JsonProperty("nickname")
        private String nickname;
    }

    @Data
    @NoArgsConstructor
    public static class Properties {
        @JsonProperty("thumbnail_image")
        private String thumbnailImage;
        @JsonProperty("profile_image")
        private String profileImage;
        @JsonProperty("nickname")
        private String nickname;
    }
}
