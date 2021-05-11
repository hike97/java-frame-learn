package com.mashibing.spring;

import org.junit.Test;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

/**
 * @author hike97 2month
 * @create 2021-05-06 15:52
 * @desc 其它源码测试
 **/
public class OtherTest {

	@Test
	public void test () {
		//environment接口测试
		Properties properties = System.getProperties ();
		Enumeration<?> enumeration =properties.propertyNames ();
		while (enumeration.hasMoreElements ()) {
			String name = enumeration.nextElement ().toString ();
			System.out.println (String.format ("变量名：%s|变量值：%s",name,properties.get (name)));
		}

//		for (Map.Entry<String, String> entry : System.getenv ().entrySet ()) {
//			System.out.println (entry.getKey () + ":" + entry.getValue ());
//		}
	}
}
