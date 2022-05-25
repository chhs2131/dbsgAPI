package com.dbsgapi.dbsgapi.ipo.controller;

import com.dbsgapi.dbsgapi.ipo.dto.*;
import com.dbsgapi.dbsgapi.ipo.service.IpoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@Tag(name = "IPO", description = "청약주식(공모주/실권주/스팩주) 관리를 위한 API")
@RequestMapping("/api/v1/ipo")
public class IpoApiController {
    @Autowired
    private IpoService ipoService;

    @RequestMapping(value="", method = RequestMethod.GET)
    @Operation(summary="IPO 목록을 반환", description="IPO 목록을 최근 등록된 순으로 반환합니다. (추후 페이징 방식으로 변경 예정)")
    public ResponseEntity<List<IpoSummaryDto>> getIpoList(
            @Parameter(description="페이지 번호") @RequestParam(required=false, defaultValue="1") int page,
            @Parameter(description="페이지당 반환갯수") @RequestParam(required=false, defaultValue="100") int num,
            @Parameter(description="종류") @RequestParam(required=false, defaultValue="all") String kind
    ) throws Exception {
        List<IpoSummaryDto> listIpo = ipoService.selectIpos(kind, page, num);
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

    @RequestMapping(value="/schedule", method = RequestMethod.GET)
    @Operation(summary="지정 기간내에 일정을 확인", description="지정한 기간내에 일정을 모두 확인합니다.")
    public ResponseEntity<List<IpoSummaryDto>> getScheduleList(
            @Parameter(description="조회 시작일자") String startDate, @Parameter(description="조회 종료일자") String endDate) throws Exception {
        List<IpoSummaryDto> ipoData = ipoService.selectIpoScheduleList(startDate, endDate);
        log.debug(ipoData.toString());
        return new ResponseEntity<>(ipoData, HttpStatus.OK);
    }

    @RequestMapping(value="/comment", method = RequestMethod.GET)
    @Operation(summary="IPO Comment 조회", description="코멘트(히스토리)를 조회합니다. 이 때, 최근 코멘트가 앞쪽 페이지에 위치합니다.")
    public ResponseEntity<List<IpoCommentDto>> getIpoCommentList(
            @Parameter(description="특정 ipoIndex만 조회") @RequestParam(required=false, defaultValue="0") int ipoIndex,
            @Parameter(description="페이지 번호") @RequestParam(required=false, defaultValue="1") int page,
            @RequestParam(required=false, defaultValue="20") int num
    ) throws Exception {
        List<IpoCommentDto> ipoData = new ArrayList<IpoCommentDto>();
        if(ipoIndex == 0) {  // 전체 조회
            ipoData = ipoService.selectIpoCommentList(page, num);
            log.debug(ipoData.toString());
        }else if(ipoIndex > 0) {  // 특정 종목만 조회
            ipoData = ipoService.selectIpoComment(ipoIndex);
            log.debug(ipoData.toString());
        } else {
            throw new IllegalArgumentException();
        }
        return new ResponseEntity<>(ipoData, HttpStatus.OK);
    }

    @RequestMapping(value="/comment/{commentIndex}", method = RequestMethod.GET)
    @Operation(summary="특정 Comment 확인", description="단일 comment를 조회합니다. commentIndex를 통해 조회합니다. (ipoIndex 즉, 종목번호 아님)")
    public ResponseEntity<IpoCommentDto> getIpoComment(@PathVariable("commentIndex") long commentIndex) throws Exception {
        IpoCommentDto ipoData = ipoService.selectIpoCommentIndex(commentIndex);
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
