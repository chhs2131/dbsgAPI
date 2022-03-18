package com.dbsgapi.dbsgapi.ipo.service;

import com.dbsgapi.dbsgapi.ipo.dto.*;
import com.dbsgapi.dbsgapi.ipo.mapper.IpoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IpoServiceImpl implements IpoService{
    @Autowired
    private IpoMapper ipoMapper;

    @Override
    public List<IpoSummaryDto> selectIpos() throws Exception {
        return ipoMapper.selectIpos();
    }

    @Override
    public IpoDto selectIpo(long ipoIndex) throws Exception {
        return ipoMapper.selectIpo(ipoIndex);
    }

    @Override
    public List<IpoScheduleDto> selectIpoScheduleList(String startDate, String endDate) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        // map.put 전에 날짜형태가 맞는지 확인하고, 정상적으로 데이터가 입력되었는지 확인하는 Verify 로직 필요.
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        return ipoMapper.selectIpoScheduleList(map);
    }

    @Override
    public List<IpoCommentDto> selectIpoComment(long ipoIndex) throws Exception {
        return ipoMapper.selectIpoComment(ipoIndex);
    }

    @Override
    public List<IpoCommentDto> selectIpoCommentList() throws Exception {
        return ipoMapper.selectIpoCommentList();
    }

    @Override
    public List<IpoUnderwriterDto> selectIpoUnderwriter(long ipoIndex) throws Exception {
        return ipoMapper.selectIpoUnderwriter(ipoIndex);
    }
}
