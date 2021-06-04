package juc.cas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hike97 2month
 * @create 2021-05-15 11:19
 * @desc automicInteger 代替sychronnized
 **/
public class T01_AtomicInteger {
    AtomicInteger count = new AtomicInteger (0);

    void m () {
        for (int i = 0; i < 10000; i++) {
            //incrementAndGet 返回的是新值
            //getAndIncrement 返回的是旧值
            count.incrementAndGet ();
        }
    }

    public static void main (String[] args) {
        T01_AtomicInteger t = new T01_AtomicInteger ();
        List<Thread> threads = new ArrayList<> ();
        for (int i = 0; i < 10; i++) {
            threads.add (new Thread (t::m, "thread-" + i));
        }
        threads.forEach ((o) -> o.start ());


        //当前只剩下主线程 和 gc线程时 退出循环
        while (Thread.activeCount () > 2) {
            Thread.yield ();
        }

        System.out.println (t.count);
    }
}
