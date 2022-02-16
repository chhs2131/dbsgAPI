package com.dbsgapi.dbsgapi.member.controller;

import com.dbsgapi.dbsgapi.ipo.dto.IpoSummaryDto;
import com.dbsgapi.dbsgapi.ipo.service.IpoService;
import com.dbsgapi.dbsgapi.login.dto.MemberDto;
import com.dbsgapi.dbsgapi.login.kakao.dto.KakaoOAuthDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.dbsgapi.dbsgapi.security.SecurityUtil;

import java.util.List;
import java.util.Optional;


/*
	@RequestMapping(value="/api/board", method=RequestMethod.GET)
	public List<BoardDto> openBoardList() throws Exception {
		return boardService.selectBoardList();
	}

	@RequestMapping(value="/api/board/write", method=RequestMethod.POST)
	public void insertBoard(@RequestBody BoardDto board) throws Exception {
		boardService.insertBoard(board);
	}

	@RequestMapping(value="/api/board/{boardIdx}", method=RequestMethod.GET)
	public BoardDto openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception {
		return boardService.selectBoardDetail(boardIdx);
	}

	@RequestMapping(value="/api/board/{boardIdx}", method=RequestMethod.PUT)
	public void updateBoard(@RequestBody BoardDto board) throws Exception {
		boardService.updateBoard(board);
	}

	@RequestMapping(value="/api/board/{boardIdx}", method=RequestMethod.DELETE)
	public void deleteBoard(BoardDto board) throws Exception {
		boardService.deleteBoard(board.getBoardIdx());
	}


    1. orElse() 메소드 : 저장된 값이 존재하면 그 값을 반환하고, 값이 존재하지 않으면 인수로 전달된 값을 반환함.
    2. orElseGet() 메소드 : 저장된 값이 존재하면 그 값을 반환하고, 값이 존재하지 않으면 인수로 전달된 람다 표현식의 결괏값을 반환함.
    3. orElseThrow() 메소드 : 저장된 값이 존재하면 그 값을 반환하고, 값이 존재하지 않으면 인수로 전달된 예외를 발생시킴.
 */


@Slf4j
@RestController
@Tag(name = "Member", description = "(아직 제작중임!!) 유저별로 귀속된 데이터를 관리하는 API")
@RequestMapping("/api/v1/member")
public class MemberApiController {
    // 즐겨찾기 리스트 가져오기
    @RequestMapping(value={"/favorite"}, method=RequestMethod.GET)
    @Operation(summary="즐겨찾기 리스트 가져오기", description="uuid를 전달하고 사용자가 즐겨찾기한 종목들의 index값을 가져옵니다.")
    public String getFavoriteList() throws Exception {
        long userNo = SecurityUtil.getUserNo();
        return "favorite test userNo: " + userNo;
    }

    // 즐겨찾기 단일 가져오기 (등록여부 확인용)
    @RequestMapping(value={"/favorite/{favoriteIndex}"}, method=RequestMethod.GET)
    @Operation(summary="즐겨찾기 항목 상세조회", description="uuid와 해당 종목의 index값을 전달하여 특정 종목의 즐겨찾기 여부를 조회합니다.")
    public String getFavorite(@Parameter(description="DBSG 회원번호") String uuid, String kind, String kind_no, @PathVariable String favoriteIndex) throws Exception {
        return "favorite test" + uuid;
    }

    // 즐겨찾기 추가하기
    @RequestMapping(value={"/favorite/insert"}, method=RequestMethod.POST)
    @Operation(summary="즐겨찾기 신규등록", description="uuid와 해당 종목의 index값을 전달하여 새로운 즐겨찾기를 등록합니다.")
    public String insertFavorite(String kind, String kind_no) throws Exception {
        return "favorite test";
    }

    // 즐겨찾기 삭제하기
    @RequestMapping(value={"/favorite/{favoriteIndex}"}, method=RequestMethod.DELETE)
    @Operation(summary="즐겨찾기 삭제", description="uuid와 해당 종목의 index값을 KEY로 즐겨찾기를 취소합니다.")
    public String deleteFavorite(@Parameter(description = "DBSG 회원번호") String uuid, @PathVariable String favoriteIndex) throws Exception {
        return "favorite test" + uuid;
    }


    /*

    // 즐겨찾기 리스트 가져오기
    @RequestMapping(value={"/favorite"}, method=RequestMethod.GET)
    @Operation(summary="즐겨찾기 리스트 가져오기", description="uuid를 전달하고 사용자가 즐겨찾기한 종목들의 index값을 가져옵니다.")
    public String getFavoriteList(@Parameter(description="DBSG 회원번호") String uuid) throws Exception {
        return "favorite test" + uuid;
    }

    // 즐겨찾기 단일 가져오기 (등록여부 확인용)
    @RequestMapping(value={"/favorite/{favoriteIndex}"}, method=RequestMethod.GET)
    @Operation(summary="즐겨찾기 항목 상세조회", description="uuid와 해당 종목의 index값을 전달하여 특정 종목의 즐겨찾기 여부를 조회합니다.")
    public String getFavorite(@Parameter(description="DBSG 회원번호") String uuid, String kind, String kind_no, @PathVariable String favoriteIndex) throws Exception {
        return "favorite test" + uuid;
    }

    // 즐겨찾기 추가하기
    @RequestMapping(value={"/favorite/insert"}, method=RequestMethod.POST)
    @Operation(summary="즐겨찾기 신규등록", description="uuid와 해당 종목의 index값을 전달하여 새로운 즐겨찾기를 등록합니다.")
    public String insertFavorite(@Parameter(description="DBSG 회원번호") String uuid, String kind, String kind_no) throws Exception {
        return "favorite test" + uuid;
    }

    // 즐겨찾기 삭제하기
    @RequestMapping(value={"/favorite/{favoriteIndex}"}, method=RequestMethod.DELETE)
    @Operation(summary="즐겨찾기 삭제", description="uuid와 해당 종목의 index값을 KEY로 즐겨찾기를 취소합니다.")
    public String deleteFavorite(@Parameter(description = "DBSG 회원번호") String uuid, @PathVariable String favoriteIndex) throws Exception {
        return "favorite test" + uuid;
    }
     */

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
