package com.dbsgapi.dbsgapi.common;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonCommentConverter {
    private String commentType;
    private String commentJson;
    private int commentCode;  // commentType를 통해 code 파생됨.
    private List<String> commentList = new ArrayList<String>();

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

    public String getRecentComment() {
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

    public List<String> getCommentList() {
        System.out.println(this.commentList);
        return this.commentList;
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

    private boolean mapVerify(Map<String, Object> map, String key) {
        if (!map.containsKey(key)) {
            return false;
        } else if (map.get(key) == null) {
            return false;
        } else if (map.get(key) == "-1") {
            return false;
        }
        System.out.println("map contain: " + key + " " + String.valueOf(map.get(key)));
        return true;
    }

    private String commentIpo(String commentJson) {
        // 해당하는값이 -1인 경우도 구분해줘야한다. -1은 값이 사라짐.
        Map<String, Object> map = jsonToMap(commentJson);
        assert map != null;
        String mapValue;
        if(!map.isEmpty()) {
            System.out.println("commentIPO");

            if (mapVerify(map, "profits")) {
                mapValue = String.valueOf(map.get("profits"));
                this.commentList.add("profits" + mapValue);
            }
            if (mapVerify(map, "sales")) {
                mapValue = String.valueOf( map.get("sales"));
                this.commentList.add("sales" + mapValue);
            }
            if (mapVerify(map, "ipo_forecast_start")) {
                this.commentList.add( "ipo_forecast_start" + String.valueOf( map.get("ipo_forecast_start")));
            }  if (mapVerify(map, "ipo_forecast_end")) {
                this.commentList.add( "ipo_forecast_end" + String.valueOf( map.get("ipo_forecast_end")));
            }  if (mapVerify(map, "ipo_forecast_date")) {
                this.commentList.add( "ipo_forecast_date" + String.valueOf( map.get("ipo_forecast_date")));
            }  if (mapVerify(map, "ipo_start_date")) {
                this.commentList.add( "ipo_start_date" + String.valueOf( map.get("ipo_start_date")));
            }  if (mapVerify(map, "ipo_end_date")) {
                this.commentList.add( "ipo_end_date" + String.valueOf( map.get("ipo_end_date")));
            }  if (mapVerify(map, "ipo_refund_date")) {
                this.commentList.add( "ipo_refund_date" + String.valueOf( map.get("ipo_refund_date")));
            }  if (mapVerify(map, "ipo_debut_date")) {
                this.commentList.add( "ipo_debut_date" + String.valueOf( map.get("ipo_debut_date")));
            }  if (mapVerify(map, "lock_up_percent")) {
                this.commentList.add( "lock_up_percent" + String.valueOf( map.get("lock_up_percent")));
            }  if (mapVerify(map, "ipo_institutional_acceptance_rate")) {
                this.commentList.add( "ipo_institutional_acceptance_rate" + String.valueOf( map.get("ipo_institutional_acceptance_rate")));
            }  if (mapVerify(map, "number_of_ipo_shares")) {
                this.commentList.add( "number_of_ipo_shares" + String.valueOf( map.get("number_of_ipo_shares")));
            }  if (mapVerify(map, "par_value")) {
                this.commentList.add( "par_value" + String.valueOf( map.get("par_value")));
            }  if (mapVerify(map, "ipo_price")) {
                this.commentList.add( "ipo_price" + String.valueOf( map.get("ipo_price")));
            }  if (mapVerify(map, "ipo_price_low")) {
                this.commentList.add( "ipo_price_low" + String.valueOf( map.get("ipo_price_low")));
            }  if (mapVerify(map, "ipo_price_high")) {
                this.commentList.add( "ipo_price_high" + String.valueOf( map.get("ipo_price_high")));
            }  if (mapVerify(map, "ipo_min_deposit")) {
                this.commentList.add( "ipo_min_deposit" + String.valueOf( map.get("ipo_min_deposit")));
            }  if (mapVerify(map, "put_back_option_who")) {
                this.commentList.add( "put_back_option_who" + String.valueOf( map.get("put_back_option_who")));
            }  if (mapVerify(map, "put_back_option_price")) {
                this.commentList.add( "put_back_option_price" + String.valueOf( map.get("put_back_option_price")));
            }  if (mapVerify(map, "put_back_option_deadline")) {
                this.commentList.add( "put_back_option_deadline" + String.valueOf( map.get("put_back_option_deadline")));
            }

            return commentList.get(0);
        }
        getCommentList();
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
