package com.dbsgapi.dbsgapi.api.ipo.controller;

import com.dbsgapi.dbsgapi.api.ipo.domain.IpoSequence;
import com.dbsgapi.dbsgapi.api.ipo.dto.*;
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

    @GetMapping(value="")
    @Operation(summary="IPO 목록을 반환", description="IPO 목록을 최근 등록된 순으로 반환합니다.")
    public ResponseEntity<List<IpoSummaryDto>> getIpoList(
            @Parameter(description="페이지 번호") @RequestParam(required=false, defaultValue="1") int page,
            @Parameter(description="페이지당 반환갯수") @RequestParam(required=false, defaultValue="100") int num,
            @Parameter(description="기준 일자") @RequestParam(required=false, defaultValue="#{T(java.time.LocalDate).now()}")
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate refDate,
            @Parameter(description="기준일 진행 단계") @RequestParam(required=false, defaultValue="ALL")IpoSequence state,
            @Parameter(description="정렬") @RequestParam(required=false, defaultValue="asc")String sort
            //@Parameter(description="청약철회된 종목 반환여부") @RequestParam(required=false, defaultValue="false")Boolean cancelValue
            ) throws Exception {
        // state 변수를 queryString 변수로 변환
        String queryString;
        String targetDate = "'" + refDate.toString() + "'";

        if(state == IpoSequence.ALL)
            queryString = "1=1";
        else if(state == IpoSequence.TODAY)
            // 오늘 진행되는 일정을 보여준다. (금일 진행되는 수요예측, 공모청약, 환불, 상장, 상장 철회)
            queryString =
                    targetDate + "= ipo_cancel_date OR " +
                    targetDate + "= ipo_debut_date OR " +
                    targetDate + "= ipo_refund_date OR " +
                    targetDate + " BETWEEN ipo_forecast_start AND ipo_forecast_end OR " +
                    targetDate + " BETWEEN ipo_start_date AND ipo_end_date";
        else if(state == IpoSequence.BEFORE_FORECAST)
            // 수요예측 예정인 종목을 보여준다.
            queryString = targetDate + "< ipo_forecast_start";
        else if(state == IpoSequence.BEFORE_IPO || state == IpoSequence.AFTER_FORECAST)
            // 공모청약 예정인 종목을 보여준다.
            queryString = targetDate + " BETWEEN DATE_ADD(ipo_forecast_end, INTERVAL 1 DAY) AND DATE_SUB(ipo_start_date, INTERVAL 1 DAY)";
        else if(state == IpoSequence.BEFORE_REFUND || state == IpoSequence.AFTER_IPO)
            // 환불 예정인 종목을 보여준다.
            queryString = targetDate + " BETWEEN DATE_ADD(ipo_end_date, INTERVAL 1 DAY) AND DATE_SUB(ipo_refund_date, INTERVAL 1 DAY)";
        else if(state == IpoSequence.BEFORE_DEBUT || state == IpoSequence.AFTER_REFUND)
            // 상장 예정인 종목을 보여준다.
            queryString = targetDate + " BETWEEN DATE_ADD(ipo_refund_date, INTERVAL 1 DAY) AND DATE_SUB(ipo_debut_date, INTERVAL 1 DAY)";
        else if(state == IpoSequence.AFTER_DEBUT)
            // 상장 완료한 종목을 보여준다.
            queryString = targetDate + ">= ipo_debut_date";
        else
            throw new CustomException(IPO_LIST_NOT_SUPPORTED_STATE);

        // IPO 목록 조회
        List<IpoSummaryDto> listIpo = ipoService.selectIpos(queryString, page, num);

        // 예외처리 및 결과반환
        if(listIpo.isEmpty())
            throw new CustomException(IPO_LIST_NOT_FOUND_EXCEPTION);
        return new ResponseEntity<>(listIpo, HttpStatus.OK);
    }

    // TODO 단일 조회 path mapping uri 변경 필요 detail <->
    @GetMapping(value="/{ipoIndex}")
    @Operation(summary="단일 IPO 종목을 상세조회", description="ipoIndex에 해당하는 종목에 상세 정보를 반환합니다.")
    public ResponseEntity<IpoDetailDto> getIpo(@PathVariable("ipoIndex") long ipoIndex) throws Exception {
        IpoDetailDto ipoData = new IpoDetailDto();
        ipoData.setIpo(ipoService.selectIpo(ipoIndex));
        ipoData.setComment(ipoService.selectIpoComment(ipoIndex));
        ipoData.setUnderwriter(ipoService.selectIpoUnderwriter(ipoIndex));

        if(ipoData.getIpo() == null)
            throw new CustomException(IPO_DETAIL_NOT_FOUND_EXCEPTION);
        return new ResponseEntity<>(ipoData, HttpStatus.OK);
    }
    
    @GetMapping(value="/detail/{ipoIndex}")
    @Operation(summary="IPO 기본정보 확인", description="해당 종목에 기본정보를 조회합니다.")
    public ResponseEntity<IpoDto> getIpoDetail(@PathVariable("ipoIndex") long ipoIndex) throws Exception {
        IpoDto ipoData = ipoService.selectIpo(ipoIndex);

        if(ipoData == null)
            throw new CustomException(IPO_DETAIL_NOT_FOUND_EXCEPTION);
        return new ResponseEntity<>(ipoData, HttpStatus.OK);
    }

    @GetMapping(value="/schedule")
    @Operation(summary="지정 기간내에 일정을 확인", description="지정한 기간내에 일정을 모두 확인합니다.")
    public ResponseEntity<List<IpoSummaryDto>> getScheduleList(
            @Parameter(description="조회 시작일자") String startDate,
            @Parameter(description="조회 종료일자") String endDate) throws Exception {
        //TODO 파라미터 반드시 입력해야되는지 확인
        //TODO Date Parameter를 String이 아닌 LocalDate Type으로 받도록 설정
        List<IpoSummaryDto> ipoData = ipoService.selectIpoScheduleList(startDate, endDate);

        if(ipoData.isEmpty())
            throw new CustomException(IPO_SCHEDULE_NOT_FOUND_EXCEPTION);
        return new ResponseEntity<>(ipoData, HttpStatus.OK);
    }

    @GetMapping(value="/comment")
    @Operation(summary="IPO Comment 조회", description="코멘트(히스토리)를 조회합니다. 이 때, 최근 코멘트가 앞쪽 페이지에 위치합니다.")
    public ResponseEntity<List<IpoCommentDto>> getIpoCommentList(
            @Parameter(description="특정 ipoIndex만 조회") @RequestParam(required=false, defaultValue="0") int ipoIndex,
            @Parameter(description="조회 시작일자") @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDate).now().minusDays(7)}")
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @Parameter(description="조회 종료일자") @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDate).now()}")
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
    ) throws Exception {
        List<IpoCommentDto> ipoData;
        if(ipoIndex == 0) {  // 전체 조회
            ipoData = ipoService.selectIpoCommentList(startDate, endDate);
        }else if(ipoIndex > 0) {  // 특정 종목만 조회
            ipoData = ipoService.selectIpoComment(ipoIndex);
        } else {
            throw new CustomException(IPO_COMMENT_WRONG_PARAMETER_EXCEPTION);
        }

        if(ipoData.isEmpty())
            throw new CustomException(IPO_COMMENT_LIST_NOT_FOUND_EXCEPTION);
        return new ResponseEntity<>(ipoData, HttpStatus.OK);
    }

    @GetMapping(value="/comment/{commentIndex}")
    @Operation(summary="특정 Comment 확인", description="단일 comment를 조회합니다. commentIndex를 통해 조회합니다. (ipoIndex 즉, 종목번호 아님)")
    public ResponseEntity<IpoCommentDto> getIpoComment(@PathVariable("commentIndex") long commentIndex) throws Exception {
        IpoCommentDto ipoData = ipoService.selectIpoCommentIndex(commentIndex);

        if(ipoData == null)
            throw new CustomException(IPO_COMMENT_NOT_FOUND_EXCEPTION);
        return new ResponseEntity<>(ipoData, HttpStatus.OK);
    }

    @GetMapping(value="/underwriter/{ipoIndex}")
    @Operation(summary="IPO 주간사 정보 확인", description="해당 종목에 주간사 정보를 조회합니다.")
    public ResponseEntity<List<IpoUnderwriterDto>> getIpoUnderwriter(@PathVariable("ipoIndex") long ipoIndex) throws Exception {
        List<IpoUnderwriterDto> ipoData = ipoService.selectIpoUnderwriter(ipoIndex);

        if(ipoData.isEmpty())
            throw new CustomException(IPO_UNDERWRITER_NOT_FOUND_EXCEPTION);
        return new ResponseEntity<>(ipoData, HttpStatus.OK);
    }
}
