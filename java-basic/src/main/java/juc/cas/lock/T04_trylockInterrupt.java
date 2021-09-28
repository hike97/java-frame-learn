package juc.cas.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hike97 2month
 * @create 2021-05-16 16:43
 * @desc lock 对interrupt异常的反应
 **/
public class T04_trylockInterrupt {
    public static void main (String[] args) {
        System.out.println ("您好啊");
        Lock lock = new ReentrantLock ();
        /**
         * t1 线程
         */
        new Thread (() -> {
            lock.lock ();
            try {
                System.out.println ("t1 start");
                //t1 线程一直持有锁
                TimeUnit.SECONDS.sleep (Integer.MAX_VALUE);
                System.out.println ("t1 end");
            } catch (InterruptedException e) {
                e.printStackTrace ();
            } finally {
                lock.unlock ();
            }
        }, "t1").start ();

        Thread t2 = new Thread (() -> {
            try {
                //可以对interrupt()做出响应
                lock.lockInterruptibly ();
                System.out.println ("t2 start");
                TimeUnit.SECONDS.sleep (5);
                System.out.println ("t2 end");
            } catch (InterruptedException e) {
                System.out.println ("线程被打断,e:" + e);
            } finally {
                lock.unlock ();
            }
        });

        t2.start ();
        //2s后打断线程2的等待
        try {
            TimeUnit.SECONDS.sleep (1);
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }
        t2.interrupt ();

    }
}
