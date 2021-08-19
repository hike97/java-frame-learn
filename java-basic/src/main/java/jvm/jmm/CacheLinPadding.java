package jvm.jmm;

/**
 * @author hike97
 * @create 2021-08-13 18:20
 * @desc 缓存行对齐 实现->disruptor
 **/
public class CacheLinPadding {

    private static class Padding {
        //一个long类型是8个字节 一个cacheLine 是64个字节 这里对齐7个字节
        public volatile long p1, p2, p3, p4, p5, p6, p7;
    }

    //这样保证T类型对齐8个字节
    private static class T extends Padding {
        public volatile long x = 0L;
    }

    //在声明一个不继承对齐的类
    private static class TT {
        public volatile long x = 0L;
    }

    public static T[] arr = new T[2];
    public static TT[] arrTT = new TT[2];

    static {
        //模拟两个元素不在同一个缓存行
        arr[0] = new T ();
        arr[1] = new T ();
        //模拟两个元素在同一个缓存行
        arrTT[0] = new TT ();
        arrTT[1] = new TT ();
    }

    public static void main (String[] args) throws InterruptedException {
        Thread t1 = new Thread (() -> {
            for (int i = 0; i < 10000_0000L; i++) {
                arr[0].x = i;
                //                arrTT[0].x = i;
            }
        });
        Thread t2 = new Thread (() -> {
            for (int i = 0; i < 10000_0000L; i++) {
                arr[1].x = i;
                //                arrTT[1].x = i;
            }
        });
        long start = System.currentTimeMillis ();
        t1.start ();
        t2.start ();
        t1.join ();
        t2.join ();
        long end = System.currentTimeMillis ();
        System.out.println ("cost " + (end - start) + " ms");
    }
}
