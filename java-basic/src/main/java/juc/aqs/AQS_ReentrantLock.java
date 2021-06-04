package juc.aqs;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hike97 2month
 * @create 2021-05-17 11:37
 * @desc 从reentrantLock 查看AQS源码
 **/
public class AQS_ReentrantLock {
    private static volatile int i = 0;

    public static void main (String[] args) {
        ReentrantLock lock = new ReentrantLock ();
        lock.lock ();
        try {
            i++;
        } finally {
            lock.unlock ();
        }

    }
}
