package com.mashibing.spring.ioc;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author hike97 2month
 * @create 2021-04-29 20:17
 * @desc 利用反射模拟SpringIOC过程
 **/
public class IOCDemoTest {
	public static void main (String[] args) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		//1.先new 出基本对象
		UserController userController = new UserController ();
		UserService userService = new UserService ();
		System.out.println ("注入前： "+userController.getUserService ());
		//获取controller 的对象类
		Class<? extends UserController> clazz = userController.getClass ();
		//获取对象的属性
		Field userServiceDeclaredField = clazz.getDeclaredField ("userService");
		userServiceDeclaredField.setAccessible (true);
		String name = userServiceDeclaredField.getName ();
		name = name.substring (0,1).toUpperCase () + name.substring (1);
		//用获取到controller的set 方法
		Method method = clazz.getMethod ("set" + name, UserService.class);
		method.invoke (userController,userService);
		System.out.println ("注入后： "+userController.getUserService ());
	}

	/**
	 * 根据注解的方式注入 自定义注解AutoWried
	 */
	@Test
	public void test () {
		//1.先new 出基本对象
		UserController userController = new UserController ();
		UserService userService = new UserService ();
		System.out.println ("注入前： "+userController.getUserService ());
		//获取controller 的对象类
		Class<? extends UserController> clazz = userController.getClass ();
		Arrays.stream (clazz.getDeclaredFields ()).forEach (field ->{
			String name = field.getName ();
			Autowried annotation = field.getAnnotation (Autowried.class);
			if (annotation!=null) {
				field.setAccessible (true);
				Class<?> type = field.getType ();
				try {
					/**
					 * 这个对象是直接new出来的
					 * 在spring中 这个bean是定义在注解或xml中的，如何找到？
					 * */
					Object o = type.getConstructor ().newInstance ();
					field.set(userController,o);
				} catch (InstantiationException e) {
					e.printStackTrace ();
				} catch (IllegalAccessException e) {
					e.printStackTrace ();
				} catch (InvocationTargetException e) {
					e.printStackTrace ();
				} catch (NoSuchMethodException e) {
					e.printStackTrace ();
				}
			}
		});
		System.out.println ("注入后： "+userController.getUserService ());
	}
}
