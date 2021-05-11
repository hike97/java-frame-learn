package com.mashibing.spring.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author hike97 2month
 * @create 2021-04-28 22:50
 * @desc JDK动态代理 只能代理 有实现接口的对象
 **/
public class TestJDKProxy {
	public static void main (String[] args) {
		Girl girl = new Girl ();
		Human humanProxy = (Human) Proxy.newProxyInstance (Girl.class.getClassLoader (), Girl.class.getInterfaces (), new InvocationHandler () {
			@Override
			public Object invoke (Object proxy, Method method, Object[] args) throws Throwable {
				System.out.println (method.getName () + "->> 方法被执行");
				if (method.getName ().equals ("bath")) {
					System.out.println ("偷窥洗澡");
					Object invoke = method.invoke (girl, args);
					System.out.println ("6666");
					return invoke;
				} else {
					System.out.println ("吃饭前");
					Object invoke = method.invoke (girl, args);
					System.out.println ("吃饭后");
					return invoke;
				}
			}
		});
		humanProxy.eat ();
		System.out.println ("lalalala");
		humanProxy.bath ();
	}
}
