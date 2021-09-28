package juc.cas.lock;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hike97 2month
 * @create 2021-05-16 17:48
 * @desc 公平锁
 **/
public class T05_FairLock extends Thread {
    private static ReentrantLock lock = new ReentrantLock (true);

    @SneakyThrows
    @Override
    public void run () {
        for (int i = 0; i < 100; i++) {
            lock.lock ();
            try {
                TimeUnit.SECONDS.sleep (1);
                System.out.println (Thread.currentThread ().getName () + "获得锁");
            } finally {
                lock.unlock ();
            }
        }
    }

    public static void main (String[] args) {
        T05_FairLock rl = new T05_FairLock ();
        Thread th1 = new Thread (rl);
        Thread th2 = new Thread (rl);
        th1.start ();
        th2.start ();
    }
}
