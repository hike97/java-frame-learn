package juc.cas.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hike97 2month
 * @create 2021-05-16 14:12
 * @desc trylock 实现可重入锁
 **/
public class T03_TryLock {
    /**
     * 使用reentrantlock可以完成sychronized 同步锁的同样功能
     * 需要注意的是，必须要必须要必须要手动释放锁（重要的事情说三遍）
     * 使用syn锁定的话如果遇到异常，jvm会自动释放锁，但是lock必须手动释放锁，因此经常在finally中进行锁的释放
     * 使用reentrantlock可以进行“尝试锁定”tryLock，这样无法锁定，或者在指定时间内无法锁定，线程可以决定是否继续等待
     */
    Lock lock = new ReentrantLock ();

    void m1 () {
        /**
         * 在使用阻塞等待获取锁的方式中，必须在try代码块之外，并且在加锁方法与try代码块之间没有任何可能抛出异常的方法调用，
         * 避免加锁成功后，在finally中无法解锁。
         * 说明一：如果在lock方法与try代码块之间的方法调用抛出异常，那么无法解锁，造成其它线程无法成功获取锁。
         * 说明二：如果lock方法在try代码块之内，可能由于其它方法抛出异常，导致在finally代码块中，
         * unlock对未加锁的对象解锁，它会调用AQS的tryRelease方法（取决于具体实现类），抛出IllegalMonitorStateException异常。
         * 说明三：在Lock对象的lock方法实现中可能抛出unchecked异常，产生的后果与说明二相同。
         */
        //***lock.lock 需要放在try 代码块外
        lock.lock ();
        try {
            for (int i = 0; i < 3; i++) {
                TimeUnit.SECONDS.sleep (1);
                System.out.println (i);
            }
        } catch (Exception e) {
            e.printStackTrace ();
        } finally {
            lock.unlock ();
        }
    }

    /**
     * 使用tryLock进行尝试锁定，不管锁定与否，方法都将继续执行
     * 可以根据tryLock的返回值来判定是否锁定
     * 也可以指定tryLock的时间，由于tryLock(time)抛出异常，所以要注意unclock的处理，必须放到finally中
     */
    void m2 () {
        boolean locked = false;
        try {
            locked = lock.tryLock (5, TimeUnit.SECONDS);
            System.out.println ("m2 exec()..." + locked);
        } catch (InterruptedException e) {
            e.printStackTrace ();
        } finally {
            if (locked) {
                lock.unlock ();
            }
        }
    }

    public static void main (String[] args) throws InterruptedException {
        T03_TryLock rl = new T03_TryLock ();
        new Thread (rl::m1).start ();
        TimeUnit.SECONDS.sleep (1);
        new Thread (rl::m2).start ();
    }
}
