package juc.cas.lock;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author hike97 2month
 * @create 2021-05-16 19:14
 * @desc 读写锁
 **/
public class T09_TestReadWriteLock {
    private static int value;
    static Lock lock = new ReentrantLock ();
    //读写锁 没有实现lock接口
    static ReadWriteLock readWriteLock = new ReentrantReadWriteLock ();
    static Lock readLock = readWriteLock.readLock ();
    static Lock writeLock = readWriteLock.writeLock ();

    public static void read (Lock lock) {
        lock.lock ();
        try {
            Thread.sleep (1000);
            System.out.println ("read over");
        } catch (InterruptedException e) {
            e.printStackTrace ();
        } finally {
            lock.unlock ();
        }
    }

    public static void write (Lock lock, int v) {
        lock.lock ();
        try {
            Thread.sleep (1000);
            value = v;
            System.out.println ("write over");
        } catch (InterruptedException e) {
            e.printStackTrace ();
        } finally {
            lock.unlock ();
        }
    }

    public static void main (String[] args) throws InterruptedException {
        System.out.println ("reentrantLock 实现读写");
        Runnable readReentrantLockRunnable = () -> read (lock);
        Runnable writeReentrantLockRunnable = () -> write (lock, new Random ().nextInt ());
        for (int i = 0; i < 10; i++) {
            new Thread (readReentrantLockRunnable).start ();
        }
        for (int i = 0; i < 2; i++) {
            new Thread (writeReentrantLockRunnable).start ();
        }
        //        TimeUnit.SECONDS.sleep (15);
        System.out.println ("readWriteLock  实现读写");
        readReentrantLockRunnable = () -> read (readLock);
        writeReentrantLockRunnable = () -> write (readLock, new Random ().nextInt ());
        for (int i = 0; i < 10; i++) {
            new Thread (readReentrantLockRunnable).start ();
        }
        for (int i = 0; i < 2; i++) {
            new Thread (writeReentrantLockRunnable).start ();
        }
    }
}
