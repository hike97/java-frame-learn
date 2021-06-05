package service.combine.impl;

import entity.bo.HeadLine;
import entity.bo.ShopCategory;
import entity.dto.MainPageInfoDTO;
import entity.dto.Result;
import service.combine.HeadLineShopCategoryCombineService;
import service.solo.HeadLineService;
import service.solo.ShopCategoryService;

import java.util.List;

/**
 * @author hike97
 * @create 2021-06-05 14:46
 * @desc
 **/
public class HeadLineShopCategoryCombineServiceImpl implements HeadLineShopCategoryCombineService {
    private HeadLineService headLineService;
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
