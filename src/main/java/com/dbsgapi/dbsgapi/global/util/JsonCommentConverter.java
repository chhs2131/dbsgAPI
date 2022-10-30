package com.dbsgapi.dbsgapi.global.util;

import com.dbsgapi.dbsgapi.api.ipo.domain.CommentLevel;
import com.dbsgapi.dbsgapi.api.ipo.domain.IpoComment;
import com.dbsgapi.dbsgapi.api.ipo.domain.KindOfComment;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.dbsgapi.dbsgapi.api.ipo.domain.CommentLevel.IPO_CANCEL;
import static com.dbsgapi.dbsgapi.api.ipo.domain.KindOfComment.*;

@Slf4j
public class JsonCommentConverter {
    private static final KrWonFormat wonFormat = new KrWonFormat();

    private String commentType;
    private String commentJson;
    private int commentCode;  // commentType를 통해 code 파생됨.
    private CommentLevel title;
    private List<IpoComment> commentList = new ArrayList<IpoComment>();

    ObjectMapper objectMapper = new ObjectMapper();

    public JsonCommentConverter() {
    }

    public JsonCommentConverter(String commentType, String commentJson) {
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

    public List<IpoComment> getCommentList() {
        return this.commentList;
    }

    private void setCommentCode(String commentType) {
        if (commentType.isEmpty()) {
            this.commentCode = 0;
        } else if (commentType.equals("ipo")) {
            this.commentCode = 1;
        } else if (commentType.equals("underwriter")) {
            this.commentCode = 2;
        } else {
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
        } else if (Objects.equals(String.valueOf(map.get(key)), "N")) {
            return false;
        }
        return true;
    }

    private String getMapValue(Map<String, Object> map, String key) {
        // mapVerify 함수로 검증된 데이터만 접근할 것.
        return String.valueOf(map.get(key));
    }

    private String getMapValueWithoutUnit(Map<String, Object> map, String key) {
        // mapVerify 함수로 검증된 데이터만 접근할 것.
        return wonFormat.withoutUnit(String.valueOf(map.get(key)));
    }

    private String getMapValueWithUnit(Map<String, Object> map, String key) {
        // mapVerify 함수로 검증된 데이터만 접근할 것.
        return wonFormat.getString(String.valueOf(map.get(key)));
    }

    private void setFinalTitle(CommentLevel title) {
        // title이 아직 설정되지 않은 경우에만 설정한다.
        if(this.title == null)
            this.title = title;
    }

    public String getTitle() {
        return this.title.getName();
    }

    // comment list 에 내용을 추가합니다.
    private void appendComment(KindOfComment type, String comment) {
        setFinalTitle(type.getType());  // 높은 우선순위에 메세지를 제목으로 설정합니다.
        this.commentList.add(IpoComment.makeNew(type, comment));  // comment 추가
    }

    private String commentIpo(String commentJson) {
        Map<String, Object> map = jsonToMap(commentJson);
        assert map != null;
        if (map.isEmpty())  // comment 내용이 없을 경우 Blank String 을 반환한다.
            return "";

        // 0순위: 공모철회 관련 정보
        if (mapVerify(map, "ipo_cancel_bool")) {
            appendComment(CANCEL, "공모청약이 철회되었습니다.");
        }
        // 1순위: 일정관련 정보들
        if (mapVerify(map, "ipo_start_date") && mapVerify(map, "ipo_end_date")) {
            appendComment(CHANGED_IPO_DATE, getMapValue(map, "ipo_start_date") + " ~ " + getMapValue(map, "ipo_end_date"));
        } else if (mapVerify(map, "ipo_start_date")) {
            appendComment(CHANGED_IPO_DATE, getMapValue(map, "ipo_start_date"));
        } else if (mapVerify(map, "ipo_end_date")) {
            appendComment(CHANGED_IPO_DATE, getMapValue(map, "ipo_end_date"));
        }

        if (mapVerify(map, "ipo_refund_date")) {
            appendComment(CHANGED_REFUND_DATE, getMapValue(map, "ipo_refund_date"));
        }
        if (mapVerify(map, "ipo_debut_date")) {
            appendComment(CHANGED_DEBUT_DATE, getMapValue(map, "ipo_debut_date"));
        }

        if (mapVerify(map, "ipo_forecast_start") && mapVerify(map, "ipo_forecast_end")) {
            appendComment(CHANGED_FORECAST_DATE, getMapValue(map, "ipo_forecast_start") + " ~ " + getMapValue(map, "ipo_forecast_end"));
        } else if (mapVerify(map, "ipo_forecast_start") && mapVerify(map, "ipo_forecast_date")) {
            appendComment(CHANGED_FORECAST_DATE, getMapValue(map, "ipo_forecast_start") + " ~ " + getMapValue(map, "ipo_forecast_date"));
        } else if (mapVerify(map, "ipo_forecast_start")) {
            appendComment(CHANGED_FORECAST_DATE, getMapValue(map, "ipo_forecast_start"));
        } else if (mapVerify(map, "ipo_forecast_end")) {
            appendComment(CHANGED_FORECAST_DATE, getMapValue(map, "ipo_forecast_end"));
        } else if (mapVerify(map, "ipo_forecast_date")) {  // 수요예측 종료일의 이전 컬럼명.
            appendComment(CHANGED_FORECAST_DATE, getMapValue(map, "ipo_forecast_date"));
        }

        if (mapVerify(map, "ex_start_date") && mapVerify(map, "ex_end_date")) {
            appendComment(CHANGED_EX_DATE, getMapValue(map, "ex_start_date") + " ~ " + getMapValue(map, "ex_end_date"));
        } else if (mapVerify(map, "ex_start_date")) {
            appendComment(CHANGED_EX_DATE, getMapValue(map, "ex_start_date"));
        } else if (mapVerify(map, "ex_end_date")) {
            appendComment(CHANGED_EX_DATE, getMapValue(map, "ex_end_date"));
        }

        // 2순위: 공모 관련 변동 정보
        if (mapVerify(map, "lock_up_percent")) {
            appendComment(CHANGED_LOCK_UP_PERCENT, getMapValueWithoutUnit(map, "lock_up_percent"));
        }
        if (mapVerify(map, "ipo_institutional_acceptance_rate")) {
            appendComment(SET_ACCEPTANCE_RATE, getMapValueWithoutUnit(map, "ipo_institutional_acceptance_rate"));
        }
        if (mapVerify(map, "ipo_price")) {
            appendComment(SET_IPO_PRICE, getMapValueWithoutUnit(map, "ipo_price") + "원");
        }

        if (mapVerify(map, "ipo_price_low") && mapVerify(map, "ipo_price_high")) {
            appendComment(CHANGED_IPO_PRICE_BAND, getMapValueWithoutUnit(map, "ipo_price_low") + "원 ~ " + getMapValueWithoutUnit(map, "ipo_price_high") + "원");
        } else if (mapVerify(map, "ipo_price_low")) {
            appendComment(CHANGED_IPO_PRICE_BAND, "하단: " + getMapValueWithoutUnit(map, "ipo_price_low") + "원");
        } else if (mapVerify(map, "ipo_price_high")) {
            appendComment(CHANGED_IPO_PRICE_BAND, "상단: " + getMapValueWithoutUnit(map, "ipo_price_high") + "원");
        }

        if (mapVerify(map, "ipo_min_deposit")) {
            appendComment(CHANGED_IPO_MIN_DEPOSIT, getMapValueWithoutUnit(map, "ipo_min_deposit") + "원");
        }
        if (mapVerify(map, "put_back_option_who")) {
            appendComment(CHANGED_PUT_BACK_OPTION, getMapValue(map, "put_back_option_who"));
        }
        if (mapVerify(map, "put_back_option_price")) {
            appendComment(CHANGED_PUT_BACK_OPTION, getMapValueWithoutUnit(map, "put_back_option_price") + "원");
        }
        if (mapVerify(map, "put_back_option_deadline")) {
            appendComment(CHANGED_PUT_BACK_OPTION, getMapValue(map, "put_back_option_deadline"));
        }

        // 3순위: 공모 관련 고정 정보
        if (mapVerify(map, "profits")) {
            appendComment(CHANGED_PROFITS, getMapValueWithUnit(map, "profits") + "원");
        }
        if (mapVerify(map, "sales")) {
            appendComment(CHANGED_SALES, getMapValueWithUnit(map, "sales") + "원");
        }
        if (mapVerify(map, "number_of_ipo_shares")) {
            appendComment(CHANGED_IPO_SHARES, getMapValueWithUnit(map, "number_of_ipo_shares") + "주");
        }
        if (mapVerify(map, "par_value")) {
            appendComment(CHANGED_PAR_VALUE, getMapValueWithoutUnit(map, "par_value") + "원");
        }
        if (mapVerify(map, "purpose_of_funds")) {
            appendComment(CHANGED_PURPOSE_OF_FUNDS, getMapValue(map, "purpose_of_funds"));
        }

        return getTitle();
    }

    private String commentUnderwriter(String commentJson) {
        // underwriter(주간사) json을 해석하고 그 내용을 String으로 반환합니다.
        Map<String, Object> map = jsonToMap(commentJson);
        assert map != null;
        if (map.isEmpty())
            return "";

        if (mapVerify(map, "ind_can_max")) {
            appendComment(CHANGED_UNDERWRITER_TOTAL, "최대 청약 수량: " + map.get("ind_can_max"));
        }
        if (mapVerify(map, "ind_can_min")) {
            appendComment(CHANGED_UNDERWRITER_TOTAL, "최소 청약 수량: " + map.get("ind_can_min"));
        }
        if (mapVerify(map, "ind_total_max")) {
            appendComment(CHANGED_UNDERWRITER_TOTAL, "전체 수량 최대: " + map.get("ind_total_max"));
        }
        if (mapVerify(map, "ind_total_min")) {
            appendComment(CHANGED_UNDERWRITER_TOTAL, "전체 수량 최소: " + map.get("ind_total_min"));
        }
        if (mapVerify(map, "sub_min_quan")) {
            appendComment(CHANGED_UNDERWRITER_MIN_QUAN, "최소 청약 수량: " + map.get("sub_min_quan"));
        }
        if (mapVerify(map, "sub_deposit_percent")) {
            appendComment(CHANGED_UNDERWRITER_DEPOSIT_PERCENT, "증거금률: " + map.get("sub_deoposit_percent") + "%");
        }

        return getTitle();
    }

    private Map<String, Object> jsonToMap(String json) {
        try {
            return objectMapper.readValue(json, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
