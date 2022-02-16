package com.dbsgapi.dbsgapi.member.mapper;

import com.dbsgapi.dbsgapi.member.dto.MemberFavoriteDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    List<MemberFavoriteDto> selectFavorites(long userNo) throws Exception;
    MemberFavoriteDto selectFavorite(MemberFavoriteDto memberFavoriteDto) throws Exception;
    void insertFavorite(MemberFavoriteDto memberFavoriteDto) throws Exception;
    void deleteFavorite(long favoriteIndex) throws Exception;
}
