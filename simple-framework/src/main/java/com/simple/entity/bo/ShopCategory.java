package com.simple.entity.bo;

import lombok.Data;

import java.util.Date;

/**
 * @author hike97
 * @create 2021-06-05 12:47
 * @desc
 **/
@Data
public class ShopCategory {
    private Long shopCategoryId;
    private String shopCategoryName;
    private String shopCategoryDesc;
    private String shopCategoryImg;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;
    private ShopCategory parent;
}
