package com.dbsgapi.dbsgapi.ipo.service;

import com.dbsgapi.dbsgapi.common.JsonCommentConverter;
import com.dbsgapi.dbsgapi.ipo.dto.*;
import com.dbsgapi.dbsgapi.ipo.mapper.IpoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class IpoServiceImpl implements IpoService{
    @Autowired
    private IpoMapper ipoMapper;

    @Override
    public List<IpoSummaryDto> selectIpos(String queryString, int page, int num) throws Exception {
        // todo ipolist keyword 구분하는 구문 추가
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("limit", num);
        map.put("offset", page * num - num);
        map.put("queryString", queryString);

        // ipo list 조회 로직
        return ipoMapper.selectIpos(map);
    }

    @Override
    public IpoDto selectIpo(long ipoIndex) throws Exception {
        return ipoMapper.selectIpo(ipoIndex);
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
        ipoCommentList.removeIf(ipoComment -> "".equals(ipoComment.getComment()));

        return ipoCommentList;
    }

    @Override
    public IpoCommentDto selectIpoCommentIndex(long commentIndex) throws Exception {
        return ipoMapper.selectIpoCommentIndex(commentIndex);
    }

    @Override
    public List<IpoCommentDto> selectIpoCommentList(int page, int num) throws Exception {
        // map.put 전에 각 데이터가 0이 아닌지 확인하는 Verify 로직 필요.
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("limit", num);
        map.put("offset", page * num - num);

        // 쿼리문 요청, 조회
        List<IpoCommentDto> ipoCommentList = ipoMapper.selectIpoCommentList(map);

        // 결과 중 내용이 없는 코멘트가 있는 경우 제거한다.
        ipoCommentList.removeIf(ipoComment -> "".equals(ipoComment.getComment()));

        return ipoCommentList;
    }

    @Override
    public List<IpoUnderwriterDto> selectIpoUnderwriter(long ipoIndex) throws Exception {
        return ipoMapper.selectIpoUnderwriter(ipoIndex);
    }
}
