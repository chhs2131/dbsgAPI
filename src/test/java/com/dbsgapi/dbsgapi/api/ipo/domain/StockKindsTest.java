package com.dbsgapi.dbsgapi.api.ipo.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StockKindsTest {

    @Test
    void isIpo() {
        boolean b = StockKinds.IPO.isIpo();
        assertTrue(b);
    }

    @Test
    void isForfeited() {
    }

    @Test
    void isSpec() {
    }

    @Test
    void isReit() {
    }

    @ValueSource(strings = {"공모주", "실권주", "스팩주", "리츠주"})
    @ParameterizedTest
    void from(String name) {
        StockKinds stockKinds = StockKinds.from(name);
        assertThat(stockKinds.getName()).isEqualTo(name);
    }

    @Test
    void getName() {
    }
}