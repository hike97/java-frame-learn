package juc.container.blockingqueue;

import lombok.ToString;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author hike97
 * @create 2021-06-25 0:06
 * @desc delayQueue 实现 需要继承 Delayed 方法 并且重写 相关方法
 **/

public class DelayQueueTest {
    /**
     * 任务队列
     */
    static DelayQueue<DelayedItem<Integer>> queue = new DelayQueue<> ();

    static class DelayedItem<T> implements Delayed {
        T value;
        long time = 0;

        public DelayedItem (T value, long delay) {
            this.value = value;
            this.time = delay + System.currentTimeMillis ();
        }

        @Override
        public long getDelay (TimeUnit unit) {
            return time - System.currentTimeMillis ();
        }

        @Override
        public int compareTo (Delayed o) {
            return (int) (this.time - ((DelayedItem) o).time);
        }

        @Override
        public String toString () {
            return "DelayedItem{" +
                    "value=" + value +
                    ", time=" + time +
                    '}';
        }

        public static void main (String[] args) {
            //创建1000个任务
            new Thread (() -> {
                for (int i = 0; i < 10; i++) {
                    queue.offer (new DelayedItem<Integer> (i, i * 1000));
                }
            }).start ();

            //消费任务
            new Thread (() -> {
                while (true) {
                    try {
                        DelayedItem<Integer> item = queue.take ();
                        System.out.println (item.toString ());
                    } catch (InterruptedException e) {
                        e.printStackTrace ();
                    }
                }
            }).start ();
        }
    }
}
