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

    @Test
    void ipoTestSales() {
        jcc.setCommentType("ipo");
        jcc.setCommentJson("{\"sales\": 3043680004, \"profits\": null, \"ipo_price\": null, \"par_value\": null, \"ipo_end_date\": null, \"ipo_price_low\": null, \"ipo_debut_date\": null, \"ipo_price_high\": null, \"ipo_start_date\": null, \"ipo_min_deposit\": null, \"ipo_refund_date\": null, \"lock_up_percent\": null, \"ipo_forecast_date\": null, \"put_back_option_who\": null, \"number_of_ipo_shares\": null, \"put_back_option_price\": null, \"put_back_option_deadline\": null, \"ipo_institutional_acceptance_rate\": null}");
        assertEquals(jcc.toString(), "sales3043680004");
    }
    @Test
    void ipoTestProfits() {
        jcc.setCommentType("ipo");
        jcc.setCommentJson("{\"sales\": null, \"profits\": -4891444439, \"ipo_price\": null, \"par_value\": null, \"ipo_end_date\": null, \"ipo_price_low\": null, \"ipo_debut_date\": null, \"ipo_price_high\": null, \"ipo_start_date\": null, \"ipo_min_deposit\": null, \"ipo_refund_date\": null, \"lock_up_percent\": null, \"ipo_forecast_date\": null, \"put_back_option_who\": null, \"number_of_ipo_shares\": null, \"put_back_option_price\": null, \"put_back_option_deadline\": null, \"ipo_institutional_acceptance_rate\": null}");
        assertEquals(jcc.toString(), "profits-4891444439");
    }
}
