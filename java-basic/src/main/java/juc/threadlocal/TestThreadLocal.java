package juc.threadlocal;

/**
 * @author hike97 2month
 * @create 2021-05-17 16:03
 * @desc ThreadLocal 测试
 **/
public class TestThreadLocal {
    //ThreadLocal set 方法
    //将Thread.currentThread.map(ThreadLocal,value) 设置到了当前线程的map中
    //实际应用 数据库连接池的Connection对象放入图当前线程 后续方法执行 会从ThreadLocalMap 中获取 达到声明式事务效果
}
