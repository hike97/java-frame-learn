package juc.container.blockingqueue.base;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Author hike97
 * @Description
 * @create 2020-09-24 14:30
 * @Modified By:
 **/
public class BlockingQueueDemo {
    public static void main (String[] args) throws InterruptedException {
        ArrayBlockingQueue<Object> blockingQueue = new ArrayBlockingQueue<> (3);
        /*GROUP1 : add remove 抛出异常组 element*/
        System.out.println (blockingQueue.add ("a"));
        System.out.println (blockingQueue.add ("b"));
        System.out.println (blockingQueue.add ("c"));
        /**
         * java.lang.IllegalStateException: Queue full
         */
        System.out.println (blockingQueue.element ());
        //		System.out.println (blockingQueue.add ("a"));
        System.out.println (blockingQueue.remove ());
        System.out.println (blockingQueue.remove ());
        System.out.println (blockingQueue.remove ());
        /**
         * java.util.NoSuchElementException
         */
        //		blockingQueue.remove ();
        System.out.println ();
        System.out.println ();

        /*GROUP2 : offer poll 返回boolean类型 peek*/
        System.out.println (blockingQueue.offer ("a"));
        System.out.println (blockingQueue.offer ("b"));
        System.out.println (blockingQueue.offer ("c"));
        System.out.println (blockingQueue.offer ("c"));

        System.out.println (blockingQueue.peek ());

        System.out.println (blockingQueue.poll ());
        System.out.println (blockingQueue.poll ());
        System.out.println (blockingQueue.poll ());
        System.out.println (blockingQueue.poll ());

        System.out.println ();
        System.out.println ();

        /*GROUP3 : put take 无返回 阻塞*/
        blockingQueue.put ("a");
        blockingQueue.put ("b");
        blockingQueue.put ("c");
        System.out.println ("blocking···············");
        //		blockingQueue.put ("d");
        blockingQueue.take ();
        blockingQueue.take ();
        blockingQueue.take ();
        //		blockingQueue.take ();


        System.out.println (blockingQueue.offer ("1", 2L, TimeUnit.SECONDS));
        System.out.println (blockingQueue.offer ("2", 2L, TimeUnit.SECONDS));
        System.out.println (blockingQueue.offer ("3", 2L, TimeUnit.SECONDS));
        System.out.println (blockingQueue.offer ("4", 2L, TimeUnit.SECONDS));
    }
}
