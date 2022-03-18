package com.dbsgapi.dbsgapi.ipo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description ="공모주의 일정관련 정보만 제공합니다.")
public class IpoScheduleDto {
    private long ipoIndex;
    private String stockName;
    @Schema(description ="수요예측일")
    private String ipoForecastDate;
    @Schema(description ="공모 청약 시작일")
    private String ipoStartDate;
    @Schema(description ="공모 청약 마감일")
    private String ipoEndDate;
    @Schema(description ="환불일")
    private String ipoRefundDate;
    @Schema(description ="상장일")
    private String ipoDebutDate;
}
