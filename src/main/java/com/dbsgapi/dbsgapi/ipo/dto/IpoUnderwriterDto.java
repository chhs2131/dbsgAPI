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

    /*
    //depreacted. 20220309
    private String registDate;
    private String nhIndTotal;
    private String nhIndCan;
    private String koreaIndTotal;
    private String koreaIndCan;
    private String shinhanIndTotal;
    private String shinhanIndCan;
    private String hanaIndTotal;
    private String hanaIndCan;
    private String kiwoomIndTotal;
    private String kiwoomIndCan;
    private String miraeIndTotal;
    private String miraeIndCan;
    private String kbIndTotal;
    private String kbIndCan;
    private String kakaopayIndTotal;
    private String kakaopayIndCan;
    private String daishinIndTotal;
    private String daishinIndCan;
    private String bookookIndTotal;
    private String bookookIndCan;
    private String samsungIndTotal;
    private String samsungIndCan;
    private String tossIndTotal;
    private String tossIndCan;
    private String hanwhaIndTotal;
    private String hanwhaIndCan;
    private String eugeneIndTotal;
    private String eugeneIndCan;
    private String ebestIndTotal;
    private String ebestIndCan;
    private String dbIndTotal;
    private String dbIndCan;
    private String hiIndTotal;
    private String hiIndCan;
    private String yuantaIndTotal;
    private String yuantaIndCan;
    private String meritzIndTotal;
    private String meritzIndCan;
    private String kyoboIndTotal;
    private String kyoboIndCan;
    private String skIndTotal;
    private String skIndCan;
    private String hyundaimotorIndTotal;
    private String hyundaimotorIndCan;
    private String fossIndTotal;
    private String fossIndCan;
    private String shinyoungIndTotal;
    private String shinyoungIndCan;
    private String capeIndTotal;
    private String capeIndCan;
    private String ktbIndTotal;
    private String ktbIndCan;
    private String bnkIndTotal;
    private String bnkIndCan;
    private String ibkIndTotal;
    private String ibkIndCan;
     */
}
