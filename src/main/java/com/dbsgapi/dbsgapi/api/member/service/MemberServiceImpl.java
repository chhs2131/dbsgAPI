package com.dbsgapi.dbsgapi.api.member.service;

import com.dbsgapi.dbsgapi.api.member.dto.MemberFavoriteDto;
import com.dbsgapi.dbsgapi.api.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberMapper memberMapper;

    @Override
    public List<MemberFavoriteDto> selectFavorites(long userNo) throws Exception {
        return memberMapper.selectFavorites(userNo);
    }

    @Override
    public MemberFavoriteDto selectFavorite(MemberFavoriteDto memberFavoriteDto) throws Exception {
        return memberMapper.selectFavorite(memberFavoriteDto);
    }

    @Override
    public void insertFavorite(MemberFavoriteDto memberFavoriteDto) throws Exception {
        memberMapper.insertFavorite(memberFavoriteDto);
    }

    @Override
    public void deleteFavorite(MemberFavoriteDto memberFavoriteDto) throws Exception {
        memberMapper.deleteFavorite(memberFavoriteDto);
    }
}
