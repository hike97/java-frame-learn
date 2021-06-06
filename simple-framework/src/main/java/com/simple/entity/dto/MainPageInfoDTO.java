package com.simple.entity.dto;

import com.simple.entity.bo.HeadLine;
import com.simple.entity.bo.ShopCategory;
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
