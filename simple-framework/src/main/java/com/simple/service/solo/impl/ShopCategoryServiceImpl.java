package com.simple.service.solo.impl;

import com.simple.entity.bo.ShopCategory;
import com.simple.entity.dto.Result;
import org.simpleframework.core.annotation.Service;
import com.simple.service.solo.ShopCategoryService;

import java.util.List;

/**
 * @author hike97
 * @create 2021-06-05 14:41
 * @desc
 **/
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
    @Override
    public Result<Boolean> addShopCategory (ShopCategory shopCategory) {
        return null;
    }


    @Override
    public Result<Boolean> modifyShopCategory (ShopCategory shopCategory) {
        return null;
    }


    @Override
    public Result<Boolean> removeShopCategory (ShopCategory shopCategory) {
        return null;
    }

    @Override
    public Result<ShopCategory> getShopCategoryById (int shopCategoryId) {
        return null;
    }

    @Override
    public Result<List<ShopCategory>> getShopCategoryList (ShopCategory shopCategoryCondition, int pageIndex, int pageSize) {
        return null;
    }
}
