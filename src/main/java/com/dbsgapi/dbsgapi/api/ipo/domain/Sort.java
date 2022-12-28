package com.dbsgapi.dbsgapi.api.ipo.domain;

import java.util.Arrays;
import java.util.List;

public enum Sort {
    ASC(Arrays.asList("ASC", "asc")),
    DESC(Arrays.asList("DESC", "desc"));

    private final List<String> values;

    Sort(List<String> values) {
        this.values = values;
    }

    public static Sort from(String value) {
        return Arrays.stream(Sort.values())
                .filter(sort -> sort.getValues().contains(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 정렬값입니다. 변환 불가 " + value));
    }

    public List<String> getValues() {
        return values;
    }

    public String getName() {
        return this.name();
    }
}
