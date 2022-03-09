package com.dbsgapi.dbsgapi.ipo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)  // NON_NULL -> NON_ABSENT -> NON_EMPTY -> NON_DEFAULT
public class IpoUnderwriterDto {
    private long ipoIndex;
    private String updateDate;
    private String underName;
    private long indTotalMax;
    private long indTotalMin;
    private long indCanMax;
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
