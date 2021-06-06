package com.simple.controller.superadmin;


import com.simple.entity.bo.ShopCategory;
import com.simple.entity.dto.Result;
import com.simple.service.solo.ShopCategoryService;
import lombok.Getter;
import org.simpleframework.core.annotation.Controller;
import org.simpleframework.inject.annotation.Autowired;

import java.util.List;

/**
 * @description:
 * @author: air
 * @create: 2020-03-14 11:30
 */
@Controller
@Getter
public class ShopCategoryOperationController {

    @Autowired (value = "ShopCategoryServiceImpl")
    private ShopCategoryService iShopCategoryService;


    public Result<Boolean> addShopCategory (ShopCategory shopCategory) {
        return iShopCategoryService.addShopCategory (shopCategory);
    }

    public Result<Boolean> removeShopCategory (ShopCategory shopCategory) {
        return iShopCategoryService.removeShopCategory (shopCategory);
    }

    public Result<Boolean> modifyShopCategory (ShopCategory shopCategory) {
        return iShopCategoryService.modifyShopCategory (shopCategory);
    }

    public Result<ShopCategory> getShopCategoryById (int shopCategoryId) {
        return iShopCategoryService.getShopCategoryById (shopCategoryId);
    }

    public Result<List<ShopCategory>> getShopCategoryList (ShopCategory shopCategoryCondition, int pageIndex, int pageSize) {
        return iShopCategoryService.getShopCategoryList (shopCategoryCondition, pageIndex, pageSize);
    }


}
