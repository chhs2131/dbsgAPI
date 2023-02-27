package com.dbsgapi.dbsgapi.api.auth.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KakaoTokenInfoDto {
    private long expiresInMillis;
    private long id;
    private long appId;
}
