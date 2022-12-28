package com.dbsgapi.dbsgapi.api.ipo.service;

import com.dbsgapi.dbsgapi.api.ipo.domain.DatePeriod;
import com.dbsgapi.dbsgapi.api.ipo.domain.IpoPaging;
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
    public List<IpoSummaryDto> selectIpos(IpoPaging ipoPaging) throws IllegalStateException {
        Map<String, Object> map = ipoPaging.toMap();
        return ifPresent(ipoMapper.selectIpos(map));
    }

    @Override
    public IpoDto selectIpo(long ipoIndex) throws IllegalStateException {
        return ipoMapper.selectIpo(ipoIndex).orElseThrow(IllegalStateException::new);
    }

    @Override
    public List<IpoSummaryDto> selectIpoScheduleList(DatePeriod datePeriod) throws IllegalStateException {
        Map<String, Object> map = datePeriod.toMap();
        return ifPresent(ipoMapper.selectIpoScheduleList(map));
    }


    @Override
    public List<IpoUnderwriterDto> selectIpoUnderwriter(long ipoIndex) throws IllegalStateException {
        return ifPresent(ipoMapper.selectIpoUnderwriter(ipoIndex));
    }

    private <T> List<T> ifPresent(List<T> targetList) {
        validateListNull(targetList);
        validateListEmpty(targetList);
        return targetList;
    }

    private <T> void validateListNull(List<T> targetList) {
        if (targetList == null) {
            throw new IllegalStateException();
        }
    }

    private <T> void validateListEmpty(List<T> targetList) {
        if (targetList.isEmpty()) {
            throw new IllegalStateException();
        }
    }
}
