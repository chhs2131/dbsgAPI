package com.dbsgapi.dbsgapi.api.member.service;

import com.dbsgapi.dbsgapi.api.member.dto.MemberFavoriteDto;

import java.util.List;

public interface MemberService {
    List<MemberFavoriteDto> selectFavorites(long userNo) throws Exception;
    MemberFavoriteDto selectFavorite(MemberFavoriteDto memberFavoriteDto) throws Exception;
    void insertFavorite(MemberFavoriteDto memberFavoriteDto) throws Exception;
    void deleteFavorite(MemberFavoriteDto memberFavoriteDto) throws Exception;
}
