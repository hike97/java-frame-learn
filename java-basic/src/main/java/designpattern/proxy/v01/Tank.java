package designpattern.proxy.v01;

import java.util.Random;

/**
 * 问题：我想记录坦克的移动时间
 * 方案一: 继承的方法 不利于解耦
 * 方案二: 静态工厂 缺点 不可以代理其它的代理 例如时间代理 代理 日志代理
 */
public class Tank implements Movable {

    /**
     * 模拟坦克移动了一段儿时间
     */
    @Override
    public void move() {
        System.out.println("Tank moving claclacla...");
        try {
            Thread.sleep(new Random().nextInt(10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new TimeProxy(new Tank()).move();
    }
}

interface Movable {
    void move();
}

class TimeProxy implements Movable {

    private Tank tank;

    public TimeProxy(Tank tank) {
        this.tank = tank;
    }

    @Override
    public void move() {
        long start = System.currentTimeMillis();
        tank.move();
        long end = System.currentTimeMillis();
        System.out.println(String.format("一共用时%ss", end - start));
    }
}