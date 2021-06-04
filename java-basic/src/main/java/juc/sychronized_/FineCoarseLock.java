package juc.sychronized_;

import java.util.concurrent.TimeUnit;

/**
 * @author hike97 2month
 * @create 2021-05-14 18:45
 * @desc 锁的粒度优化
 **/
public class FineCoarseLock {
    int count = 0;

    /**
     * 分析 此段代码 除了count++ 需要加锁 其它都不需要
     *
     * @throws InterruptedException
     */
    synchronized void m1 () throws InterruptedException {
        TimeUnit.SECONDS.sleep (2);
        count++;
        TimeUnit.SECONDS.sleep (2);
    }

    /**
     * 锁粒度细化方法
     *
     * @throws InterruptedException
     */
    void m2 () throws InterruptedException {
        TimeUnit.SECONDS.sleep (2);
        //业务逻辑中只有下面这句需要sync，这时不应该给整个方法上锁
        //采用细粒度的锁，可以使线程争用时间变短，从而提高效率
        synchronized (this) {
            count++;
        }
        TimeUnit.SECONDS.sleep (2);
    }
}
