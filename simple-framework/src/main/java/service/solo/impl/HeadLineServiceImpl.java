package service.solo.impl;

import entity.bo.HeadLine;
import entity.dto.Result;
import service.solo.HeadLineService;

import java.util.List;

/**
 * @author hike97
 * @create 2021-06-05 14:41
 * @desc
 **/
public class HeadLineServiceImpl implements HeadLineService {
    @Override
    public Result<Boolean> addHeadLine (HeadLine headLine) {
        return null;
    }

    @Override
    public Result<Boolean> removeHeadLine (int headLineId) {
        return null;
    }

    @Override
    public Result<Boolean> modifyHeadLine (HeadLine headLine) {
        return null;
    }

    @Override
    public Result<HeadLine> queryHeadLineById (int headLineId) {
        return null;
    }

    @Override
    public Result<List<HeadLine>> queryHeadLine (HeadLine headLineCondition, int pageIndex, int pageSize) {
        return null;
    }
}
