package com.dbsgapi.dbsgapi.common;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class JsonCommentConverter {
    private String commentType;
    private String commentJson;
    private int commentCode;  // commentType를 통해 code 파생됨.

    ObjectMapper objectMapper = new ObjectMapper();

    public JsonCommentConverter() {
    }
    public JsonCommentConverter(String commentType, String commentJson){
        this.commentType = commentType;
        this.commentJson = commentJson;
        setCommentCode(this.commentType);
    }
    public void setCommentType(String commentType) {
        this.commentType = commentType;
        setCommentCode(commentType);
    }
    public void setCommentJson(String commentJson) {
        this.commentJson = commentJson;
    }

    public String toString() {
        String commentReturn = "";
        switch (this.commentCode) {
            case 0:
                System.out.println("nono");
                break;
            case 1:
                System.out.println("code ipo");
                commentReturn = commentIpo(this.commentJson);
                break;
            case 2:
                System.out.println("code underwriter");
                commentReturn = commentUnderwriter(this.commentJson);
                break;
            default:
                System.out.println("code default");
                break;
        }
        return commentReturn;
    }

    private void setCommentCode(String commentType) {
        if(commentType.isEmpty()) {
            this.commentCode = 0;
        }else if(commentType.equals("ipo")) {
            this.commentCode = 1;
        }else if(commentType.equals("underwriter")) {
            this.commentCode = 2;
        }else {
            this.commentCode = 0;
        }
    }

    private String commentIpo(String commentJson) {
        // json 해석 로직 작성 필요
        /*
        'profits', nullif(NEW.profits, OLD.profits),
        'sales', nullif(NEW.sales, OLD.sales),
        'ipo_forecast_date', nullif(NEW.ipo_forecast_date, OLD.ipo_forecast_date),
        'ipo_start_date', nullif(NEW.ipo_start_date, OLD.ipo_start_date),
        'ipo_end_date', nullif(NEW.ipo_end_date, OLD.ipo_end_date),
        'ipo_refund_date', nullif(NEW.ipo_refund_date, OLD.ipo_refund_date),
        'ipo_debut_date', nullif(NEW.ipo_debut_date, OLD.ipo_debut_date),
        'lock_up_percent', nullif(NEW.lock_up_percent, OLD.lock_up_percent),
        'ipo_institutional_acceptance_rate', nullif(NEW.ipo_institutional_acceptance_rate, OLD.ipo_institutional_acceptance_rate),
        'number_of_ipo_shares', nullif(NEW.number_of_ipo_shares, OLD.number_of_ipo_shares),
        'par_value', nullif(NEW.par_value, OLD.par_value),
        'ipo_price', nullif(NEW.ipo_price, OLD.ipo_price),
        'ipo_price_low', nullif(NEW.ipo_price_low, OLD.ipo_price_low),
        'ipo_price_high', nullif(NEW.ipo_price_high, OLD.ipo_price_high),
        'ipo_min_deposit', nullif(NEW.ipo_min_deposit, OLD.ipo_min_deposit),
        'put_back_option_who', nullif(NEW.put_back_option_who, OLD.put_back_option_who),
        'put_back_option_price', nullif(NEW.put_back_option_price, OLD.put_back_option_price),
        'put_back_option_deadline', nullif(NEW.put_back_option_deadline, OLD.put_back_option_deadline)
         */

        Map<String, Object> map = jsonToMap(commentJson);

        assert map != null;
        if(!map.isEmpty()) {
            if (map.containsKey("profits") && map.get("profits") != null) {
                return "profits" + (String) map.get("profits");
            } else if (map.containsKey("sales") && map.get("sales") != null) {
                return "sales" + (String) map.get("sales");
            }
        }

        return "ipo가 변경되었습니다.";
    }

    private String commentUnderwriter(String commentJson) {
        // underwriter(주간사) json을 해석하고 그 내용을 String으로 반환합니다.
        Map<String, Object> map = jsonToMap(commentJson);
        System.out.println(map);

        assert map != null;
        if(!map.isEmpty()) {
            if (map.containsKey("ind_can_max") && map.get("ind_can_max") != null) {
                return "ind_can_max" + String.valueOf(map.get("ind_can_max"));
            } else if (map.containsKey("ind_can_min") && map.get("ind_can_min") != null) {
                return "ind_can_min" + String.valueOf(map.get("ind_can_min"));
            } else if (map.containsKey("ind_total_max") && map.get("ind_total_max") != null) {
                return "ind_total_max" + String.valueOf(map.get("ind_total_max"));
            } else if (map.containsKey("ind_total_min") && map.get("ind_total_min") != null) {
                return "ind_total_min" + String.valueOf(map.get("ind_total_min"));
            }
        }

        return "";
    }

    private Map<String, Object> jsonToMap(String json) {
        try {
            return objectMapper.readValue(json, Map.class);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
