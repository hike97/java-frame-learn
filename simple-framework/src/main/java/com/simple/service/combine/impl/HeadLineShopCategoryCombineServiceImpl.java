package com.simple.service.combine.impl;

import com.simple.entity.bo.HeadLine;
import com.simple.entity.dto.MainPageInfoDTO;
import com.simple.entity.dto.Result;
import org.simpleframework.core.annotation.Service;
import com.simple.service.combine.HeadLineShopCategoryCombineService;
import com.simple.service.solo.HeadLineService;
import com.simple.service.solo.ShopCategoryService;
import org.simpleframework.inject.annotation.Autowired;

/**
 * @author hike97
 * @create 2021-06-05 14:46
 * @desc
 **/
@Service
public class HeadLineShopCategoryCombineServiceImpl implements HeadLineShopCategoryCombineService {

    @Autowired
    private HeadLineService headLineService;

    @Autowired (value = "ShopCategoryServiceImpl")
    private ShopCategoryService shopCategoryService;

    @Override
    public Result<MainPageInfoDTO> getMainPageInfo () {
        //1.获取头条列表
        HeadLine headLineCondition = new HeadLine ();
        headLineCondition.setEnableStatus (1);
        //        Result<List<HeadLine>> HeadLineResult = headLineService.queryHeadLine (headLineCondition, pagelng
        //        ShopCategory shopCategoryCondition = new ShopCategory0;
        //        //2.获取店铺类别列表
        //        Result<List<ShopCategory>> shopCategoryResult = shopCategoryService.queryShopCategory (shopCate)
        //        //3.合并两者并返回
        //        Result < MainPageInfoDTO > result = mergeMainPageInfoResult (HeadLineResult, shopCategoryResult);
        //        return result;
        return null;
    }

    //    @private Result<MainPageInfoDTO> mergeMainPageInfoResult (Result < List < HeadLine >> headLineResult, Re
    //        return null
}
