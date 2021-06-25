package juc.container.blockingqueue;

import java.util.concurrent.*;

public class BlockingQueueTest {
    /**
     * 抛异常
     * add/remove
     * 非阻塞
     * offer/poll
     * 阻塞
     * put/take
     *
     * @param argv
     */

    public static void main (String[] argv) {
        BlockingQueue<Integer> queue;
        queue = new ArrayBlockingQueue<Integer> (10);
        //queue = new LinkedBlockingQueue<Integer>();
        //queue = new LinkedBlockingDeque<>();
        //queue = new PriorityBlockingQueue<>(); 遵循顺序
        //queue = new LinkedTransferQueue<Integer>();
        //        queue = new SynchronousQueue<>();
        //queue = new DelayQueue<>(); //元素要实现delayed 接口

        // Producer
        for (int i = 0; i < 100; i++) {
            new Thread (() -> {
                try {
                    queue.put ((int) (Math.random () * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace ();
                }
            }).start ();
        }

        // Consumer
        for (int i = 0; i < 10; i++) {
            new Thread (() -> {
                while (true) {
                    Integer x = null;
                    try {
                        x = queue.take ();
                    } catch (InterruptedException e) {
                        e.printStackTrace ();
                    }
                    System.out.println ("Receive:" + x);
                    try {
                        Thread.sleep (10);
                    } catch (InterruptedException e) {
                        e.printStackTrace ();
                    }


                }
            }).start ();
        }

    }
}
