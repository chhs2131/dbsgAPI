package com.dbsgapi.dbsgapi.api.ipo.service;

import com.dbsgapi.dbsgapi.api.ipo.domain.IpoSequence;
import com.dbsgapi.dbsgapi.api.ipo.dto.IpoCommentDto;
import com.dbsgapi.dbsgapi.api.ipo.dto.IpoDto;
import com.dbsgapi.dbsgapi.api.ipo.dto.IpoSummaryDto;
import com.dbsgapi.dbsgapi.api.ipo.dto.IpoUnderwriterDto;
import com.dbsgapi.dbsgapi.api.ipo.mapper.IpoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class IpoServiceImpl implements IpoService {
    private final IpoMapper ipoMapper;

    @Override
    public List<IpoSummaryDto> selectIpos(LocalDate targetDate, LocalDate startDate, LocalDate endDate, IpoSequence ipoSequence, Boolean withCancelItem, int page, int num, String sort) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("limit", num);
        map.put("offset", page * num - num);
        map.put("targetDate", targetDate);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("ipoSequence", ipoSequence.toString());
        map.put("withCancelItem", withCancelItem);
        map.put("sort", sort);

        // ipo list 조회 로직
        return ipoMapper.selectIpos(map);
    }

    @Override
    public IpoDto selectIpo(long ipoIndex) throws IllegalStateException {
        return ipoMapper.selectIpo(ipoIndex).orElseThrow(IllegalStateException::new);
    }

    @Override
    public List<IpoSummaryDto> selectIpoScheduleList(String startDate, String endDate) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        // map.put 전에 날짜형태가 맞는지 확인하고, 정상적으로 데이터가 입력되었는지 확인하는 Verify 로직 필요.
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        return ipoMapper.selectIpoScheduleList(map);
    }

    @Override
    public List<IpoCommentDto> selectIpoComment(long ipoIndex) throws Exception {
        // 쿼리문 요청, 조회
        List<IpoCommentDto> ipoCommentList = ipoMapper.selectIpoComment(ipoIndex);
        // 결과 중 내용이 없는 코멘트가 있는 경우 제거한다.
        ipoCommentList.removeIf(ipoComment -> "".equals(ipoComment.getTitle()));

        return ipoCommentList;
    }

    @Override
    public IpoCommentDto selectIpoCommentIndex(long commentIndex) throws IllegalStateException {
        return ipoMapper.selectIpoCommentIndex(commentIndex).orElseThrow(IllegalStateException::new);
    }

    @Override
    public List<IpoCommentDto> selectIpoCommentList(LocalDate startDate, LocalDate endDate) throws Exception {
        // map.put 전에 각 데이터가 0이 아닌지 확인하는 Verify 로직 필요.
        Map<String, String> map = new HashMap<String, String>();
        map.put("startDate", startDate.toString());
        map.put("endDate", endDate.toString());

        // 쿼리문 요청, 조회
        List<IpoCommentDto> ipoCommentList = ipoMapper.selectIpoCommentList(map);

        // 결과 중 내용이 없는 코멘트가 있는 경우 제거한다. (null)
        ipoCommentList.removeIf(ipoComment -> "".equals(ipoComment.getTitle()));

        // 리스트에 신규상장이 있는지 한바퀴 둘러보고 보관하기
        Map<Long, IpoCommentDto> newRegisterCommentList = new HashMap<>();
        for (IpoCommentDto commentTemp : ipoCommentList) {
            if ("신규 등록되었습니다.".equals(commentTemp.getTitle())) {
                newRegisterCommentList.put(commentTemp.getIpoIndex(), commentTemp);
            }
        }
        // 당일 신규 등록된 종목의 경우 해당일에 다른 코멘트들은 제외한다.
        ipoCommentList.removeIf(ipoComment -> commentIsNew(ipoComment, newRegisterCommentList));

        return ipoCommentList;
    }

    private boolean commentIsNew(IpoCommentDto ipoComment, Map<Long, IpoCommentDto> newRegisterCommentList) {
        IpoCommentDto newRegisterComment = newRegisterCommentList.get(ipoComment.getIpoIndex());
        if (newRegisterComment == null) return false;

        if (!newRegisterComment.getRegistDate().equals(ipoComment.getRegistDate())) {
            // comment 발행일이 다른 것은 제거대상 x
            return false;
        }
        if (newRegisterComment.getCommentIndex() != ipoComment.getCommentIndex()) {
            // commentIndex가 다른 것 (= 신규상장 코멘트가 아닌 것)은 제거대상 o
            // System.out.println(ipoComment);
            // System.out.println(newRegisterComment);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<IpoUnderwriterDto> selectIpoUnderwriter(long ipoIndex) throws Exception {
        return ipoMapper.selectIpoUnderwriter(ipoIndex);
    }
}
