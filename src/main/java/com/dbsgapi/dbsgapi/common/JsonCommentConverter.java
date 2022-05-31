package com.dbsgapi.dbsgapi.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
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
        } else if (Objects.equals(String.valueOf(map.get(key)), "-1")) {
            return false;
        } else if (Objects.equals(String.valueOf(map.get(key)), "-1.0")) {
            return false;
        }
        return true;
    }

    private String commentIpo(String commentJson) {
        String returnComment;
        // 해당하는값이 -1인 경우도 구분해줘야한다. -1은 값이 사라짐.
        Map<String, Object> map = jsonToMap(commentJson);
        assert map != null;
        String mapValue;
        if(!map.isEmpty()) {
        // 1순위: 일정관련 정보들
            if (mapVerify(map, "ipo_start_date") && mapVerify(map, "ipo_end_date")) {
                this.commentList.add("공모청약일이 변경되었습니다. (" +
                        String.valueOf( map.get("ipo_start_date")) + " ~ " + String.valueOf( map.get("ipo_end_date")) + ")");
            }
            else if (mapVerify(map, "ipo_start_date")) {
                this.commentList.add("공모청약 시작일이 변경되었습니다. (" + String.valueOf( map.get("ipo_start_date")) + ")");
            }
            else if (mapVerify(map, "ipo_end_date")) {
                this.commentList.add("공모청약 종료일이 변경되었습니다. (" + String.valueOf( map.get("ipo_end_date")) + ")");
            }

            if (mapVerify(map, "ipo_refund_date")) {
                this.commentList.add("대금 환불일이 변경되었습니다. (" + String.valueOf( map.get("ipo_refund_date")) + ")");
            }
            if (mapVerify(map, "ipo_debut_date")) {
                this.commentList.add("상장일이 변경되었습니다. (" + String.valueOf( map.get("ipo_debut_date")) + ")");
            }

            if (mapVerify(map, "ipo_forecast_start") && mapVerify(map, "ipo_forecast_end")) {
                this.commentList.add("수요예측일이 변경되었습니다. (" +
                        String.valueOf( map.get("ipo_forecast_start")) + " ~ " + String.valueOf( map.get("ipo_forecast_end")) + ")");
            }
            else if (mapVerify(map, "ipo_forecast_start") && mapVerify(map, "ipo_forecast_date")) {
                this.commentList.add("수요예측일이 변경되었습니다. (" +
                        String.valueOf( map.get("ipo_forecast_start")) + " ~ " + String.valueOf( map.get("ipo_forecast_date")) + ")");
            }
            else if (mapVerify(map, "ipo_forecast_start")) {
                this.commentList.add("수요예측 시작일이 변경되었습니다. (" + String.valueOf( map.get("ipo_forecast_start")) + ")");
            }
            else if (mapVerify(map, "ipo_forecast_end")) {
                this.commentList.add("수요예측 종료일이 변경되었습니다. (" + String.valueOf( map.get("ipo_forecast_end")) + ")");
            }
            else if (mapVerify(map, "ipo_forecast_date")) {  // 수요예측 종료일의 이전 컬럼명.
                this.commentList.add("수요예측 종료일이 변경되었습니다. (" + String.valueOf( map.get("ipo_forecast_date")) + ")");
            }

            if (mapVerify(map, "ex_start_date") && mapVerify(map, "ex_end_date")) {
                this.commentList.add("구주주 청약일이 변경되었습니다. (" +
                        String.valueOf( map.get("ex_start_date")) + " ~ " + String.valueOf( map.get("ex_end_date")) + ")");
            }
            else if (mapVerify(map, "ex_start_date")) {
                this.commentList.add("구주주 청약 시작일이 변경되었습니다. (" + String.valueOf( map.get("ex_start_date")) + ")");
            }
            else if (mapVerify(map, "ex_end_date")) {
                this.commentList.add("구주주 청약 종료일이 변경되었습니다. (" + String.valueOf( map.get("ex_end_date")) + ")");
            }

        // 2순위: 공모 관련 변동 정보
            if (mapVerify(map, "lock_up_percent")) {
                this.commentList.add( "의무보유확약 비율이 정정되었습니다. (" + String.valueOf( map.get("lock_up_percent"))+"%)");
            }
            if (mapVerify(map, "ipo_institutional_acceptance_rate")) {
                this.commentList.add( "수요 예측 결과가 발표되었습니다. (기업경쟁률: " + String.valueOf( map.get("ipo_institutional_acceptance_rate")) + ")");
            }
            if (mapVerify(map, "ipo_price")) {
                this.commentList.add( "공모가가 확정되었습니다. (" + String.valueOf( map.get("ipo_price")) + "원)");
            }

            if (mapVerify(map, "ipo_price_low") && mapVerify(map, "ipo_price_high")) {
                this.commentList.add("공모가 밴드가 변경되었습니다. (" +
                        String.valueOf( map.get("ipo_price_low")) + "원 ~ " + String.valueOf( map.get("ipo_price_high")) + "원)");
            }
            else if (mapVerify(map, "ipo_price_low")) {
                this.commentList.add( "공모가 밴드가 변경되었습니다. (하단: " + String.valueOf( map.get("ipo_price_low"))+"원)");
            }
            else if (mapVerify(map, "ipo_price_high")) {
                this.commentList.add( "공모가 밴드가 변경되었습니다. (상단: " + String.valueOf( map.get("ipo_price_high"))+"원)");
            }

            if (mapVerify(map, "ipo_min_deposit")) {
                this.commentList.add( "최소 청약 증거금이 변경되었습니다. (" + String.valueOf( map.get("ipo_min_deposit"))+"원)");
            }
            if (mapVerify(map, "put_back_option_who")) {
                this.commentList.add( "환매청구권 대상이 정정되었습니다. (" + String.valueOf( map.get("put_back_option_who")) +")");
            }
            if (mapVerify(map, "put_back_option_price")) {
                this.commentList.add( "환매청구권 가격이 정정되었습니다. (" + String.valueOf( map.get("put_back_option_price"))+"원)");
            }
            if (mapVerify(map, "put_back_option_deadline")) {
                this.commentList.add( "환매청구권 기한이 정정되었습니다. (" + String.valueOf( map.get("put_back_option_deadline"))+")");
            }

        // 3순위: 공모 관련 고정 정보
            if (mapVerify(map, "profits")) {
                mapValue = String.valueOf(map.get("profits"));
                this.commentList.add("영업이익 값이 정정되었습니다. " + mapValue);
            }
            if (mapVerify(map, "sales")) {
                mapValue = String.valueOf( map.get("sales"));
                this.commentList.add("매출액 값이 정정되었습니다." + mapValue);
            }
            if (mapVerify(map, "number_of_ipo_shares")) {
                this.commentList.add( "총 공모 주식수가 변경되었습니다. (" + String.valueOf( map.get("number_of_ipo_shares")) + "주)");
            }
            if (mapVerify(map, "par_value")) {
                this.commentList.add( "액면가가 변경되었습니다. (" + String.valueOf( map.get("par_value")) + "원)");
            }
            if (mapVerify(map, "purpose_of_funds")) {
                this.commentList.add( "자금의 사용목적이 변경되었습니다. (" + String.valueOf( map.get("purpose_of_funds")) + ")");
            }

            // 가장 높은 우선순위에 있는 comment를 반환한다. ex) 공모가가 확정되었습니다. (1,200원) - 외 3건
            if(!commentList.isEmpty()) {
                int sizeOfList = commentList.size() - 1;  // 변경사항이 여러개일 경우 "외 n건"을 붙인다.
                if (sizeOfList == 0) {
                    returnComment = commentList.get(0);
                } else {
                    returnComment = commentList.get(0) + " - 외 " + String.valueOf(sizeOfList) + "건";
                }
            } else {
                returnComment = "";
            }
        } else {
            // comment 내용이 없을 경우 Blank String 을 반환한다.
            returnComment = "";
        }

        return returnComment;
    }

    private String commentUnderwriter(String commentJson) {
        String returnComment;
        // underwriter(주간사) json을 해석하고 그 내용을 String으로 반환합니다.
        Map<String, Object> map = jsonToMap(commentJson);
        assert map != null;
        if(!map.isEmpty()) {
            if (map.containsKey("ind_can_max") && map.get("ind_can_max") != null) {
                returnComment = "일반인 청약가능 수량이 변경되었습니다.";
                // return "ind_can_max" + String.valueOf(map.get("ind_can_max"));
            } else if (map.containsKey("ind_can_min") && map.get("ind_can_min") != null) {
                returnComment = "일반인 청약가능 수량이 변경되었습니다.";
                // return "ind_can_min" + String.valueOf(map.get("ind_can_min"));
            } else if (map.containsKey("ind_total_max") && map.get("ind_total_max") != null) {
                returnComment = "일반인 청약가능 수량이 변경되었습니다.";
                // return "ind_total_max" + String.valueOf(map.get("ind_total_max"));
            } else if (map.containsKey("ind_total_min") && map.get("ind_total_min") != null) {
                returnComment = "일반인 청약가능 수량이 변경되었습니다.";
                // return "ind_total_min" + String.valueOf(map.get("ind_total_min"));
            } else {
                returnComment = "";
            }
        } else {
            returnComment = "";
        }
        return returnComment;
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
