package juc.container.blockingqueue.base;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Author hike97
 * @Description 同步队列
 * @create 2020-09-24 15:56
 * @Modified By:
 **/
public class SychronousQueueDemo {
    public static void main (String[] args) {
        BlockingQueue<String> blockingQueue = new SynchronousQueue<> ();
        new Thread (() -> {
            try {
                System.out.println (Thread.currentThread ().getName () + "put 1");
                blockingQueue.put ("1");
                System.out.println (Thread.currentThread ().getName () + "put 2");
                blockingQueue.put ("2");
                System.out.println (Thread.currentThread ().getName () + "put 3");
                blockingQueue.put ("3");
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
        }, "AAA").start ();

        new Thread (() -> {
            try {
                System.out.println (Thread.currentThread ().getName () + "get 1");
                blockingQueue.take ();
                TimeUnit.SECONDS.sleep (5);
                System.out.println (Thread.currentThread ().getName () + "get 2");
                blockingQueue.take ();
                TimeUnit.SECONDS.sleep (5);
                System.out.println (Thread.currentThread ().getName () + "get 3");
                blockingQueue.take ();
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
        }, "BBB").start ();

    }
}
