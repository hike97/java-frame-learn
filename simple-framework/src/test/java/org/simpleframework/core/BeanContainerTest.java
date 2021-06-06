package org.simpleframework.core;


import com.simple.controller.frontend.MainPageController;
import com.simple.entity.bo.HeadLine;
import com.simple.service.solo.HeadLineService;
import com.simple.service.solo.impl.HeadLineServiceImpl;
import org.junit.jupiter.api.*;
import org.simpleframework.core.annotation.BeanContainer;
import org.simpleframework.core.annotation.Controller;
import org.simpleframework.core.annotation.Service;

/**
 * @description:
 * @author: air
 * @create: 2020-03-16 11:55
 */
//单元方法按照顺序执行
@TestMethodOrder (MethodOrderer.OrderAnnotation.class)
public class BeanContainerTest {
    private static BeanContainer beanContainer;

    @BeforeAll
    static void init () {
        System.out.println ("init");
        beanContainer = BeanContainer.getInstance ();
    }

    @Test
    @DisplayName (value = "加载目标类及实例到BeanContainer:loadBeansTest")
    @Order (1)
    public void loadBeans () {
        Assertions.assertEquals (false, beanContainer.isLoading ());
        beanContainer.loadBeans ("com.simple");
        Assertions.assertEquals (6, beanContainer.size ());
        Assertions.assertEquals (true, beanContainer.isLoading ());
    }

    @Order (2)
    @DisplayName ("根据类获取其实例：getBeanTest")
    @Test
    public void getBean () {
        MainPageController controller = (MainPageController) beanContainer.getBean (MainPageController.class);
        Assertions.assertEquals (true, controller instanceof MainPageController);
        HeadLine headLine = (HeadLine) beanContainer.getBean (HeadLine.class);
        Assertions.assertEquals (null, headLine);
    }


    @Test
    @Order (3)
    @DisplayName (value = "根据注解获取对应的实例 getClassesByAnnotationTest")
    public void getClassesByAnnotation () {
        //判断容器是否被加载
        Assertions.assertEquals (true, beanContainer.isLoading ());
        System.out.println (beanContainer.getClassesByAnnotation (Controller.class).size ());
        Assertions.assertEquals (3, beanContainer.getClassesByAnnotation (Service.class).size ());
    }

    @Test
    @Order (4)
    @DisplayName (value = "根据父类或接口获取子类实现类 getClassesBySuperTest")
    public void getClassesBySuperTest () {
        //判断容器是否被加载
        Assertions.assertEquals (true, beanContainer.isLoading ());
        Assertions.assertEquals (true, beanContainer.getClassesBySuper (HeadLineService.class).contains (HeadLineServiceImpl.class));
    }


}
