package com.dbsgapi.dbsgapi.api.member.controller;

import com.dbsgapi.dbsgapi.api.member.dto.MemberDto;
import com.dbsgapi.dbsgapi.api.member.service.MemberService;
import com.dbsgapi.dbsgapi.global.authentication.JwtAuthentication;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
@Tag(name = "member", description = "회원 정보 관련 API")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<MemberDto> getMyInformation() {
        JwtAuthentication authentication = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
        String uuid = authentication.getName();
        MemberDto result = memberService.findMemberByUuid(uuid);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
