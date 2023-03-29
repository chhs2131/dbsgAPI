package com.dbsgapi.dbsgapi.api.auth.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@NoArgsConstructor
public class KakaoErrorDto {
    private int code;
    private String msg;
}
