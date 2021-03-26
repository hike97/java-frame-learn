package com.mashibing.spring;

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
}
