package com.dbsgapi.dbsgapi.ipo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Data;

@Data
public class IpoDto {
    private long ipoIndex;
    private String stockName;
    private String stockExchange;
    private String stockKinds;
    private String stockCode;
    private String dartCode;
    private String sector;
    private long profits;
    private long sales;

    private String ipoForecastDate;
    private String ipoStartDate;
    private String ipoEndDate;
    private String ipoRefundDate;
    private String ipoDebutDate;

    private long numberOfIpoShares;
    private double lockUpPercent;
    private double ipoInstitutionalAcceptanceRate;
    private int ipoPrice;
    private int ipoPriceLow;
    private int ipoPriceHigh;
    private int ipoMinDeposit;

    // private String underwriter;
    private String putBackOptionWho;
    private int putBackOptionPrice;
    private String putBackOptionDeadline;
    private String tag;
    private String registDate;
    private String updateDate;

    public void setIpoPriceHigh(int ipoPriceHigh) {
        // 실권주에 확정공모가가 아직 없는 경우, 확정이전가격(ipo_price_high)을 확정공모가로 반환해준다.
        this.ipoPriceHigh = ipoPriceHigh;
        if(this.ipoPrice == 0 && this.stockKinds.equals("실권주")) {
            this.ipoPrice = this.ipoPriceHigh;
        }
    }
}
