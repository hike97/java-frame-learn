package com.mashibing.spring.factory.impl;

import com.mashibing.spring.factory.Car;

/**
 * @author hike97 2month
 * @create 2021-04-10 20:59
 * @desc 奥迪车
 **/
public class Audi implements Car {
	@Override
	public String getName () {
		return "Audi A7";
	}

	@Override
	public String getPrice () {
		return "700000";
	}
}
