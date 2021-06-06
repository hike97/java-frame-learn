package com.simple.controller.frontend;


import com.simple.entity.dto.MainPageInfoDTO;
import com.simple.entity.dto.Result;
import lombok.Getter;
import org.simpleframework.core.annotation.Controller;
import com.simple.service.combine.HeadLineShopCategoryCombineService;
import org.simpleframework.inject.annotation.Autowired;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: air
 * @create: 2020-03-14 11:22
 */
@Controller
@Getter
public class MainPageController {
    @Autowired
    private HeadLineShopCategoryCombineService iHeadLineShopCategoryCombineService;

    public Result<MainPageInfoDTO> getMainPageInfo (HttpServletRequest request, HttpServletResponse response) {
        return iHeadLineShopCategoryCombineService.getMainPageInfo ();
    }

}
