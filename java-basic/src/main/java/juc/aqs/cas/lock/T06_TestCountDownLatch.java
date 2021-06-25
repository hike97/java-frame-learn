package juc.aqs.cas.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author hike97 2month
 * @create 2021-05-16 17:53
 * @desc 门闩 :保证每个线程执行完成 用latch.await() 阻塞
 **/
public class T06_TestCountDownLatch {
    public static void main (String[] args) throws InterruptedException {
        //        countDownLatchDemo();
        //        countDownLatchDemoInOrder();
        //        usingCountDownLatch();
        usingJoin ();

    }

    private static void countDownLatchDemo () {
        for (int i = 0; i < 6; i++) {
            new Thread (() -> {
                System.out.println (Thread.currentThread ().getName () + "\t********************** 上完自习,离开教室");
            }).start ();
        }
        System.out.println (Thread.currentThread ().getName () + "\t********************** 班长最后关门走人");
    }

    private static void countDownLatchDemoInOrder () {
        CountDownLatch latch = new CountDownLatch (6);
        for (int i = 0; i < 6; i++) {
            new Thread (() -> {
                System.out.println (Thread.currentThread ().getName () + "\t********************** 上完自习,离开教室");
                latch.countDown ();
            }, String.valueOf (i)).start ();
            try {
                TimeUnit.SECONDS.sleep (1);
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
        }

        try {
            latch.await ();
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }
        System.out.println (Thread.currentThread ().getName () + "\t********************** 班长最后关门走人");
    }

    private static void usingCountDownLatch () throws InterruptedException {
        Thread[] threads = new Thread[100];
        CountDownLatch latch = new CountDownLatch (threads.length);
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread (() -> {
                int result = 0;
                for (int j = 0; j < 10000; j++) {
                    result += j;
                }
                System.out.println ("result=" + result);
                latch.countDown ();
            });
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].start ();
        }
        latch.await ();
        System.out.println ("end latch");
    }

    /**
     * 也可以用join 方法
     */
    private static void usingJoin () {
        Thread[] threads = new Thread[100];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread (() -> {
                int result = 0;
                for (int j = 0; j < 10000; j++) result += j;
                System.out.println (result);
            });
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].start ();
        }

        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join ();
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
        }

        System.out.println ("end join");
    }
}
