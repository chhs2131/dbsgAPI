package com.dbsgapi.dbsgapi.api.auth.dto;

import com.dbsgapi.dbsgapi.global.authentication.OauthType;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberOauthAccountDto {
    private String oauthId;
    private OauthType oauthType;
    private String memberUuid;
    private LocalDateTime registDatetime;

    public void setOauthType(String oauthType) {
        this.oauthType = OauthType.valueOf(oauthType);
    }

    public int getOauthType() {
        return oauthType.getId();
    }
}
