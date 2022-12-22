package com.dbsgapi.dbsgapi.api.ipo.domain;

import java.time.LocalDate;

public class DatePeriod {
    private final LocalDate startDate;
    private final LocalDate endDate;

    public DatePeriod(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        validate();
    }

    private void validate() {
        validateNotNull();
        validateDateRange();
    }

    private void validateNotNull() {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("startDate and endDate must not be null");
        }
    }

    private void validateDateRange() {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
    }

    public static DatePeriod from(LocalDate targetDate) {
        return new DatePeriod(targetDate, targetDate);
    }

    public static DatePeriod from(LocalDate startDate, LocalDate endDate) {
        return new DatePeriod(startDate, endDate);
    }

    /**
     * tartget, start, end 중에 어떤 것을 런타임레벨에서 동적으로 결정하여 결과를 반환합니다.
     * @param targetDate 특정일에 대한 명시 (startDate, endDate 값이 없는 경우에 사용됩니다.)
     * @param startDate 시작 기준일
     * @param endDate 종료 기준일
     * @return DatePeriod
     */
    public static DatePeriod from(LocalDate targetDate, LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null) {
            return new DatePeriod(startDate, endDate);
        }
        if (startDate == null && endDate == null && targetDate != null) {
            return new DatePeriod(targetDate, targetDate);
        }
        throw new IllegalArgumentException("잘못된 기간 값이 입력되었습니다.");
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "기간: " + startDate.toString() + " ~ " + endDate.toString();
    }
}
