package juc.aqs.cas;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author hike97 2month
 * @create 2021-05-15 11:51
 * @desc atomic sync longadder 性能测试
 **/
public class t02_AtomciVsSyncVsLongAdder {
    //atomic
    static AtomicLong count1 = new AtomicLong (0L);
    //long sync
    static long count2 = 0L;
    //longadder
    static LongAdder count3 = new LongAdder ();

    public static void main (String[] args) throws InterruptedException {
        Thread[] threads = new Thread[1000];
        //method 1
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread (() -> {
                for (int j = 0; j < 100000; j++) {
                    count1.incrementAndGet ();
                }
            });
        }
        long start = System.currentTimeMillis ();
        for (Thread t : threads) {
            t.start ();
        }
        for (Thread t : threads) {
            t.join ();
        }

        long end = System.currentTimeMillis ();
        System.out.println ("Atomic: " + count1.get () + " time " + (end - start));

        //method 2
        Object lock = new Object ();
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread (() -> {
                for (int j = 0; j < 100000; j++) {
                    synchronized (lock) {
                        count2++;
                    }
                }
            });
        }

        start = System.currentTimeMillis ();
        for (Thread t : threads) {
            t.start ();
        }
        for (Thread t : threads) {
            t.join ();
        }

        end = System.currentTimeMillis ();
        System.out.println ("Sync: " + count2 + " time " + (end - start));

        //method 3

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread (() -> {
                for (int j = 0; j < 100000; j++) {
                    count3.increment ();
                }
            });
        }

        start = System.currentTimeMillis ();

        for (Thread t : threads) {
            t.start ();
        }
        for (Thread t : threads) {
            t.join ();
        }

        end = System.currentTimeMillis ();

        //TimeUnit.SECONDS.sleep(10);

        System.out.println ("LongAdder: " + count1.longValue () + " time " + (end - start));

        /**
         * Atomic: 100000000 time 1679  比较稳定
         * Sync: 100000000 time 2826     相对来说 最慢
         * LongAdder: 100000000 time 232 忽高忽低
         */

    }
}
