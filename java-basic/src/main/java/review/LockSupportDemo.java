package review;

import java.util.concurrent.locks.LockSupport;

/**
 * @author hike97
 * @create 2021-08-29 23:16
 * @desc lockSupportDemo
 **/
public class LockSupportDemo {
    public static void main (String[] args) {

    }
}

class MyThread01 extends Thread {
    private Object object;

    public MyThread01 (Object object) {
        this.object = object;
    }

    @Override
    public void run () {
        System.out.println ("before unpark");
        // 释放许可
        LockSupport.unpark ((Thread) object);
        System.out.println ("after unpark");
    }
}