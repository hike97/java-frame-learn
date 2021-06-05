package service.solo.impl;

import entity.bo.ShopCategory;
import entity.dto.Result;
import service.solo.ShopCategoryService;

import java.util.List;

/**
 * @author hike97
 * @create 2021-06-05 14:41
 * @desc
 **/
public class ShopCategoryServiceImpl implements ShopCategoryService {
    @Override
    public Result<Boolean> addShopCategory (ShopCategory shopCategory) {
        return null;
    }

    @Override
    public Result<Boolean> removeShopCategory (int shopCategoryId) {
        return null;
    }

    @Override
    public Result<Boolean> modifyShopCategory (ShopCategory shopCategory) {
        return null;
    }

    @Override
    public Result<ShopCategory> queryShopCategoryById (int shopCategoryId) {
        return null;
    }

    @Override
    public Result<List<ShopCategory>> queryShopCategory (ShopCategory shopCategoryCondition, int pageIndex, int pageSize) {
        return null;
    }
}
