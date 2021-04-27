package juc.sychronizedlearn;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName T07_DirtyRead
 * @Description 线程不同步方法 可与同步方法同时运行 造成脏读
 * @Author hike97
 * @Date 2021/4/13 17:47
 * @Version 1.0
 **/
public class T07_DirtyRead {
    String name;
    double balance;

    /**
     * set 方法 时 延长 设置账户时间 模拟读取该账户数据
     * @param name
     * @param balance
     */
    public synchronized void set(String name,double balance){
        this.name = name;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.balance = balance;
    }

    public /*synchronized*/ double getBalance(String name){
        return this.balance;
    }

    public static void main(String[] args) throws InterruptedException {
        T07_DirtyRead account = new T07_DirtyRead();
        new Thread(()->account.set("zhangsan",200.0)).start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println(account.getBalance("zhangsan"));
        TimeUnit.SECONDS.sleep(2);
        System.out.println(account.getBalance("zhangsan"));
    }
}
