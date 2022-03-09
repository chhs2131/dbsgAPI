package com.dbsgapi.dbsgapi.ipo.controller;

import com.dbsgapi.dbsgapi.ipo.dto.*;
import com.dbsgapi.dbsgapi.ipo.service.IpoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Tag(name = "IPO", description = "청약주식(공모주/실권주/스팩주) 관리를 위한 API")
@RequestMapping("/api/v1/ipo")
public class IpoApiController {
    @Autowired
    private IpoService ipoService;

    @RequestMapping(value="", method = RequestMethod.GET)
    @Operation(summary="IPO 목록을 반환", description="IPO 목록을 최근 등록된 순으로 반환합니다.")
    public ResponseEntity<List<IpoSummaryDto>> getIpoList() throws Exception {
        List<IpoSummaryDto> listIpo = ipoService.selectIpos();
        log.debug(listIpo.toString());
        return new ResponseEntity<>(listIpo, HttpStatus.OK);
    }

    @RequestMapping(value="/{ipoIndex}", method = RequestMethod.GET)
    @Operation(summary="단일 IPO 종목을 상세조회", description="ipoIndex에 해당하는 종목에 상세 정보를 반환합니다.")
    public ResponseEntity<IpoDetailDto> getIpo(@PathVariable("ipoIndex") long ipoIndex) throws Exception {
        IpoDetailDto ipoData = new IpoDetailDto();
        ipoData.setIpo(ipoService.selectIpo(ipoIndex));
        ipoData.setComment(ipoService.selectIpoComment(ipoIndex));
        ipoData.setUnderwriter(ipoService.selectIpoUnderwriter(ipoIndex));

        return new ResponseEntity<>(ipoData, HttpStatus.OK);
    }
    
    @RequestMapping(value="/detail/{ipoIndex}", method = RequestMethod.GET)
    @Operation(summary="IPO 기본정보 확인", description="해당 종목에 기본정보를 조회합니다.")
    public ResponseEntity<IpoDto> getIpoDetail(@PathVariable("ipoIndex") long ipoIndex) throws Exception {
        IpoDto ipoData = ipoService.selectIpo(ipoIndex);
        log.debug(ipoData.toString());
        return new ResponseEntity<>(ipoData, HttpStatus.OK);
    }

    @RequestMapping(value="/comment", method = RequestMethod.GET)
    @Operation(summary="전체 IPO Comment 확인", description="최근 코멘트(히스토리)를 전부 조회합니다. 페이징 변수 추가.")
    public ResponseEntity<List<IpoCommentDto>> getIpoCommentList() throws Exception {
        List<IpoCommentDto> ipoData = ipoService.selectIpoCommentList();
        log.debug(ipoData.toString());
        return new ResponseEntity<>(ipoData, HttpStatus.OK);
    }

    @RequestMapping(value="/comment/{ipoIndex}", method = RequestMethod.GET)
    @Operation(summary="단일 IPO Comment 확인", description="해당 종목에 코멘트(히스토리)를 조회합니다.")
    public ResponseEntity<List<IpoCommentDto>> getIpoComment(@PathVariable("ipoIndex") long ipoIndex) throws Exception {
        List<IpoCommentDto> ipoData = ipoService.selectIpoComment(ipoIndex);
        log.debug(ipoData.toString());
        return new ResponseEntity<>(ipoData, HttpStatus.OK);
    }

    @RequestMapping(value="/underwriter/{ipoIndex}", method = RequestMethod.GET)
    @Operation(summary="IPO 주간사 정보 확인", description="해당 종목에 주간사 정보를 조회합니다.")
    public ResponseEntity<List<IpoUnderwriterDto>> getIpoUnderwriter(@PathVariable("ipoIndex") long ipoIndex) throws Exception {
        List<IpoUnderwriterDto> ipoData = ipoService.selectIpoUnderwriter(ipoIndex);
        log.debug(ipoData.toString());
        return new ResponseEntity<>(ipoData, HttpStatus.OK);
    }
}
