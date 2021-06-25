package juc.aqs.cas.lock;

import java.util.concurrent.Exchanger;

/**
 * @author hike97 2month
 * @create 2021-05-16 20:20
 * @desc exchanger 两个线程之间 交换私有变量
 **/
public class T11_TestExchanger {
    static Exchanger<String> exchanger = new Exchanger<> ();

    public static void main (String[] args) {
        new Thread (() -> {
            String s = "T1";
            try {
                s = exchanger.exchange (s);
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
            System.out.println (Thread.currentThread ().getName () + " " + s);

        }, "t1").start ();


        new Thread (() -> {
            String s = "T2";
            try {
                s = exchanger.exchange (s);
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
            System.out.println (Thread.currentThread ().getName () + " " + s);

        }, "t2").start ();
    }
}
