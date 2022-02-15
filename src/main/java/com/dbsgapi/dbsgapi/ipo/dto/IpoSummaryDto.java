package com.dbsgapi.dbsgapi.ipo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description ="종목에 대한 요약된 정보를 제공합니다.")
@Data
public class IpoSummaryDto {
    @Schema(description ="DB에서 관리하는 index번호", example = "100077", required=true)
    private long ipoIndex;
    @Schema(description ="종목명", example = "LG에너지솔루션")
    private String stockName;
    @Schema(description ="시장명", example = "코스피", allowableValues = {"코스피","코스닥","코넥스"}, required=true)
    private String stockExchange;
    @Schema(description ="유형구분", example = "공모주", allowableValues = {"공모주","실권주","스팩주"})
    private String stockKinds;

    @Schema(description ="청약 시작일", example = "2022-01-18", required=true)
    private String ipoStartDate;
    @Schema(description ="청약 종료일", example = "2022-01-19")
    private String ipoEndDate;
    @Schema(description ="환불 및 배정일", example = "2022-01-21")
    private String ipoRefundDate;
    @Schema(description ="시장 상장일", example = "2022-01-27")
    private String ipoDebutDate;

    @Schema(description ="주간사", example = "KB증권,대신증권,신한금융,미래에셋증권,신영증권,하나금융,하이투자")
    private String underwriter;
    @Schema(description ="태그", example = "")
    private String tag;
    @Schema(description ="최근 발행된 코멘트", example = "수요예측이 끝났습니다! 결과를 확인해보세요.")
    private String recentComment;
}
