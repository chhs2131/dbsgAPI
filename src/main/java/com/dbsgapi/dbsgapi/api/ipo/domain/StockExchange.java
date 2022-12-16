package com.dbsgapi.dbsgapi.api.ipo.domain;

public enum StockExchange {
    KOSPI("코스피"),
    KOSDAQ("코스닥"),
    KONEX("코넥스"),
    ETC("기타");

    private final String name;

    StockExchange(String name) {
        this.name = name;
    }
}
