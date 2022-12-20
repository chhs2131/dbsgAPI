package com.dbsgapi.dbsgapi.api.ipo.domain;

import java.util.Arrays;

public enum Sort {
    ASC("asc"),
    DESC("desc");

    private final String value;

    Sort(String value) {
        this.value = value;
    }

    public static Sort from(String value) {
        return Arrays.stream(Sort.values())
                .filter(sort -> sort.getValue().equals(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 정렬값입니다. 변환 불가 " + value));
    }

    public String getValue() {
        return value;
    }
}
