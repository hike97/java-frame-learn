package juc.sychronizedlearn;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName T01_WhatIsThread
 * @Description TODO
 * @Author hike97
 * @Date 2021/4/12 16:05
 * @Version 1.0
 **/
public class T01_WhatIsThread {

    private static class T1 extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    TimeUnit.MICROSECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("T1");
            }
        }
    }

    /**
     * 线程 一个程序里不同的执行路径
     * @param args
     */
    public static void main(String[] args) {
           //t1.run 相当于方法调用
//        new T1().run();
        new T1().start();
        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.MICROSECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("main");
        }

    }
}
