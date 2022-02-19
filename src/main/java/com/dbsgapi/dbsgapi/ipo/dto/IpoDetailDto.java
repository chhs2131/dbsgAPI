package com.dbsgapi.dbsgapi.ipo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description ="종목에 대한 상세 정보를 제공합니다.")
@Data
public class IpoDetailDto {
    @Schema(description ="IPO 기본정보")
    private IpoDto ipo;
    @Schema(description ="주간사 정보")
    private IpoUnderwriterDto underwriter;
    @Schema(description ="코멘트(히스토리) 정보")
    private List<IpoCommentDto> comment;
}
