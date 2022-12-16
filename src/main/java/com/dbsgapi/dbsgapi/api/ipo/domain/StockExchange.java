package com.dbsgapi.dbsgapi.api.ipo.domain;

import java.util.Arrays;

public enum StockExchange {
    KOSPI("코스피"),
    KOSDAQ("코스닥"),
    KONEX("코넥스"),
    ETC("기타");

    private final String name;

    StockExchange(String name) {
        this.name = name;
    }

    public static StockExchange from(String name) {
        return Arrays.stream(StockExchange.values())
                .filter(stockExchange -> stockExchange.getName().equals(name))
                .findAny()
                .orElse(null);
    }

    public String getName() {
        return name;
    }
}
