package com.dbsgapi.dbsgapi.common;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class JsonCommentConverterTest {
    JsonCommentConverter jcc = new JsonCommentConverter();

    @Test
    void underwriterTestIndCanMax() {
        jcc.setCommentType("underwriter");
        jcc.setCommentJson("{\"ind_can_max\": 1, \"ind_can_min\": null, \"ind_total_max\": null, \"ind_total_min\": 1111}");
        assertEquals(jcc.getRecentComment(), "일반인 청약가능 수량이 변경되었습니다.");
    }
    @Test
    void underwriterTestIndCanMin() {
        jcc.setCommentType("underwriter");
        jcc.setCommentJson("{\"ind_can_max\": null, \"ind_can_min\": 2, \"ind_total_max\": 1111, \"ind_total_min\": 1111}");
        assertEquals(jcc.getRecentComment(), "일반인 청약가능 수량이 변경되었습니다.");
    }
    @Test
    void underwriterTestIndTotalMax() {
        jcc.setCommentType("underwriter");
        jcc.setCommentJson("{\"ind_can_max\": null, \"ind_can_min\": null, \"ind_total_max\": 3, \"ind_total_min\": 1111}");
        assertEquals(jcc.getRecentComment(), "일반인 청약가능 수량이 변경되었습니다.");
    }
    @Test
    void underwriterTestIndTotalMin() {
        jcc.setCommentType("underwriter");
        jcc.setCommentJson("{\"ind_can_max\": null, \"ind_can_min\": null, \"ind_total_max\": null, \"ind_total_min\": 1111}");
        assertEquals(jcc.getRecentComment(), "일반인 청약가능 수량이 변경되었습니다.");
    }

    @Test
    void ipoTestSales() {
        jcc.setCommentType("ipo");
        jcc.setCommentJson("{\"sales\": 3043680004, \"profits\": 333, \"ipo_price\": null, \"par_value\": null, \"ipo_end_date\": null, \"ipo_price_low\": null, \"ipo_debut_date\": null, \"ipo_price_high\": null, \"ipo_start_date\": null, \"ipo_min_deposit\": null, \"ipo_refund_date\": null, \"lock_up_percent\": 34.27, \"ipo_forecast_date\": null, \"put_back_option_who\": null, \"number_of_ipo_shares\": 12345, \"put_back_option_price\": null, \"put_back_option_deadline\": null, \"ipo_institutional_acceptance_rate\": null}");
        assertEquals(jcc.getRecentComment(), "의무보유확약 비율이 정정되었습니다. (34.27%) - 외 3건");
        //assertThat(jcc.getCommentList()).contains("sales3043680004", "2");
    }
    @Test
    void ipoTestIpoDate() {
        jcc.setCommentType("ipo");
        jcc.setCommentJson("{\"sales\": 3043680004, \"profits\": 333, \"ipo_price\": null, \"par_value\": null, \"ipo_end_date\": \"2022-01-02\", \"ipo_price_low\": null, \"ipo_debut_date\": null, \"ipo_price_high\": null, \"ipo_start_date\": \"2022-01-01\", \"ipo_min_deposit\": null, \"ipo_refund_date\": null, \"lock_up_percent\": 34.27, \"ipo_forecast_date\": null, \"put_back_option_who\": null, \"number_of_ipo_shares\": 12345, \"put_back_option_price\": null, \"put_back_option_deadline\": null, \"ipo_institutional_acceptance_rate\": null}");
        assertEquals(jcc.getRecentComment(), "공모청약일이 변경되었습니다. (2022-01-01 ~ 2022-01-02) - 외 4건");
        //assertThat(jcc.getCommentList()).contains("sales3043680004", "2");
    }
    @Test
    void ipoTestForecastDate() {
        jcc.setCommentType("ipo");
        jcc.setCommentJson("{\"sales\": 3043680004, \"profits\": 333, \"ipo_price\": null, \"par_value\": null, \"ipo_end_date\": null, \"ipo_price_low\": null, \"ipo_debut_date\": null, \"ipo_price_high\": null, \"ipo_start_date\": null, \"ipo_min_deposit\": null, \"ipo_refund_date\": null, \"lock_up_percent\": 34.27, \"ipo_forecast_start\": \"2022-03-03\", \"ipo_forecast_end\": \"2022-03-04\", \"put_back_option_who\": null, \"number_of_ipo_shares\": 12345, \"put_back_option_price\": null, \"put_back_option_deadline\": null, \"ipo_institutional_acceptance_rate\": null}");
        assertEquals(jcc.getRecentComment(), "수요예측일이 변경되었습니다. (2022-03-03 ~ 2022-03-04) - 외 4건");
        //assertThat(jcc.getCommentList()).contains("sales3043680004", "2");
    }
    @Test
    void ipoTestPriceLow() {
        jcc.setCommentType("ipo");
        jcc.setCommentJson("{\"sales\": null, \"profits\": -4891444439, \"ipo_price\": null, \"par_value\": null, \"ipo_end_date\": null, \"ipo_price_low\": 100, \"ipo_debut_date\": null, \"ipo_price_high\": null, \"ipo_start_date\": null, \"ipo_min_deposit\": null, \"ipo_refund_date\": null, \"lock_up_percent\": null, \"ipo_forecast_date\": null, \"put_back_option_who\": null, \"number_of_ipo_shares\": 99999, \"put_back_option_price\": null, \"put_back_option_deadline\": null, \"ipo_institutional_acceptance_rate\": null}");
        assertEquals(jcc.getRecentComment(), "공모가 밴드가 변경되었습니다. (하단: 100원) - 외 2건");
    }
    @Test
    void ipoTestPriceHigh() {
        jcc.setCommentType("ipo");
        jcc.setCommentJson("{\"sales\": null, \"profits\": -4891444439, \"ipo_price\": null, \"par_value\": null, \"ipo_end_date\": null, \"ipo_price_low\": null, \"ipo_debut_date\": null, \"ipo_price_high\": 500, \"ipo_start_date\": null, \"ipo_min_deposit\": null, \"ipo_refund_date\": null, \"lock_up_percent\": null, \"ipo_forecast_date\": null, \"put_back_option_who\": null, \"number_of_ipo_shares\": 99999, \"put_back_option_price\": null, \"put_back_option_deadline\": null, \"ipo_institutional_acceptance_rate\": null}");
        assertEquals(jcc.getRecentComment(), "공모가 밴드가 변경되었습니다. (상단: 500원) - 외 2건");
    }
    @Test
    void ipoTestPriceBand() {
        jcc.setCommentType("ipo");
        jcc.setCommentJson("{\"sales\": null, \"profits\": -4891444439, \"ipo_price\": null, \"par_value\": null, \"ipo_end_date\": null, \"ipo_price_low\": 100, \"ipo_debut_date\": null, \"ipo_price_high\": 500, \"ipo_start_date\": null, \"ipo_min_deposit\": null, \"ipo_refund_date\": null, \"lock_up_percent\": null, \"ipo_forecast_date\": null, \"put_back_option_who\": null, \"number_of_ipo_shares\": 99999, \"put_back_option_price\": null, \"put_back_option_deadline\": null, \"ipo_institutional_acceptance_rate\": null}");
        assertEquals(jcc.getRecentComment(), "공모가 밴드가 변경되었습니다. (100원 ~ 500원) - 외 2건");
    }
}

