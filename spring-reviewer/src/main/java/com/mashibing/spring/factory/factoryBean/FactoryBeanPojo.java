package com.mashibing.spring.factory.factoryBean;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author hike97 2month
 * @create 2021-05-06 15:00
 * @desc factoryBean 实现类
 **/
public class FactoryBeanPojo implements FactoryBean<School> {

	@Override
	public School getObject () throws Exception {
		return new School ();
	}

	@Override
	public Class<?> getObjectType () {
		return School.class;
	}

	@Override
	public boolean isSingleton () {
		return true;
	}
}
