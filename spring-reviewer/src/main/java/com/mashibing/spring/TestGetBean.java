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
        System.out.println(ToStringBuilder.reflectionToString(person, ToStringStyle.JSON_STYLE));
    }
}
