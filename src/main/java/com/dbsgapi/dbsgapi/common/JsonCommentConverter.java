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
                break;
            case 1:
                commentReturn = commentIpo(this.commentJson);
                break;
            case 2:
                commentReturn = commentUnderwriter(this.commentJson);
                break;
            default:
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
        // 해당하는값이 -1인 경우도 구분해줘야한다. -1은 값이 사라짐.
        Map<String, Object> map = jsonToMap(commentJson);
        assert map != null;
        if(!map.isEmpty()) {
            if (map.containsKey("profits") && map.get("profits") != null) {
                return "profits" + String.valueOf( map.get("profits"));
            } else if (map.containsKey("sales") && map.get("sales") != null) {
                return "sales" + String.valueOf( map.get("sales"));
            } else if (map.containsKey("ipo_forecast_start") && map.get("ipo_forecast_start") != null) {
                return "ipo_forecast_start" + String.valueOf( map.get("ipo_forecast_start"));
            } else if (map.containsKey("ipo_forecast_start") && map.get("ipo_forecast_start") != null) {  // forecast_end 추후에 수정!!!!!!!!!!
                return "ipo_forecast_start" + String.valueOf( map.get("ipo_forecast_start"));
            } else if (map.containsKey("ipo_start_date") && map.get("ipo_start_date") != null) {
                return "ipo_start_date" + String.valueOf( map.get("ipo_start_date"));
            } else if (map.containsKey("ipo_end_date") && map.get("ipo_end_date") != null) {
                return "ipo_end_date" + String.valueOf( map.get("ipo_end_date"));
            } else if (map.containsKey("ipo_refund_date") && map.get("ipo_refund_date") != null) {
                return "ipo_refund_date" + String.valueOf( map.get("ipo_refund_date"));
            } else if (map.containsKey("ipo_debut_date") && map.get("ipo_debut_date") != null) {
                return "ipo_debut_date" + String.valueOf( map.get("ipo_debut_date"));
            } else if (map.containsKey("lock_up_percent") && map.get("lock_up_percent") != null) {
                return "lock_up_percent" + String.valueOf( map.get("lock_up_percent"));
            } else if (map.containsKey("ipo_institutional_acceptance_rate") && map.get("ipo_institutional_acceptance_rate") != null) {
                return "ipo_institutional_acceptance_rate" + String.valueOf( map.get("ipo_institutional_acceptance_rate"));
            } else if (map.containsKey("number_of_ipo_shares") && map.get("number_of_ipo_shares") != null) {
                return "number_of_ipo_shares" + String.valueOf( map.get("number_of_ipo_shares"));
            } else if (map.containsKey("par_value") && map.get("par_value") != null) {
                return "par_value" + String.valueOf( map.get("par_value"));
            } else if (map.containsKey("ipo_price") && map.get("ipo_price") != null) {
                return "ipo_price" + String.valueOf( map.get("ipo_price"));
            } else if (map.containsKey("ipo_price_low") && map.get("ipo_price_low") != null) {
                return "ipo_price_low" + String.valueOf( map.get("ipo_price_low"));
            } else if (map.containsKey("ipo_price_high") && map.get("ipo_price_high") != null) {
                return "ipo_price_high" + String.valueOf( map.get("ipo_price_high"));
            } else if (map.containsKey("ipo_min_deposit") && map.get("ipo_min_deposit") != null) {
                return "ipo_min_deposit" + String.valueOf( map.get("ipo_min_deposit"));
            } else if (map.containsKey("put_back_option_who") && map.get("put_back_option_who") != null) {
                return "put_back_option_who" + String.valueOf( map.get("put_back_option_who"));
            } else if (map.containsKey("put_back_option_price") && map.get("put_back_option_price") != null) {
                return "put_back_option_price" + String.valueOf( map.get("put_back_option_price"));
            } else if (map.containsKey("put_back_option_deadline") && map.get("put_back_option_deadline") != null) {
                return "put_back_option_deadline" + String.valueOf( map.get("put_back_option_deadline"));
            }
        }
        return "";
    }

    private String commentUnderwriter(String commentJson) {
        // underwriter(주간사) json을 해석하고 그 내용을 String으로 반환합니다.
        Map<String, Object> map = jsonToMap(commentJson);
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
