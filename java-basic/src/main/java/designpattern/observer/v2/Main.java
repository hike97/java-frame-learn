package designpattern.observer.v2;

import java.util.ArrayList;
import java.util.List;

/**
 * 主要组成
 * 观察者  父母 狗
 * 事件   （可抽象出来）
 * 事件原对象 childen
 */

class Child {
    private boolean cry = false;
    private List<Observer> observers = new ArrayList<>();

    {
        observers.add(new Dad());
        observers.add(new Mum());
        observers.add(new Dog());
        //也可以用java8 函数式编程
        observers.add ((event)-> System.out.println("ppp"));
    }


    public boolean isCry() {
        return cry;
    }

    public void wakeUp() {
        cry = true;

        WakeUpEvent event = new WakeUpEvent(System.currentTimeMillis(), "bed");

        for(Observer o : observers) {
            o.actionOnWakeUp(event);
        }
    }
}

//事件类 fire Event
class WakeUpEvent extends Event<Child>{

    long timestamp;
    String loc;
    Child source;

    public WakeUpEvent(long timestamp, String loc) {
        this.timestamp = timestamp;
        this.loc = loc;
    }

    @Override
    Child getSource () {
        return source;
    }
}

interface Observer {
    void actionOnWakeUp(WakeUpEvent event);
}

class Dad implements Observer {
    public void feed() {
        System.out.println("dad feeding...");
    }

    @Override
    public void actionOnWakeUp(WakeUpEvent event) {
        feed();
    }
}

class Mum implements Observer {
    public void hug() {
        System.out.println("mum hugging...");
    }

    @Override
    public void actionOnWakeUp(WakeUpEvent event) {
        hug();
    }
}

class Dog implements Observer {
    public void wang() {
        System.out.println("dog wang...");
    }

    @Override
    public void actionOnWakeUp(WakeUpEvent event) {
        wang();
    }
}
//加入事件抽象对象
abstract class Event<T> {
    abstract T getSource();
}

public class Main {
    public static void main(String[] args) {
        Child c = new Child();
        //do sth
        c.wakeUp();
    }
}
