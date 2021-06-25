package juc.container.blockingqueue;

import com.google.errorprone.annotations.Var;
import lombok.SneakyThrows;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hike97
 * @create 2021-06-25 0:20
 * @desc 实现Scheduler调度器+线程池
 **/
public class Scheduler {
    //    LinkedTransferQueue<Runnable> tasks = new LinkedTransferQueue();
    //也可以用SynchronousQueue
    /**
     * transfer = fair ? new TransferQueue<E>() : new TransferStack<E>();
     * 公平队列 用 双向队列 FIFO
     * 不公平用 双向栈 不保证顺序
     */

    SynchronousQueue<Runnable> tasks = new SynchronousQueue<> (true);
    static AtomicInteger idcount = new AtomicInteger (0);

    public Scheduler (int workers) {
        //        System.out.println ("初始化:Scheduler()");
        for (int i = 0; i < workers; i++) {
            new Thread (new Worker ()).start ();
        }
    }

    public void submit (Runnable r) {
        /*
         * 相当前于 FixedThreadPool
         */
        //        tasks.offer (r);
        //tasks.offer(r) 如果只用offer 相当于->LinkedBlockingQueue 不能作为双向队列->DualQueue
        /*
         *相当于CachedThreadPool
         * 反向压力 如果有足够消费者
         */
        //        while(!tasks.tryTransfer(r)) {
        //            Thread.onSpinWait();
        //            new Thread(new Worker()).start();
        //        }

        while (!tasks.offer (r)) {
            Thread.onSpinWait ();
            new Thread (new Worker ()).start ();
        }
    }

    private class Worker implements Runnable {
        int id;

        public Worker () {
            this.id = idcount.getAndIncrement ();
        }

        @SneakyThrows
        @Override
        public void run () {
            while (true) {
                System.out.println ("wait for Task :tasks.take ()");
                Runnable runnable = tasks.take ();
                //队列中的任务由submit方法实现
                //这里将Runnable对象放到队列里然后执行普通的run方法实现
                runnable.run ();
                System.out.format ("work done by id=%d\n", id);
                Thread.sleep (100);
            }
        }
    }

    public static void main (String[] args) throws InterruptedException {
        Scheduler scheduler = new Scheduler (10);
        //        scheduler.submit (new Runnable () {
        //            @Override
        //            public void run () {
        //                System.out.println ("提交一个任务");
        //            }
        //        });
        for (int i = 0; i < 1000; i++) {
            Thread.sleep (1000);
            scheduler.submit (() -> {
                System.out.println ("submit a task");
            });
        }
    }
}
