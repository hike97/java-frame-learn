package com.simple.service.solo;

import com.simple.entity.bo.HeadLine;
import com.simple.entity.dto.Result;

import java.util.List;

/**
 * @author hike97
 * @create 2021-06-05 14:36
 * @desc
 **/
public interface HeadLineService {
    Result<Boolean> addHeadLine (HeadLine headLine);

    Result<Boolean> removeHeadLine (HeadLine headLine);

    Result<Boolean> modifyHeadLine (HeadLine headLine);

    Result<HeadLine> getHeadLineById (int headLineId);

    Result<List<HeadLine>> getHeadLineList (HeadLine headLineCondition, int pageIndex, int pageSize);
}
