package com.dbsgapi.dbsgapi.common;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class KrWonFormat {
    private final DecimalFormat df = new DecimalFormat("#,###.##");  // 소수점이 존재할 경우 최대 둘째짜리까지만 표현
    private final Map<String, Long> wonUnit = new HashMap<String, Long>() {
        {
            put("", 1L);
            put("만", 10000L);
            put("억", 100000000L);
            put("조", 1000000000000L);
            put("경", 10000000000000000L);
        }
    };

    public String getString(long number) {
        String unit = this.checkUnit(number);
        return this.formatting((double) number, unit);
    }
    public String getString(double number) {
        String unit = this.checkUnit((long) number);
        return this.formatting(number, unit);
    }
    public String getString(String number) {
        if(number.contains(".")) {
            return this.getString(Double.parseDouble(number));
        } else{
            return this.getString(Long.parseLong(number));
        }
    }

    public String withoutUnit(String number) {
        if(number.contains(".")) {
            return df.format(Double.parseDouble(number));
        } else{
            return df.format(Long.parseLong(number));
        }
    }
    public String withoutUnit(long number) {
        return df.format(number);
    }
    public String withoutUnit(double number) {
        return df.format(number);
    }

    private String formatting(double number, String unit) {
        // 숫자를 단위에 맞게 조정
        double new_number = number / this.wonUnit.get(unit);

        // Decimal Format을 통해 단위와 함께 반환
        return df.format(new_number) + unit;
    }

    private String checkUnit(long number) {
        long absNumber = Math.abs(number);
        Iterator<String> iter = this.wonUnit.keySet().iterator();

        String unit = "";
        long max = 1L;
        while (iter.hasNext()) {
            String key = iter.next();
            long unitValue = this.wonUnit.get(key);
            if (absNumber >= unitValue) {
                if (max < unitValue) {
                    max = unitValue;
                    unit = key;
                }
            }
        }

        return unit;
    }
}
