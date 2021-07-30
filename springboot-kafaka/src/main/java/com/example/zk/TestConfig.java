package com.example.zk;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author hike97
 * @create 2021-07-28 20:50
 * @desc
 **/
public class TestConfig {

    ZooKeeper zk;

    @Before
    public void conn () {
        zk = ZkUtils.getZk ("/testConf");
    }

    @Test
    public void getConf () {
        WatchCallBack watchCallBack = new WatchCallBack ();
        watchCallBack.setZk (zk);
        MyConf conf = new MyConf ();
        watchCallBack.setConf (conf);
        watchCallBack.aWait ();
        //三种情况
        /**
         * 1.节点不存在
         * 2.节点存在
         * 3.
         */
        while (true) {
            //如果获取节点后，节点被删
            if ("".equals (conf.getConf ())) {
                System.out.println ("conf diu le .............");
                watchCallBack.aWait ();
            } else {
                System.out.println (conf.getConf ());
            }


            try {
                Thread.sleep (200);
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
        }
    }

    @After
    public void close () throws InterruptedException {
        zk.close ();
    }

}
