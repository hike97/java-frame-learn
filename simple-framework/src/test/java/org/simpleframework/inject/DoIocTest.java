package org.simpleframework.inject;

import com.simple.controller.frontend.MainPageController;
import com.simple.controller.superadmin.ShopCategoryOperationController;
import com.simple.service.solo.impl.ShopCategoryServiceImpl;
import com.simple.service.solo.impl.ShopCategoryServiceImpl2;
import org.junit.jupiter.api.*;
import org.simpleframework.core.annotation.BeanContainer;

/**
 * @description:
 * @author: air
 * @create: 2020-03-16 15:51
 */
@TestMethodOrder (MethodOrderer.OrderAnnotation.class)
public class DoIocTest {

    @Test
    @DisplayName ("依赖注入doIOC")
    public void doIoc () {
        BeanContainer beanContainer = BeanContainer.getInstance ();

        beanContainer.loadBeans ("com.simple");
        Assertions.assertEquals (true, beanContainer.isLoading ());

        MainPageController mainPageController = (MainPageController) beanContainer.getBean (MainPageController.class);
        Assertions.assertEquals (true, mainPageController instanceof MainPageController);
        System.out.println ("service 注入之前");
        Assertions.assertEquals (null, mainPageController.getIHeadLineShopCategoryCombineService ());
        new DependencyInject ().doIOC ();
        System.out.println ("service 注入之后");

        //判断shopCategoryOperationController的实例 不是 ShopCategoryServiceImpl2 而是指定的 ShopCategoryServiceImpl
        ShopCategoryOperationController shopCategoryOperationController = (ShopCategoryOperationController) beanContainer.getBean (ShopCategoryOperationController.class);
        Assertions.assertNotEquals (true, shopCategoryOperationController.getIShopCategoryService () instanceof ShopCategoryServiceImpl2);
        Assertions.assertNotEquals (false, shopCategoryOperationController.getIShopCategoryService () instanceof ShopCategoryServiceImpl);

    }

}
