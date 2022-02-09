package com.dbsgapi.dbsgapi.ipo.service;

import com.dbsgapi.dbsgapi.ipo.dto.IpoCommentDto;
import com.dbsgapi.dbsgapi.ipo.dto.IpoDto;
import com.dbsgapi.dbsgapi.ipo.dto.IpoSummaryDto;
import com.dbsgapi.dbsgapi.ipo.dto.IpoUnderwriterDto;
import com.dbsgapi.dbsgapi.ipo.mapper.IpoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<IpoCommentDto> selectIpoComment(long ipoIndex) throws Exception {
        return ipoMapper.selectIpoComment(ipoIndex);
    }

    @Override
    public IpoUnderwriterDto selectIpoUnderwriter(long ipoIndex) throws Exception {
        return ipoMapper.selectIpoUnderwriter(ipoIndex);
    }
}
