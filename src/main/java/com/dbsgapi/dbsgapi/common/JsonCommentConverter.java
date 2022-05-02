package com.dbsgapi.dbsgapi.common;

public class JsonCommentConverter {
    private String commentType;
    private String commentJson;
    private int commentCode;  // commentType를 통해 code 파생됨.

    JsonCommentConverter(String commentType, String commentJson){
        this.commentType = commentType;
        this.commentJson = commentJson;
        setCommentCode(this.commentType);
    }

    public String toString() {
        String commentReturn;
        switch (this.commentCode) {
            case 0:
                System.out.println("nono");
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
        return "";
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
        return "ipo가 변경되었습니다.";
    }

    private String commentUnderwriter(String commentJson) {
        // json 해석 로직 작성 필요
        return "underwriter가 변경되었습니다.";
    }
}
