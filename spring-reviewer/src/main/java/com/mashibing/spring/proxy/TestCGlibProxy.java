package com.mashibing.spring.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author hike97 2month
 * @create 2021-04-28 23:05
 * @desc CGlib 动态代理 底层ASM 用到的设计模式 visitor 观察者模式
 **/
public class TestCGlibProxy {
	public static void main (String[] args) {
		Girl girl = new Girl ();
		Girl girlProxy = (Girl) new CGlibFactory (girl).createProxy ();
		girlProxy.eat ();
		girlProxy.bath ();
	}
}
class CGlibFactory implements MethodInterceptor {

	private  Object target;

	public CGlibFactory () {
	}

	public CGlibFactory (Object target) {
		this.target = target;
	}

	//增强器
	public Object createProxy () {
		//增强器
		Enhancer enhancer = new Enhancer ();
		enhancer.setSuperclass (Girl.class);
		//将封装好的对象 set到intercept方法中
		enhancer.setCallback (this);
		return enhancer.create ();
	}

	@Override
	public Object intercept (Object o, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		System.out.println ("method 执行前");
		Object invoke = method.invoke (target, args);
		System.out.println ("method 执行后");
		return invoke;
	}
}
