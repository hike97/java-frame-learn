package juc.sychronizedlearn;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName T08_ReenTranctLock
 * @Description 可重入锁
 * @Author hike97
 * @Date 2021/4/14 15:10
 * @Version 1.0
 **/
public class T08_ReenTranctLock {

    synchronized void m1() {
        System.out.println("m1 start");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        m2();
        System.out.println("m1 end");
    }

    synchronized void m2() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m2");
    }

    public static void main(String[] args) {
//        new T08_ReenTranctLock().m1();
        //继承可重入锁
        new TT().m();
    }

}

/**
 * 继承中所出现的同步锁
 */
class T {
    synchronized void m() {
        System.out.println("m start");
        try {
            System.out.println("父类方法当前类对象：" + this);
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m end");
    }
}

class TT extends T {
    @Override
    synchronized void m() {
        System.out.println("m child start");
        //此处 super.m 锁的也是当前对象this 就是TT 和 TT锁的当前对象this 是同一个
        // 所以这种情况也算可重入锁的一种
        super.m();
        System.out.println("子类方法当前类对象：" + this);
        System.out.println("m child end");
    }
}
