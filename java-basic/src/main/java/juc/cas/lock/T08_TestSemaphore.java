package juc.cas.lock;


import juc.utils.PoolUtils;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author hike97 2month
 * @create 2021-05-16 19:06
 * @desc 信号灯 秒杀时可能会用到
 **/
public class T08_TestSemaphore {
    public static void main (String[] args) throws Exception {
        //模拟三个停车位
        Semaphore semaphore = new Semaphore (3);
        ThreadPoolExecutor pool = PoolUtils.getPool (6, 6, null);
        for (int i = 0; i < 6; i++) {
            pool.execute (() -> {
                try {
                    //acquire 为0 阻塞
                    semaphore.acquire ();
                    System.out.println (Thread.currentThread ().getName () + " 抢到车位");
                    TimeUnit.SECONDS.sleep (3);
                    System.out.println (Thread.currentThread ().getName () + " 停车3s钟离开");
                } catch (InterruptedException e) {
                    e.printStackTrace ();
                } finally {
                    semaphore.release ();
                }
            });
        }
        pool.shutdown ();
    }
}
