package entity.dto;

import entity.bo.HeadLine;
import entity.bo.ShopCategory;
import lombok.Data;

import java.util.List;

/**
 * @author hike97
 * @create 2021-06-05 14:44
 * @desc
 **/
@Data
public class MainPageInfoDTO {
    private List<HeadLine> headLineList;
    private List<ShopCategory> shopCategoryList;
}
