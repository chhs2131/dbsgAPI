package com.dbsgapi.dbsgapi.api.ipo.domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class IpoPaging {
    private final int pageNumber;
    private final int numberOfArticles;
    private final IpoSequence ipoSequence;
    private final DatePeriod datePeriod;
    private final boolean withCancelItem;
    private final Sort sort;

    public IpoPaging(int pageNumber, int numberOfArticles, IpoSequence ipoSequence, LocalDate startDate,
                     LocalDate endDate, boolean withCancelItem, Sort sort) {
        this.pageNumber = pageNumber;
        this.numberOfArticles = numberOfArticles;
        this.ipoSequence = ipoSequence;
        this.datePeriod = DatePeriod.from(startDate, endDate);
        this.withCancelItem = withCancelItem;
        this.sort = sort;
    }

    public IpoPaging(int pageNumber, int numberOfArticles, IpoSequence ipoSequence, LocalDate targetDate,
                     boolean withCancelItem, Sort sort) {
        this.pageNumber = pageNumber;
        this.numberOfArticles = numberOfArticles;
        this.ipoSequence = ipoSequence;
        this.datePeriod = DatePeriod.from(targetDate);
        this.withCancelItem = withCancelItem;
        this.sort = sort;
    }

    public IpoPaging(int pageNumber, int numberOfArticles, IpoSequence ipoSequence, LocalDate targetDate,
                     LocalDate startDate, LocalDate endDate, boolean withCancelItem, Sort sort) {
        this.pageNumber = pageNumber;
        this.numberOfArticles = numberOfArticles;
        this.ipoSequence = ipoSequence;
        this.datePeriod = DatePeriod.from(targetDate, startDate, endDate);
        this.withCancelItem = withCancelItem;
        this.sort = sort;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("limit", numberOfArticles);
        map.put("offset", pageNumber * numberOfArticles - numberOfArticles);
        map.put("targetDate", datePeriod.getStartDate());
        map.put("startDate", datePeriod.getStartDate());
        map.put("endDate", datePeriod.getEndDate());
        map.put("ipoSequence", ipoSequence.getSequenceName());
        map.put("withCancelItem", withCancelItem);
        map.put("sort", sort.getName());

        return map;
    }

    @Override
    public String toString() {
        return toMap().toString();
    }
}
