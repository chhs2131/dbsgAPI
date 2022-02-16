package com.dbsgapi.dbsgapi.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description ="즐겨찾기에 대한 정보를 저장합니다.")
@Data
public class MemberFavoriteDto {
    @Schema(description ="종목의 고유 index번호", example = "12345")
    private long favoriteIndex;
    @Schema(description ="DB에서 관리하는 회원번호", example = "100077", required=true)
    private long userNo;
    @Schema(description ="분류명", example = "ipo", allowableValues = {"ipo", "미정"}, required=true)
    private String kind;
    @Schema(description ="종목의 고유 index번호", example = "12345", required=true)
    private long kindNo;
    @Schema(description ="등록일자")
    private String registDate;
}
