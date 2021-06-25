/**
 * volatile并不能保证多个线程共同修改running变量时所带来的不一致问题，也就是说volatile不能替代synchronized
 * 运行下面的程序，并分析结果
 *
 * @author mashibing
 */
package juc.aqs.cas.volatile_;

import java.util.ArrayList;
import java.util.List;

public class T02_VolatileNotSync {
    volatile int count = 0;

    synchronized void m () {
        for (int i = 0; i < 10000; i++) {
            //count ++ 不保证原子性操作
            //解决方案 用synchronized
            count++;
        }
    }

    public static void main (String[] args) {
        T02_VolatileNotSync t = new T02_VolatileNotSync ();

        List<Thread> threads = new ArrayList<Thread> ();
        //重启10个线程 每个线程加10000次
        for (int i = 0; i < 10; i++) {
            threads.add (new Thread (t::m, "thread-" + i));
        }

        threads.forEach ((o) -> o.start ());

        threads.forEach ((o) -> {
            try {
                o.join ();
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
        });

        System.out.println (t.count);


    }

}


