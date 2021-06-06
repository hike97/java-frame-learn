package com.simple.service.solo;

import com.simple.entity.bo.ShopCategory;
import com.simple.entity.dto.Result;

import java.util.List;

/**
 * @author hike97
 * @create 2021-06-05 14:37
 * @desc
 **/
public interface ShopCategoryService {

    Result<Boolean> addShopCategory (ShopCategory shopCategory);

    Result<Boolean> removeShopCategory (ShopCategory shopCategory);

    Result<Boolean> modifyShopCategory (ShopCategory shopCategory);

    Result<ShopCategory> getShopCategoryById (int shopCategoryId);

    Result<List<ShopCategory>> getShopCategoryList (ShopCategory shopCategoryCondition, int pageIndex, int pageSize);
}
