package com.dbsgapi.dbsgapi.ipo.dto;

import lombok.Data;

@Data
public class IpoCommentDto {
    private long commentIndex;
    private long ipoIndex;
    private String comment;
    private String writer;
    private String registDate;
}
