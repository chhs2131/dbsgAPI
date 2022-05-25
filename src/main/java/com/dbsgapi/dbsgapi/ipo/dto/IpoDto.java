package com.dbsgapi.dbsgapi.ipo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class IpoDto {
    // 기본 정보
    private long ipoIndex;
    private String stockName;
    private String stockExchange;
    private String stockKinds;
    private String stockCode;
    private String dartCode;
    private String sector;
    private long profits;
    private long sales;

    // 일정 정보
    private String ipoForecastStart;
    private String ipoForecastEnd;
    private String ipoStartDate;
    private String ipoEndDate;
    private String ipoRefundDate;
    private String ipoDebutDate;

    private String ipoCancelBool;
    private String ipoCancelDate;
    private String ipoCancelReason;

    // 공모 정보
    private long numberOfIpoShares;
    private double lockUpPercent;
    private double ipoInstitutionalAcceptanceRate;
    private int ipoPrice;
    private int ipoPriceLow;
    private int ipoPriceHigh;
    private int ipoMinDeposit;
    private String putBackOptionWho;
    private int putBackOptionPrice;
    private String putBackOptionDeadline;


    // private String underwriter;
    private String tag;
    private String registDate;
    private String updateDate;
    private String terminateDate;

    public void setIpoPriceHigh(int ipoPriceHigh) {
        // 실권주에 확정공모가가 아직 없는 경우, 확정이전가격(ipo_price_high)을 확정공모가로 반환해준다.
        this.ipoPriceHigh = ipoPriceHigh;
        if(this.ipoPrice == 0 && this.stockKinds.equals("실권주")) {
            this.ipoPrice = this.ipoPriceHigh;
        }
    }

    @JsonIgnore
    public String getTerminateDate() {return this.terminateDate;}
}
