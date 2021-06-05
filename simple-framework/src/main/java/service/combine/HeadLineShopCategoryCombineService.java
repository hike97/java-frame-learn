package service.combine;

import entity.dto.MainPageInfoDTO;
import entity.dto.Result;

/**
 * @author hike97
 * @create 2021-06-05 14:42
 * @desc
 **/
public interface HeadLineShopCategoryCombineService {
    Result<MainPageInfoDTO> getMainPageInfo ();
}
