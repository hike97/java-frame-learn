package juc.deadlock;


import juc.utils.PoolUtils;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author hike97
 * @Description 死锁描述
 * @create 2020-09-25 11:07
 * @Modified By:
 **/
class HoldLockThread implements Runnable {

    private String lockA;
    private String lockB;

    public HoldLockThread (String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run () {
        synchronized (lockA) {
            System.out.println (Thread.currentThread ().getName () + "\t 自己持有" + lockA + "\t 尝试获得" + lockB);
            try {
                TimeUnit.SECONDS.sleep (2L);
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
            synchronized (lockB) {
                System.out.println (Thread.currentThread ().getName () + "\t 自己持有" + lockB + "\t 尝试获得" + lockA);
            }
        }
    }
}

/**
 * 两个以上的线程在进行过程中，进行资源抢夺时，没有外力的情况下互相等待的情况。
 */
public class DeadLockDemo {
    public static void main (String[] args) {
        ThreadPoolExecutor pool = PoolUtils.getPool (2, 2, "AAA%d");
        String lockA = "lockA";
        String lockB = "lockB";
        pool.execute (new HoldLockThread (lockA, lockB));
        pool.execute (new HoldLockThread (lockB, lockA));
        pool.shutdown ();
        /**
         * 排查死锁情况的工具
         * jps = java ps  jps -l
         * 相当于linux ps -ef|grep xxx ls-l
         */

        /**
         * Found one Java-level deadlock:
         * =============================
         * "AAA0":
         *   waiting to lock monitor 0x000002de5c329b00 (object 0x00000007129e8538, a java.lang.String),
         *   which is held by "AAA1"
         * "AAA1":
         *   waiting to lock monitor 0x000002de5c329c00 (object 0x00000007129e8508, a java.lang.String),
         *   which is held by "AAA0"
         *
         * Java stack information for the threads listed above:
         * ===================================================
         * "AAA0":
         *         at com.interview.lock.HoldLockThread.run(DeadLockDemo.java:34)
         *         - waiting to lock <0x00000007129e8538> (a java.lang.String)
         *         - locked <0x00000007129e8508> (a java.lang.String)
         *         at java.util.concurrent.ThreadPoolExecutor.runWorker(java.base@11.0.2/ThreadPoolExecutor.java:1128)
         *         at java.util.concurrent.ThreadPoolExecutor$Worker.run(java.base@11.0.2/ThreadPoolExecutor.java:628)
         *         at java.lang.Thread.run(java.base@11.0.2/Thread.java:834)
         * "AAA1":
         *         at com.interview.lock.HoldLockThread.run(DeadLockDemo.java:34)
         *         - waiting to lock <0x00000007129e8508> (a java.lang.String)
         *         - locked <0x00000007129e8538> (a java.lang.String)
         *         at java.util.concurrent.ThreadPoolExecutor.runWorker(java.base@11.0.2/ThreadPoolExecutor.java:1128)
         *         at java.util.concurrent.ThreadPoolExecutor$Worker.run(java.base@11.0.2/ThreadPoolExecutor.java:628)
         *         at java.lang.Thread.run(java.base@11.0.2/Thread.java:834)
         *
         * Found 1 deadlock.
         */
    }
}
