package com.dbsgapi.dbsgapi.api.login.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "사용자 계정 정보")
@Data
public class MemberDto {
    @Schema(description ="DBSG서버에서 관리하는 유저식별번호(uuid)", example = "100077", required=true)
    private long userNo;
    @Schema(description ="유저 권한", example = "USER")
    private String roleName;
    @Schema(description ="사용가능여부", example = "true")
    private String enabled;
    @Schema(description ="가입일자", example = "2022-01-01 12:27:10")
    private String registDate;
    @Schema(description ="Client측에 토큰값을 전달하기 위한 부분", example = "a1b2c3d4e5k6a1b2c3d4e5k6a1b2c3d4e5k6a ... c3d4e5k6")
    private String jwt;

    public void setForJwt(String userNo, String roleName, String jwt) {
        this.setUserNo(Long.parseLong(userNo));
        this.setRoleName(roleName);
        this.setJwt(jwt);
    }
}
