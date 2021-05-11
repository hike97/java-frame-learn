package com.mashibing.spring.circulorreference;

/**
 * @author hike97 2month
 * @create 2021-04-28 13:10
 * @desc
 **/
public class A {

    private String name;

	public A () {
		super();
		System.out.println ("A init");
	}

	public String getName () {
		System.out.println ("B的name: ");
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}


}
