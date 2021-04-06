package com.mashibing.spring;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName TestGetBean
 * @Description TODO
 * @Author hike97
 * @Date 2021/3/26 17:34
 * @Version 1.0
 **/
public class TestGetBean {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        Person person = (Person) ctx.getBean("person");
//        Food food = ctx.getBean ("food", Food.class);
//        food.setName ("banana");
//        person.setName ("hike");
//        person.setAge ("28");
//        person.setFood (food);
//        System.out.println(ToStringBuilder.reflectionToString(person, ToStringStyle.JSON_STYLE));
        //PluggableSchemaResolver 自动校验 springxml 中的 dtd xsd
        Person person2 = ctx.getBean ("person2", Person.class);
        System.out.println (ToStringBuilder.reflectionToString (person2, ToStringStyle.MULTI_LINE_STYLE));
        /*bean 的作用域*/
        /**
         * singleton 单例作用域
         * prototype 原型作用域
         * websocket 基于连接
         * request   基于请求
         * session   基于会话
         * application 基于上下文
         * 除了 prototype  其它全是单例的 只是生命周期不同
         */
    }
}
