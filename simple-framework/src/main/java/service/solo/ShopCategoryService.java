package service.solo;

import entity.bo.HeadLine;
import entity.bo.ShopCategory;
import entity.dto.Result;

import java.util.List;

/**
 * @author hike97
 * @create 2021-06-05 14:37
 * @desc
 **/
public interface ShopCategoryService {

    Result<Boolean> addShopCategory (ShopCategory shopCategory);

    Result<Boolean> removeShopCategory (int shopCategoryId);

    Result<Boolean> modifyShopCategory (ShopCategory shopCategory);

    Result<ShopCategory> queryShopCategoryById (int shopCategoryId);

    Result<List<ShopCategory>> queryShopCategory (ShopCategory shopCategoryCondition, int pageIndex, int pageSize);
}
