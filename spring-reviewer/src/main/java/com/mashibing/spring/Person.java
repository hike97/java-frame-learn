package com.mashibing.spring;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @ClassName Person
 * @Description TODO
 * @Author hike97
 * @Date 2021/3/26 17:25
 * @Version 1.0
 **/
public class Person {
    private String name;
    private String age;
    private Food food;
    private Properties gift;
    private List list;
    private Map map;
    private Set set;
    private String[] strs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Properties getGift () {
        return gift;
    }

    public void setGift (Properties gift) {
        this.gift = gift;
    }

    public List getList () {
        return list;
    }

    public void setList (List list) {
        this.list = list;
    }

    public Map getMap () {
        return map;
    }

    public void setMap (Map map) {
        this.map = map;
    }

    public Set getSet () {
        return set;
    }

    public void setSet (Set set) {
        this.set = set;
    }

    public String[] getStrs () {
        return strs;
    }

    public void setStrs (String[] strs) {
        this.strs = strs;
    }
}
