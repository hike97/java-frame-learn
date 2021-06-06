package com.simple.service.solo.impl;

import com.simple.entity.bo.HeadLine;
import com.simple.entity.dto.Result;
import org.simpleframework.core.annotation.Service;
import com.simple.service.solo.HeadLineService;

import java.util.List;

/**
 * @author hike97
 * @create 2021-06-05 14:41
 * @desc
 **/
@Service
public class HeadLineServiceImpl implements HeadLineService {
    @Override
    public Result<Boolean> addHeadLine (HeadLine headLine) {
        return null;
    }

    @Override
    public Result<Boolean> removeHeadLine (HeadLine headLine) {
        return null;
    }

    @Override
    public Result<Boolean> modifyHeadLine (HeadLine headLine) {
        return null;
    }

    @Override
    public Result<HeadLine> getHeadLineById (int headLineId) {
        return null;
    }

    @Override
    public Result<List<HeadLine>> getHeadLineList (HeadLine headLineCondition, int pageIndex, int pageSize) {
        return null;
    }
}
