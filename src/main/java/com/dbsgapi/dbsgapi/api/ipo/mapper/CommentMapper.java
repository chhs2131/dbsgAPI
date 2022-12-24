package com.dbsgapi.dbsgapi.api.ipo.mapper;

import com.dbsgapi.dbsgapi.api.ipo.dto.IpoCommentDto;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {
    List<IpoCommentDto> selectIpoComment(long ipoIndex);

    Optional<IpoCommentDto> selectIpoCommentIndex(long commentIndex);

    List<IpoCommentDto> selectIpoCommentList(Map<String, Object> map);
}
