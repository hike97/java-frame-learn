package com.simple.controller.superadmin;


import com.simple.entity.bo.HeadLine;
import com.simple.entity.dto.Result;
import com.simple.service.solo.HeadLineService;
import org.simpleframework.core.annotation.Controller;
import org.simpleframework.inject.annotation.Autowired;

import java.util.List;

/**
 * @description:
 * @author: air
 * @create: 2020-03-14 11:25
 */
@Controller
public class HeadLineOperationController {

    @Autowired
    private HeadLineService iHeadLineService;

    public Result<Boolean> addHeadLine (HeadLine headLine) {
        System.out.println ("HeadLineOperationController.addHeadLine");

        return iHeadLineService.addHeadLine (headLine);
    }

    public Result<Boolean> removeHeadLine (HeadLine headLine) {
        return iHeadLineService.removeHeadLine (headLine);
    }

    public Result<Boolean> modifyHeadLine (HeadLine headLine) {
        return iHeadLineService.modifyHeadLine (headLine);
    }

    public Result<HeadLine> getHeadLineById (int headLineId) {
        System.out.println ("headLineId = " + headLineId);
        return iHeadLineService.getHeadLineById (headLineId);
    }

    public Result<List<HeadLine>> getHeadLineList (HeadLine headLineCondition, int pageIndex, int pageSize) {
        return iHeadLineService.getHeadLineList (headLineCondition, pageIndex, pageSize);
    }

}
