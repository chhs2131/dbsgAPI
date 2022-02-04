package com.dbsgapi.dbsgapi.member.controller;

import com.dbsgapi.dbsgapi.ipo.dto.IpoSummaryDto;
import com.dbsgapi.dbsgapi.ipo.service.IpoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@Tag(name = "Member", description = "유저별로 귀속된 데이터를 관리하는 API")
@RequestMapping("/api/v1/member")
public class MemberApiController {
    // 즐겨찾기 리스트 가져오기
    // 즐겨찾기 단일 가져오기 (등록여부 확인용)
    // 즐겨찾기 추가하기
    // 즐겨찾기 삭제하기
    // 즐겨찾기 수정하기 (필요없음)

    // 알람 리스트 가져오기
    // 알람 단일 가져오기
    // 알람 추가하기
    // 알람 삭제하기
    // 알람 수정하기

    // 옵션 가져오기
    // 옵션 어쩌구저쩌구

    // 사용자별 가지고있는 증권사 가져오기

    // 사용자별 수익률 계산 가져오기

    /*
    @Autowired
    private IpoService ipoService;

    @RequestMapping(value="", method = RequestMethod.GET)
    @Operation(summary="IPO 목록을 반환", description="IPO 목록을 최근 등록된 순으로 반환합니다.")
    public ResponseEntity<List<IpoSummaryDto>> getIpoList() throws Exception {
        List<IpoSummaryDto> listIpo = ipoService.selectIpos();
        log.debug(listIpo.toString());
        return new ResponseEntity<>(listIpo, HttpStatus.OK);
    }
    */
}
