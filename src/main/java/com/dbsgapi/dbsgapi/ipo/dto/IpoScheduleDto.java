package com.dbsgapi.dbsgapi.ipo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description ="[deprecated] 공모주의 일정관련 정보만 제공합니다.")
public class IpoScheduleDto {
    // TODO deprecated 2022-05-14 ipoSummaryDto를 공동으로 사용하는 것으로 대체됨.
    private long ipoIndex;
    private String stockName;
    @Schema(description ="수요예측 시작일")
    private String ipoForecastStart;
    @Schema(description ="수요예측 마감일")
    private String ipoForecastEnd;
    @Schema(description ="공모 청약 시작일")
    private String ipoStartDate;
    @Schema(description ="공모 청약 마감일")
    private String ipoEndDate;
    @Schema(description ="환불일")
    private String ipoRefundDate;
    @Schema(description ="상장일")
    private String ipoDebutDate;
}
