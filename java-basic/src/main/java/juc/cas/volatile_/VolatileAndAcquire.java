package juc.cas.volatile_;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author hike97
 * @create 2021-06-22 20:26
 * @desc Volatile 和 AtomicReference getAcquire 和 setRelease 可以让前后指令重排序
 * happens-before关系有个很重要的性质，就是传递性，即，如果hb(a,b),hb(b,c)，则有hb(a,c)。
 * A happens before B 如果A在B前发生 A带来的变化在B可以管擦回到
 **/
public class VolatileAndAcquire {
    /**
     * volatile 禁止指令重排
     */
    volatile static DbConnection ref;

    /**
     * 利用atomicReference
     */
    static AtomicReference<DbConnection> atomicReference = new AtomicReference<> ();

    /**
     * 经典双重锁 + volatile
     *
     * @return
     */
    public static DbConnection getDb () {
        //指令1
        //指令2
        //volatile 指令1 指令2 不能重排序
        if (ref == null) {
            synchronized (VolatileAndAcquire.class) {
                if (ref == null) {
                    ref = new DbConnection ();
                }
            }
        }
        return ref;
    }

    public static DbConnection getDbAtomic () {
        //java9 atomicReference 前后都可以指令重排序
        var localRef = atomicReference.getAcquire ();
        if (localRef == null) {
            synchronized (VolatileAndAcquire.class) {
                localRef = atomicReference.getAcquire ();
                if (localRef == null) {
                    atomicReference.setRelease (new DbConnection ());
                }
            }
        }
        return localRef;
    }

    public static void main (String[] args) {
        for (int i = 0; i < 20; i++) {
            new Thread (() -> {
                getDb ();
            }).start ();
        }
    }

    /**
     * 静态内部类模拟数据库连接
     */
    static class DbConnection {
        DbConnection () {
            System.out.println ("Thread [" + Thread.currentThread ().getId () + "]: is initializing");
        }
    }
}
