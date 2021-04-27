package juc.sychronizedlearn;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName T09_ExThread
 * @Description exception 会使当前线程退出
 * @Author hike97
 * @Date 2021/4/15 10:15
 * @Version 1.0
 **/
public class T09_ExThread {
    int count = 0;
    synchronized void m(){
        System.out.println(Thread.currentThread().getName() + " start ");
        while (true){
            count++;
            System.out.println(Thread.currentThread().getName() + " count "+count);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (count==5){
                int i = 1/0;
                System.out.println(i);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        T09_ExThread t = new T09_ExThread();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                t.m();
            }
        };
        new Thread(r,"t1").start();
        TimeUnit.SECONDS.sleep(3);
        new Thread(r,"t2").start();
    }
}
