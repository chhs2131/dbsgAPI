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
    public List<IpoSummaryDto> selectIpos(String kind, int page, int num) throws Exception {
        // todo ipolist kind 구분하는 구문 추가
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("limit", num);
        map.put("offset", page * num - num);

        // ipo list 조회 로직
        List<IpoSummaryDto> ipos = ipoMapper.selectIpos(map);

        // recent_comment 조회 로직
        Iterator<IpoSummaryDto> iterator = ipos.iterator();
        while(iterator.hasNext()) {
            IpoSummaryDto ipo = iterator.next();
            String commentIndex = ipo.getRecentComment();

            if(commentIndex != null) {
                IpoCommentDto ipoComment = selectIpoCommentIndex(Long.parseLong(commentIndex));
                if(ipoComment != null) {
                    if (ipoComment.getComment() != null) {
                        ipo.setRecentComment(ipoComment.getComment());
                    } else {
                        JsonCommentConverter jcc = new JsonCommentConverter();  // json인 경우 해석해서 대입
                        jcc.setCommentType(ipoComment.getLogType());
                        jcc.setCommentJson(ipoComment.getChangeLogJson());
                        ipo.setRecentComment(jcc.toString());
                    }
                    // 아무것도 없는 예외처리도 해야할까요
                }
            }
        }

        return ipos;
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
        return ipoMapper.selectIpoComment(ipoIndex);
    }

    @Override
    public IpoCommentDto selectIpoCommentIndex(long commentIndex) throws Exception {
        return ipoMapper.selectIpoCommentIndex(commentIndex);
    }

    @Override
    public List<IpoCommentDto> selectIpoCommentList(int page, int num) throws Exception {
        Map<String, Integer> map = new HashMap<String, Integer>();
        // map.put 전에 각 데이터가 0이 아닌지 확인하는 Verify 로직 필요.
        map.put("limit", num);
        map.put("offset", page * num - num);
        return ipoMapper.selectIpoCommentList(map);
    }

    @Override
    public List<IpoUnderwriterDto> selectIpoUnderwriter(long ipoIndex) throws Exception {
        return ipoMapper.selectIpoUnderwriter(ipoIndex);
    }
}
