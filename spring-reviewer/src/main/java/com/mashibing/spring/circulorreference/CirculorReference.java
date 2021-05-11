package com.mashibing.spring.circulorreference;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author hike97 2month
 * @create 2021-04-28 13:12
 * @desc 循环引用
 **/
public class CirculorReference {

	public static void main (String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

//		A a = ctx.getBean ("A", A.class);
//		System.out.println (ToStringBuilder.reflectionToString (a));
//		System.out.println (ToStringBuilder.reflectionToString (a.getB ()));
//		System.out.println (ToStringBuilder.reflectionToString (a.getB ().getC ()));
//		System.out.println ("再获取一次的情况");
		/**
		 * A 为单例 B C 为prototype
		 * A 的引用对象也变成了单例对象
		 */
//		A a1 = ctx.getBean ("A", A.class);
//		System.out.println (ToStringBuilder.reflectionToString (a1));
//		System.out.println (ToStringBuilder.reflectionToString (a1.getB ()));
//		System.out.println (ToStringBuilder.reflectionToString (a1.getB ().getC ()));
//
//		System.out.println (a == a1);
//		System.out.println (a.getB () == a1.getB ());

		//测试B单独获取 是否是同一个对象

		C c = ctx.getBean ("C", C.class);
		C c1 = ctx.getBean ("C", C.class);
		C c2 = ctx.getBean ("C", C.class);
		System.out.println (c);
		System.out.println (c1);
		System.out.println (c2);

		/**
		 * 如果A也是prototype的 那么会报循环引用错误 Caused by: org.springframework.beans.factory.BeanCurrentlyInCreationException: Error creating bean with name 'C': Requested bean is currently in creation: Is there an unresolvable circular reference?
		 */

	}
}
