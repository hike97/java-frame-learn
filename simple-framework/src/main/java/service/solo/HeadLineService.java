package service.solo;

import entity.bo.HeadLine;
import entity.dto.Result;

import java.util.List;

/**
 * @author hike97
 * @create 2021-06-05 14:36
 * @desc
 **/
public interface HeadLineService {
    Result<Boolean> addHeadLine (HeadLine headLine);

    Result<Boolean> removeHeadLine (int headLineId);

    Result<Boolean> modifyHeadLine (HeadLine headLine);

    Result<HeadLine> queryHeadLineById (int headLineId);

    Result<List<HeadLine>> queryHeadLine (HeadLine headLineCondition, int pageIndex, int pageSize);
}
