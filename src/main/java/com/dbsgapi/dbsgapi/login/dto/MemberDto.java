package com.dbsgapi.dbsgapi.login.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class MemberDto {
    @ApiParam(value="DBSG서버에서 관리하는 유저 고유식별번호", required=true)
    private long userNo;
    @ApiParam(value="유저 권한")
    private String roleName;
    @ApiParam(value="사용가능여부")
    private String enabled;
    @ApiParam(value="가입일자")
    private String registDate;
}
