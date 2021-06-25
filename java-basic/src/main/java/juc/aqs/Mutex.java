package juc.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author hike97
 * @create 2021-06-17 20:29
 * @desc 利用AQS互斥锁
 **/
public class Mutex extends AbstractQueuedSynchronizer {
    @Override
    protected boolean tryAcquire (int arg) {
        return compareAndSetState (0, 1);
    }

    @Override
    protected boolean tryRelease (int arg) {
        return compareAndSetState (1, 0);
    }

    public static int i = 0;

    static int count () {
        for (int j = 0; j < 10000; j++) {
            i++;
        }
        return i;
    }

    public static void main (String[] args) throws InterruptedException {
        Mutex mutex = new Mutex ();
        Thread t1 = new Thread (() -> {
            mutex.acquire (0);
            count ();
            mutex.release (0);
        });

        Thread t2 = new Thread (() -> {
            mutex.acquire (0);
            count ();
            mutex.release (0);
        });
        t1.start ();
        t2.start ();
        t1.join ();
        t2.join ();
        System.out.println ("t1 t2 执行完毕");
        count ();
        System.out.println (i);
    }
}
