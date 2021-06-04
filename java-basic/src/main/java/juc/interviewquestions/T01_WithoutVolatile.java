package juc.interviewquestions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author hike97 2month
 * @create 2021-05-16 22:43
 * @desc volatile没有把握不要用
 **/
public class T01_WithoutVolatile {
    /**
     * 曾经的面试题：（淘宝？）
     * 实现一个容器，提供两个方法，add，size
     * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
     * <p>
     * 分析下面这个程序，能完成这个功能吗？
     *
     * @author mashibing
     */
    //version1:这里线程之间没有通信 明显无法实现
    //    List<Object> lists = new ArrayList<> ();
    //version2:尝试 使用volatile 或者 用同步容器
    //经过试验发现其中间 加上 睡一秒的代码 是可以实现的 但是注掉掉之后就不好使了
    //    volatile List<Object> lists =  new ArrayList<> ();
    volatile List<Object> lists = Collections.synchronizedList (new ArrayList<> ());

    /**
     * 结论:没有把握不要轻易使用volatile 关键字
     * 最好修饰简单的属性越简单越好
     *
     * @param o
     */

    public void add (Object o) {
        lists.add (o);
    }

    public int size () {
        return lists.size ();
    }

    public static void main (String[] args) {
        T01_WithoutVolatile c = new T01_WithoutVolatile ();
        new Thread (() -> {
            for (int i = 0; i < 10; i++) {
                c.add (new Object ());
                System.out.println ("add " + i);
                //注掉代码之后 volatile 或者 同步线程容器就失效了
               /* try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            }
        }, "t1").start ();

        new Thread (() -> {
            while (true) {
                if (c.size () == 5) {
                    break;
                }
            }
            System.out.println ("t2 结束");
        }, "t2").start ();
    }
}
