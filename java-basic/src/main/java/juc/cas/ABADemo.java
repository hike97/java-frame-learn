package juc.cas;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Author hike97
 * @Description 解决ABA问题
 * @create 2020-09-22 15:08
 * @Modified By:
 **/
public class ABADemo {
    static AtomicReference<Integer> atomicReference = new AtomicReference<> (100);
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<> (100, 1);

    public static void main (String[] args) {
        //ABA问题模拟
        System.out.println ("ABA 问题模拟=================================================================");
        new Thread (() -> {
            atomicReference.compareAndSet (100, 101);
            atomicReference.compareAndSet (101, 100);
        }, "t1").start ();

        new Thread (() -> {
            try {
                TimeUnit.SECONDS.sleep (1);
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
            System.out.println (atomicReference.compareAndSet (100, 2019) + "\t" + atomicReference.get ());
        }, "t2").start ();

        //ABA 问题的解决方法

        try {
            TimeUnit.SECONDS.sleep (2);
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }
        System.out.println ("ABA 问题解决方案=================================================================");
        new Thread (() -> {
            int stamp = atomicStampedReference.getStamp ();
            //暂停1sT3 让T4获取到同一版本
            try {
                TimeUnit.SECONDS.sleep (1);
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
            System.out.println (Thread.currentThread ().getName () + "  current version:  " + stamp);
            atomicStampedReference.compareAndSet (100, 101, atomicStampedReference.getStamp (), atomicStampedReference.getStamp () + 1);
            System.out.println (Thread.currentThread ().getName () + " first update version:" + atomicStampedReference.getStamp ());
            atomicStampedReference.compareAndSet (101, 100, atomicStampedReference.getStamp (), atomicStampedReference.getStamp () + 1);
            System.out.println (Thread.currentThread ().getName () + " second update version:" + atomicStampedReference.getStamp ());
        }, "t3").start ();

        new Thread (() -> {
            int stamp = atomicStampedReference.getStamp ();
            System.out.println (Thread.currentThread ().getName () + "  current version:  " + atomicStampedReference.getStamp ());
            try {
                TimeUnit.SECONDS.sleep (3);
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
            System.out.println (atomicStampedReference.compareAndSet (100, 2019, stamp, stamp + 1) + "\t current value:" + atomicReference.get ());
        }, "t4").start ();


    }
}
