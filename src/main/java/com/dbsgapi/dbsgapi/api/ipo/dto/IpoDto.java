package com.dbsgapi.dbsgapi.api.ipo.dto;

import com.dbsgapi.dbsgapi.api.ipo.domain.StockExchange;
import com.dbsgapi.dbsgapi.api.ipo.domain.StockKinds;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class IpoDto {
    //TODO 각 항목들을 collection 형태로 분리하기 (https://pooney.tistory.com/39)

    // 기본 정보
    private long ipoIndex;
    private String stockName;
    private StockExchange stockExchange;
    private StockKinds stockKinds;
    private String stockCode;
    private String dartCode;
    private String sectorCode;
    private String sector;
    private long profits;
    private long sales;

    // 일정 정보
    private String exStartDate;
    private String exEndDate;
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
    private String purposeOfFunds;
    private long numberOfIpoShares;
    private Double lockUpPercent;
    private Double ipoInstitutionalAcceptanceRate;
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

    public void setStockKinds(String stockKinds) {
        this.stockKinds = StockKinds.from(stockKinds);
    }

    public void setIpoPriceHigh(int ipoPriceHigh) {
        // 실권주에 확정공모가가 아직 없는 경우, 확정이전가격(ipo_price_high)을 확정공모가로 반환해준다.
        this.ipoPriceHigh = ipoPriceHigh;
        if (ipoPrice == 0 && stockKinds.isForfeited()) {
            ipoPrice = this.ipoPriceHigh;
        }
    }

    public void setStockExchange(String stockExchange) {
        this.stockExchange = StockExchange.from(stockExchange);
    }

    public String getStockExchange() {
        return stockExchange.getName();
    }

    public String getStockKinds() {
        if (stockKinds == null) {
            return null;
        }
        return stockKinds.getName();
    }

    @JsonIgnore
    public String getTerminateDate() {
        return this.terminateDate;
    }

    @JsonIgnore
    public String getSectorCode() {
        return this.sectorCode;
    }
}
