package com.dbsgapi.dbsgapi.common;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JsonCommentConverterTest {
    JsonCommentConverter jcc = new JsonCommentConverter();

    @Test
    void underwriterTestIndCanMax() {
        jcc.setCommentType("underwriter");
        jcc.setCommentJson("{\"ind_can_max\": 1, \"ind_can_min\": null, \"ind_total_max\": null, \"ind_total_min\": 1111}");
        assertEquals(jcc.toString(), "ind_can_max1");
    }
    @Test
    void underwriterTestIndCanMin() {
        jcc.setCommentType("underwriter");
        jcc.setCommentJson("{\"ind_can_max\": null, \"ind_can_min\": 2, \"ind_total_max\": 1111, \"ind_total_min\": 1111}");
        assertEquals(jcc.toString(), "ind_can_min2");
    }
    @Test
    void underwriterTestIndTotalMax() {
        jcc.setCommentType("underwriter");
        jcc.setCommentJson("{\"ind_can_max\": null, \"ind_can_min\": null, \"ind_total_max\": 3, \"ind_total_min\": 1111}");
        assertEquals(jcc.toString(), "ind_total_max3");
    }
    @Test
    void underwriterTestIndTotalMin() {
        jcc.setCommentType("underwriter");
        jcc.setCommentJson("{\"ind_can_max\": null, \"ind_can_min\": null, \"ind_total_max\": null, \"ind_total_min\": 1111}");
        assertEquals(jcc.toString(), "ind_total_min1111");
    }

}