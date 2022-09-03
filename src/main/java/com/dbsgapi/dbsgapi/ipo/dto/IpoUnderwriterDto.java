package com.dbsgapi.dbsgapi.ipo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)  // NON_NULL -> NON_ABSENT -> NON_EMPTY -> NON_DEFAULT
public class IpoUnderwriterDto {
    @Schema(description ="해당 종목 번호", example = "75", required=true)
    private long ipoIndex;
    @Schema(description ="업데이트 일자", example = "2022-03-31", required=true)
    private String updateDate;
    @Schema(description ="주간사 이름", example = "삼성증권㈜", required=true)
    private String underName;
    @Schema(description ="개인배정수량 최대", example = "135000")
    private long indTotalMax;
    @Schema(description ="개인배정수량 최소", example = "112500")
    private long indTotalMin;
    @Schema(description ="1인 청약가능수량 최대", example = "6500")
    private long indCanMax;
    @Schema(description ="1인 청약가능수량 최소", example = "5500")
    private long indCanMin;
    @Schema(description ="일반청약자 최소 청약수량 (균등)", example = "10")
    private int subMinQuan;
    @Schema(description ="일반청약자 증거금률", example = "50")
    private float subDepositPercent;
}
