package com.mashibing.spring.proxy;

/**
 * @author hike97 2month
 * @create 2021-04-28 22:48
 * @desc
 **/
public class Girl implements Human{
	@Override
	public void eat () {
		System.out.println ("Em ememem mmmm");
	}

	@Override
	public void bath () {
		System.out.println ("Girl is bathing");
	}
}
