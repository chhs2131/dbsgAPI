package com.dbsgapi.dbsgapi.ipo.dto;

        import lombok.Data;

@Data
public class IpoDto {
    private long ipoIndex;
    private String stockName;
    private long stockCode;
    private long dartCode;
    private String stockExchange;

    private String ipoForecastDate;
    private String ipoStartDate;
    private String ipoEndDate;
    private String ipoRefundDate;
    private String ipoDebutDate;

    private double lockUpPercent;
    private double ipoInstitutionalAcceptanceRate;
    private int ipoPrice;
    private int ipoPriceLow;
    private int ipoPriceHigh;

    private String underwriter;
    private String putBackOptionWho;
    private int putBackOptionPrice;
    private String putBackOptionDeadline;
    private String tag;
}
