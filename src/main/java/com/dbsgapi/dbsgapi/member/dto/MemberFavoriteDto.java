package com.dbsgapi.dbsgapi.member.dto;

import lombok.Data;

@Data
public class MemberFavoriteDto {
    private long favoriteIndex;
    private long userNo;
    private String kind;
    private long kindNo;
    private String registDate;
}
