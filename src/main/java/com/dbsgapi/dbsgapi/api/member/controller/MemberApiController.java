package com.dbsgapi.dbsgapi.api.member.controller;

import com.dbsgapi.dbsgapi.api.member.dto.MemberFavoriteDto;
import com.dbsgapi.dbsgapi.api.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.dbsgapi.dbsgapi.global.util.SecurityUtil;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Member", description = "유저별 귀속 데이터를 관리하는 API")
@RequestMapping("/api/v1/member")
public class MemberApiController {
    private final MemberService memberService;

    //TODO 추후 member가 아닌 즐겨찾기 controller로 별도로 분리 필요함

    // 즐겨찾기 리스트 가져오기
    @GetMapping(value={"/favorite"})
    @Operation(summary="즐겨찾기 리스트 가져오기", description="사용자가 즐겨찾기한 종목들의 index값을 가져옵니다.")
    public ResponseEntity<List<MemberFavoriteDto>> getFavoriteList() throws Exception {
        long userNo = SecurityUtil.getUserNo();
        return new ResponseEntity<>(memberService.selectFavorites(userNo), HttpStatus.OK);
    }

    // 즐겨찾기 단일 가져오기 (등록여부 확인용)
    @GetMapping(value={"/favorite/{kind}"})
    @Operation(summary="즐겨찾기 단일 조회", description="해당 분류의 index값을 전달하여 특정 종목의 즐겨찾기 여부를 조회합니다. (값이 존재할시 1개에 대한 정보 return)</br> kind 및 kindNo 필요")
    public ResponseEntity<MemberFavoriteDto> getFavorite(
            @Parameter(description="분류(ipo)") @PathVariable String kind,
            @Parameter(description="분류내 PK(index)") long kindNo) throws Exception {
        long userNo = SecurityUtil.getUserNo();
        MemberFavoriteDto memberFavoriteDto = new MemberFavoriteDto();
        memberFavoriteDto.setUserNo(userNo);
        memberFavoriteDto.setKind(kind);
        memberFavoriteDto.setKindNo(kindNo);

        return new ResponseEntity<>(memberService.selectFavorite(memberFavoriteDto), HttpStatus.OK);
    }

    // 즐겨찾기 추가하기
    @PostMapping(value={"/favorite/insert"})
    @Operation(summary="즐겨찾기 신규등록", description="해당 분류의 index값을 전달하여 새로운 즐겨찾기를 등록합니다.</br> kind 및 kind_no 필요")
    public ResponseEntity<String> insertFavorite(@RequestBody MemberFavoriteDto memberFavoriteDto) throws Exception {
        long userNo = SecurityUtil.getUserNo();
        //중복 등록 방지 처리 필요 (기준: kind, kind_no), upsert?
        memberFavoriteDto.setUserNo(userNo);

        memberService.insertFavorite(memberFavoriteDto);
        return new ResponseEntity<>("favorite test userNo: " + userNo, HttpStatus.OK);
    }

    // 즐겨찾기 삭제하기
    @DeleteMapping(value={"/favorite/{favoriteIndex}"})
    @Operation(summary="즐겨찾기 삭제", description="지정한 즐겨찾기(favorite_index)를 삭제합니다. (본인이 등록한것만 삭제가능)")
    public ResponseEntity<String> deleteFavorite(
            @Parameter(description="즐겨찾기 PK(index)") @PathVariable long favoriteIndex) throws Exception {
        long userNo = SecurityUtil.getUserNo();
        MemberFavoriteDto memberFavoriteDto = new MemberFavoriteDto();
        memberFavoriteDto.setUserNo(userNo);
        memberFavoriteDto.setFavoriteIndex(favoriteIndex);

        memberService.deleteFavorite(memberFavoriteDto);
        return new ResponseEntity<>("favorite test userNo: " + userNo, HttpStatus.OK);
    }

    // 즐겨찾기 수정하기 (현재는 필요없음)

    // 알람 리스트 가져오기
    // 알람 단일 가져오기
    // 알람 추가하기
    // 알람 삭제하기
    // 알람 수정하기

    // 옵션 가져오기
    // 옵션 어쩌구저쩌구

    // 사용자별 가지고있는 증권사 가져오기

    // 사용자별 수익률 계산 가져오기
}
