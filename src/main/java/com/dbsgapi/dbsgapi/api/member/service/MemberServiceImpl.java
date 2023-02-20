package com.dbsgapi.dbsgapi.api.member.service;

import com.dbsgapi.dbsgapi.api.member.dto.MemberDto;
import com.dbsgapi.dbsgapi.api.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberMapper memberMapper;

    @Override
    public MemberDto findMemberByUuid(String uuid) {
        return memberMapper.getMemberByUuid(uuid).orElse(null);
    }
}
