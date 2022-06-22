package com.dbsgapi.dbsgapi.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KrWonFormatTest {

    @Test
    void int_1234() {
        KrWonFormat kwf = new KrWonFormat();
        String result = kwf.getString(1234);
        assertEquals("1,234", result);
    }
    @Test
    void int_12345() {
        KrWonFormat kwf = new KrWonFormat();
        String result = kwf.getString(12345);
        assertEquals("1.23만", result);
    }
    @Test
    void int_m12345() {
        KrWonFormat kwf = new KrWonFormat();
        String result = kwf.getString(-12345);
        assertEquals("-1.23만", result);
    }
    @Test
    void int_12345000() {
        KrWonFormat kwf = new KrWonFormat();
        String result = kwf.getString(12345000);
        assertEquals("1,234.5만", result);
    }
    @Test
    void int_100000() {
        KrWonFormat kwf = new KrWonFormat();
        String result = kwf.getString(100000);
        assertEquals("10만", result);
    }
    @Test
    void int_123456789() {
        KrWonFormat kwf = new KrWonFormat();
        String result = kwf.getString(123456789);
        assertEquals("1.23억", result);
    }
    @Test
    void long_987600000000L() {
        KrWonFormat kwf = new KrWonFormat();
        String result = kwf.getString(987600000000L);
        assertEquals("9,876억", result);
    }
    @Test
    void long_12345678911111111L() {
        KrWonFormat kwf = new KrWonFormat();
        String result = kwf.getString(12345678911111111L);
        assertEquals("1.23경", result);
    }


    @Test
    void string_int_1234() {
        KrWonFormat kwf = new KrWonFormat();
        String result = kwf.getString("1234");
        assertEquals("1,234", result);
    }
    @Test
    void string_int_12345() {
        KrWonFormat kwf = new KrWonFormat();
        String result = kwf.getString(12345);
        assertEquals("1.23만", result);
    }
    @Test
    void string_int_m12345() {
        KrWonFormat kwf = new KrWonFormat();
        String result = kwf.getString(-12345);
        assertEquals("-1.23만", result);
    }
    @Test
    void string_int_12345000() {
        KrWonFormat kwf = new KrWonFormat();
        String result = kwf.getString(12345000);
        assertEquals("1,234.5만", result);
    }
    @Test
    void string_int_100000() {
        KrWonFormat kwf = new KrWonFormat();
        String result = kwf.getString(100000);
        assertEquals("10만", result);
    }
    @Test
    void string_int_123456789() {
        KrWonFormat kwf = new KrWonFormat();
        String result = kwf.getString(123456789);
        assertEquals("1.23억", result);
    }
    @Test
    void string_long_987600000000L() {
        KrWonFormat kwf = new KrWonFormat();
        String result = kwf.getString(987600000000L);
        assertEquals("9,876억", result);
    }
    @Test
    void string_long_12345678911111111L() {
        KrWonFormat kwf = new KrWonFormat();
        String result = kwf.getString(12345678911111111L);
        assertEquals("1.23경", result);
    }


    @Test
    void float_12345_67() {
        KrWonFormat kwf = new KrWonFormat();
        String result = kwf.getString(12345.67);
        assertEquals("1.23만", result);
    }
    @Test
    void float_1234_56() {
        KrWonFormat kwf = new KrWonFormat();
        String result = kwf.getString(1234.56);
        assertEquals("1,234.56", result);
    }
    @Test
    void float_111111_1111() {
        KrWonFormat kwf = new KrWonFormat();
        String result = kwf.getString(111111.1111);
        assertEquals("11.11만", result);
    }
    @Test
    void float_9999_99() {
        KrWonFormat kwf = new KrWonFormat();
        String result = kwf.getString(9999.99);
        assertEquals("9,999.99", result);
    }
    @Test
    void string_float_12345_67() {
        KrWonFormat kwf = new KrWonFormat();
        String result = kwf.getString("12345.67");
        assertEquals("1.23만", result);
    }
}
