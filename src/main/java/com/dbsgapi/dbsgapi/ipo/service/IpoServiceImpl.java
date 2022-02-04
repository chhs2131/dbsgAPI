package com.dbsgapi.dbsgapi.ipo.service;

import com.dbsgapi.dbsgapi.ipo.dto.IpoDto;
import com.dbsgapi.dbsgapi.ipo.dto.IpoSummaryDto;
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
}
