package com.mashibing.spring.factory.factoryBean;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author hike97 2month
 * @create 2021-05-06 15:05
 * @desc
 **/
public class TestFactoryBean {
	public static void main (String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		Object school=  ctx.getBean("factoryBeanPojo");
		FactoryBeanPojo factoryBeanPojo= (FactoryBeanPojo) ctx.getBean("&factoryBeanPojo");
		System.out.println(school.getClass().getName());
		System.out.println(factoryBeanPojo.getClass().getName());
	}
}
