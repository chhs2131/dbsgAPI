package com.dbsgapi.dbsgapi.api.ipo.controller;

import com.dbsgapi.dbsgapi.api.ipo.domain.DatePeriod;
import com.dbsgapi.dbsgapi.api.ipo.domain.IpoPaging;
import com.dbsgapi.dbsgapi.api.ipo.domain.IpoSequence;
import com.dbsgapi.dbsgapi.api.ipo.domain.Sort;
import com.dbsgapi.dbsgapi.api.ipo.dto.*;
import com.dbsgapi.dbsgapi.api.ipo.service.CommentService;
import com.dbsgapi.dbsgapi.api.ipo.service.IpoService;
import com.dbsgapi.dbsgapi.global.response.CustomException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.dbsgapi.dbsgapi.global.response.ErrorCode.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "IPO", description = "청약주식(공모주/실권주/스팩주) 관리를 위한 API")
@RequestMapping("/api/v1/ipo")
public class IpoApiController {
    private final IpoService ipoService;
    private final CommentService commentService;

    @GetMapping(value = "")
    @Operation(summary = "IPO 목록을 반환", description = "IPO 목록을 최근 등록된 순으로 반환합니다.")
    public ResponseEntity<List<IpoSummaryDto>> getIpoList(
            @Parameter(description = "페이지 번호") @RequestParam(required = false, defaultValue = "1") int page,
            @Parameter(description = "페이지당 반환갯수") @RequestParam(required = false, defaultValue = "100") int num,
            @Parameter(description = "기준 일자 (deprecated)") @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDate).now()}")
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate targetDate,
            @Parameter(description = "기준일 진행 단계") @RequestParam(required = false, defaultValue = "ALL") IpoSequence state,
            @Parameter(description = "정렬 (현재 사용되지 않음)") @RequestParam(required = false, defaultValue = "asc") String sort,
            @Parameter(description = "청약철회된 종목 반환여부") @RequestParam(required = false, defaultValue = "false") Boolean withCancelItem,
            @Parameter(description = "기준 시작 일자") @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @Parameter(description = "기준 종료 일자") @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
    ) {
        try {
            IpoPaging ipoPaging = new IpoPaging(page, num, state, targetDate, startDate, endDate, withCancelItem,
                    Sort.from(sort));
            IpoSequence.validate(state);  // 아직 처리할 수 없는 sequence 예외처리

            // IPO 목록 조회
            List<IpoSummaryDto> listIpo = ipoService.selectIpos(ipoPaging);
            return new ResponseEntity<>(listIpo, HttpStatus.OK);
        } catch (IllegalStateException e) {
            throw new CustomException(IPO_LIST_NOT_FOUND_EXCEPTION);
        } catch (IllegalArgumentException e) {
            throw new CustomException(IPO_ILLEGAL_ARGUMENT_EXCEPTION);
        }
    }

    @GetMapping(value = "/{ipoIndex}")
    @Operation(summary = "단일 IPO 종목 상세조회", description = "ipoIndex에 해당하는 종목에 상세 정보를 반환합니다.")
    public ResponseEntity<IpoDetailDto> getIpo(@PathVariable("ipoIndex") long ipoIndex) {
        IpoDetailDto ipoData = new IpoDetailDto();
        try {
            ipoData.setIpo(ipoService.selectIpo(ipoIndex));
            ipoData.setComment(commentService.selectIpoComment(ipoIndex));
            ipoData.setUnderwriter(ipoService.selectIpoUnderwriter(ipoIndex));
            return new ResponseEntity<>(ipoData, HttpStatus.OK);
        } catch (IllegalStateException e) {
            throw new CustomException(IPO_DETAIL_NOT_FOUND_EXCEPTION);
        }
    }

    @GetMapping(value = "/{ipoIndex}/basic")
    @Operation(summary = "IPO 기본정보 확인", description = "해당 종목에 기본정보를 조회합니다.")
    public ResponseEntity<IpoDto> getIpoDetail(@PathVariable("ipoIndex") long ipoIndex) {
        try {
            IpoDto ipoData = ipoService.selectIpo(ipoIndex);
            return new ResponseEntity<>(ipoData, HttpStatus.OK);
        } catch (IllegalStateException e) {
            throw new CustomException(IPO_DETAIL_NOT_FOUND_EXCEPTION);
        }
    }

    @GetMapping(value = "/{ipoIndex}/underwriter")
    @Operation(summary = "IPO 주간사 정보 확인", description = "해당 종목에 주간사 정보를 조회합니다.")
    public ResponseEntity<List<IpoUnderwriterDto>> getIpoUnderwriter(@PathVariable("ipoIndex") long ipoIndex) {
        try {
            List<IpoUnderwriterDto> ipoData = ipoService.selectIpoUnderwriter(ipoIndex);
            return new ResponseEntity<>(ipoData, HttpStatus.OK);
        } catch (IllegalStateException e) {
            throw new CustomException(IPO_UNDERWRITER_NOT_FOUND_EXCEPTION);
        }
    }

    @GetMapping(value = "/{ipoIndex}/comment")
    @Operation(summary = "특정 종목의 Comment 조회", description = "특정 종목의 코멘트(히스토리)를 조회합니다.")
    public ResponseEntity<List<IpoCommentDto>> getIpoCommentList(@PathVariable("ipoIndex") long ipoIndex) {
        try {
            List<IpoCommentDto> ipoData = commentService.selectIpoComment(ipoIndex);
            return new ResponseEntity<>(ipoData, HttpStatus.OK);
        } catch (IllegalStateException e) {
            throw new CustomException(IPO_COMMENT_LIST_NOT_FOUND_EXCEPTION);
        }
    }

    @GetMapping(value = "/comment")
    @Operation(summary = "IPO Comment 조회", description = "코멘트(히스토리)를 조회합니다. 이 때, 최근 코멘트가 앞쪽 페이지에 위치합니다.")
    public ResponseEntity<List<IpoCommentDto>> getIpoCommentList(
            @Parameter(description = "조회 시작일자") @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDate).now().minusDays(14)}")
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @Parameter(description = "조회 종료일자") @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDate).now()}")
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
    ) {
        try {
            DatePeriod datePeriod = DatePeriod.from(startDate, endDate);
            List<IpoCommentDto> ipoData = commentService.selectIpoCommentList(datePeriod);
            return new ResponseEntity<>(ipoData, HttpStatus.OK);
        } catch (IllegalStateException e) {
            throw new CustomException(IPO_COMMENT_LIST_NOT_FOUND_EXCEPTION);
        } catch (IllegalArgumentException e) {
            throw new CustomException(IPO_ILLEGAL_ARGUMENT_EXCEPTION);
        }
    }

    @GetMapping(value = "/comment/{commentIndex}")
    @Operation(summary = "특정 Comment 확인", description = "단일 comment를 조회합니다.")
    public ResponseEntity<IpoCommentDto> getIpoComment(@PathVariable("commentIndex") long commentIndex) {
        try {
            IpoCommentDto ipoData = commentService.selectIpoCommentIndex(commentIndex);
            return new ResponseEntity<>(ipoData, HttpStatus.OK);
        } catch (IllegalStateException e) {
            throw new CustomException(IPO_COMMENT_NOT_FOUND_EXCEPTION);
        }
    }

    @GetMapping(value = "/schedule")
    @Operation(summary = "지정 기간내에 일정을 확인", description = "지정한 기간내에 일정을 모두 확인합니다.")
    public ResponseEntity<List<IpoSummaryDto>> getScheduleList(
            @Parameter(description = "조회 시작일자") @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDate).now().minusDays(14)}")
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @Parameter(description = "조회 종료일자") @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDate).now()}")
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        try {
            DatePeriod datePeriod = DatePeriod.from(startDate, endDate);
            List<IpoSummaryDto> ipoData = ipoService.selectIpoScheduleList(datePeriod);
            return new ResponseEntity<>(ipoData, HttpStatus.OK);
        } catch (IllegalStateException e) {
            throw new CustomException(IPO_SCHEDULE_NOT_FOUND_EXCEPTION);
        } catch (IllegalArgumentException e) {
            throw new CustomException(IPO_ILLEGAL_ARGUMENT_EXCEPTION);
        }
    }
}
