package com.simple.service.combine;

import com.simple.entity.dto.MainPageInfoDTO;
import com.simple.entity.dto.Result;

/**
 * @author hike97
 * @create 2021-06-05 14:42
 * @desc
 **/
public interface HeadLineShopCategoryCombineService {
    Result<MainPageInfoDTO> getMainPageInfo ();
}
