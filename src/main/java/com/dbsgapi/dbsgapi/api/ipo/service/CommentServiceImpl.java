package com.dbsgapi.dbsgapi.api.ipo.service;

import com.dbsgapi.dbsgapi.api.ipo.domain.DatePeriod;
import com.dbsgapi.dbsgapi.api.ipo.dto.IpoCommentDto;
import com.dbsgapi.dbsgapi.api.ipo.mapper.IpoMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final IpoMapper ipoMapper;

    @Override
    public List<IpoCommentDto> selectIpoComment(long ipoIndex) throws IllegalStateException {
        List<IpoCommentDto> ipoCommentList = ipoMapper.selectIpoComment(ipoIndex);
        // 결과 중 내용이 없는 코멘트가 있는 경우 제거한다.
        ipoCommentList.removeIf(ipoComment -> "".equals(ipoComment.getTitle()));

        return ifPresent(ipoCommentList);
    }

    @Override
    public IpoCommentDto selectIpoCommentIndex(long commentIndex) throws IllegalStateException {
        return ipoMapper.selectIpoCommentIndex(commentIndex).orElseThrow(IllegalStateException::new);
    }

    @Override
    public List<IpoCommentDto> selectIpoCommentList(DatePeriod datePeriod) throws IllegalStateException {
        // 쿼리문 요청, 조회
        Map<String, Object> map = datePeriod.toMap();
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

        return ifPresent(ipoCommentList);
    }

    private boolean commentIsNew(IpoCommentDto ipoComment, Map<Long, IpoCommentDto> newRegisterCommentList) {
        IpoCommentDto newRegisterComment = newRegisterCommentList.get(ipoComment.getIpoIndex());
        if (newRegisterComment == null) {
            return false;
        }

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
