package com.dbsgapi.dbsgapi.api.ipo.domain;

public enum StockKinds {
    IPO("공모주"),
    FORFEITED("실권주"),
    SPEC("스팩주"),
    REIT("리츠주");

    private final String name;

    StockKinds(String name) {
        this.name = name;
    }
}
