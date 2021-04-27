package juc.sychronizedlearn;

/**
 * @ClassName T06_SyncMethodRunNoSync
 * @Description TODO 同步方法中可运行非同步方法
 * @Author hike97
 * @Date 2021/4/13 16:58
 * @Version 1.0
 **/
public class T06_SyncMethodRunNoSync {

    public synchronized void m1(){
        System.out.println("m1 start " + Thread.currentThread().getName());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m1 end " + Thread.currentThread().getName());
    }

    public void m2(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m2 start " + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        T06_SyncMethodRunNoSync t = new T06_SyncMethodRunNoSync();
        new Thread(t::m1,"t1").start();
        new Thread(t::m2,"t2").start();
    }

}
