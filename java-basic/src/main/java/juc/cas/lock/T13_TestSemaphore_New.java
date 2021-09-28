package juc.cas.lock;

import java.util.concurrent.Semaphore;

/**
 * @author hike97
 * @create 2021-08-30 23:51
 * @desc semaphore 限流demo
 **/
public class T13_TestSemaphore_New {

    public static void main (String[] args) {
        Semaphore s = new Semaphore (2);
        new Thread (() -> {
            try {
                s.acquire ();
                System.out.println ("T1 running");
                Thread.sleep (200);
                System.out.println ("T1 running");
            } catch (InterruptedException e) {
                e.printStackTrace ();
            } finally {
                s.release ();
            }

        }).start ();

        new Thread (() -> {
            try {
                s.acquire ();
                System.out.println ("T2 running");
                Thread.sleep (200);
                System.out.println ("T2 running");
            } catch (InterruptedException e) {
                e.printStackTrace ();
            } finally {
                s.release ();
            }

        }).start ();
    }
}
