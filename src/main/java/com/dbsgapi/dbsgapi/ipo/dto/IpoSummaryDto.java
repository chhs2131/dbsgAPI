package com.dbsgapi.dbsgapi.ipo.dto;

import lombok.Data;

@Data
public class IpoSummaryDto {
    private long ipoIndex;
    private String stockName;
    private String stockExchange;
    private String stockKinds;

    private String ipoStartDate;
    private String ipoEndDate;
    private String ipoRefundDate;
    private String ipoDebutDate;

    private String underwriter;
    private String tag;
}
