package com.mashibing.spring.circulorreference;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author hike97 2month
 * @create 2021-04-28 13:37
 * @desc spring 确认bean的初始化顺序
 **/
public class DependsOnTest {


	public static void main (String[] args) {

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

		A a = ctx.getBean ("A", A.class);
		B b = ctx.getBean ("B", B.class);
		C c = ctx.getBean ("C", C.class);

		System.out.println (a);
		System.out.println (b);
		System.out.println (c);
	}
}
