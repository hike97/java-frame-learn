package com.mashibing.spring.factory.impl;

import com.mashibing.spring.factory.Car;

/**
 * @author hike97 2month
 * @create 2021-04-10 21:00
 * @desc 宝马
 **/
public class Bmw implements Car {
	@Override
	public String getName () {
		return "别摸我";
	}

	@Override
	public String getPrice () {
		return "700000";
	}
}
