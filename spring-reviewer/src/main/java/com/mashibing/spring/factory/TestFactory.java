package com.mashibing.spring.factory;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author hike97 2month
 * @create 2021-04-10 21:10
 * @desc
 **/
public class TestFactory {
	public static void main (String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		Car car = ctx.getBean ("car", Car.class);
		System.out.println (car.getName ());
		System.out.println (car.getPrice ());
	}
}
