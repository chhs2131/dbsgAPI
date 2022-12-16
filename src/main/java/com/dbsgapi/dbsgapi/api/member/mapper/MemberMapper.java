package com.dbsgapi.dbsgapi.api.member.mapper;

import com.dbsgapi.dbsgapi.api.member.dto.MemberFavoriteDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    List<MemberFavoriteDto> selectFavorites(long userNo) throws Exception;

    MemberFavoriteDto selectFavorite(MemberFavoriteDto memberFavoriteDto) throws Exception;

    void insertFavorite(MemberFavoriteDto memberFavoriteDto) throws Exception;

    void deleteFavorite(MemberFavoriteDto memberFavoriteDto) throws Exception;
}
