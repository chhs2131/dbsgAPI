package com.dbsgapi.dbsgapi.api.ipo.domain;

import java.util.Arrays;

public enum StockKinds {
    IPO("공모주"),
    FORFEITED("실권주"),
    SPEC("스팩주"),
    REIT("리츠주");

    private final String name;

    StockKinds(String name) {
        this.name = name;
    }

    public boolean isIpo() {
        return this == IPO;
    }

    public boolean isForfeited() {
        return this == FORFEITED;
    }

    public boolean isSpec() {
        return this == SPEC;
    }

    public boolean isReit() {
        return this == REIT;
    }

    public static StockKinds from(String name) {
        return Arrays.stream(StockKinds.values())
                .filter(stockKinds -> stockKinds.getName().equals(name))
                .findAny()
                .orElse(null);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
