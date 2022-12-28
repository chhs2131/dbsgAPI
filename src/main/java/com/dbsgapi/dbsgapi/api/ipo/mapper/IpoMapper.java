package com.dbsgapi.dbsgapi.api.ipo.mapper;

import com.dbsgapi.dbsgapi.api.ipo.dto.IpoCommentDto;
import com.dbsgapi.dbsgapi.api.ipo.dto.IpoDto;
import com.dbsgapi.dbsgapi.api.ipo.dto.IpoSummaryDto;
import com.dbsgapi.dbsgapi.api.ipo.dto.IpoUnderwriterDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface IpoMapper {
    List<IpoSummaryDto> selectIpos(Map<String, Object> map);

    Optional<IpoDto> selectIpo(long ipoIndex);

    List<IpoSummaryDto> selectIpoScheduleList(Map<String, Object> map);

    List<IpoUnderwriterDto> selectIpoUnderwriter(long ipoIndex);
}
