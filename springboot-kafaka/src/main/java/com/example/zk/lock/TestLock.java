package com.example.zk.lock;

import com.example.zk.MyConf;
import com.example.zk.WatchCallBack;
import com.example.zk.ZkUtils;
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author hike97
 * @create 2021-07-29 0:19
 * @desc zk分布式锁
 **/
public class TestLock {

    ZooKeeper zk;

    @Before
    public void conn () {
        zk = ZkUtils.getZk ("/testLock");
    }

    @After
    public void close () throws InterruptedException {
        zk.close ();
    }

    @Test
    public void lock () {
        for (int i = 0; i < 10; i++) {
            new Thread (() -> {
                ZkLock lock = new ZkLock (zk, Thread.currentThread ().getName ());
                //抢锁
                lock.tryLock ();
                //干活
                System.out.println ("Working Working 996ing ~~~~~~~~~~~");
                try {
                    Thread.sleep (1000);
                } catch (InterruptedException e) {
                    e.printStackTrace ();
                }
                //释放锁
                lock.unLock ();
            }).start ();
        }

        while (true) {
        }
    }

}
