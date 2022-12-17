package com.dbsgapi.dbsgapi.api.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "옵션에 대한 정보를 저장합니다.")
@Data
public class MemberOptionDto {
    private long userNo;
}
