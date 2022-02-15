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
    private long stockCode;
    private long dartCode;
    private long sectorCode;
    private long profits;
    private long sales;

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
    private int ipoMinDeposit;

    private String underwriter;
    private String putBackOptionWho;
    private int putBackOptionPrice;
    private String putBackOptionDeadline;
    private String tag;
    private String registDate;
    private String updateDate;
}
