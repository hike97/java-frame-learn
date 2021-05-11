package com.mashibing.spring.factory;

import com.mashibing.spring.factory.impl.Audi;
import com.mashibing.spring.factory.impl.Bmw;

/**
 * @author hike97 2month
 * @create 2021-04-10 21:07
 * @desc car 工厂
 **/
public class Carfactory {

	public Car getCar(String name) {
		if ("audi".equals (name)){
			return new Audi ();
		}else {
			return new Bmw ();
		}
	}
}
