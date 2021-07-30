package com.example.zk;

import lombok.Data;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * @author hike97
 * @create 2021-07-28 21:18
 * @desc
 **/
@Data
public class WatchCallBack implements Watcher, AsyncCallback.StatCallback, AsyncCallback.DataCallback {

    ZooKeeper zk;

    MyConf conf;

    CountDownLatch latch = new CountDownLatch (1);

    public void aWait () {
        zk.exists ("/AppConf", this, this, "ABC");
        try {
            latch.await ();
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }
    }

    /**
     * getDataCallback
     */
    @Override
    public void processResult (int rc, String path, Object ctx, byte[] data, Stat stat) {
        if (data != null) {
            String s = new String (data);
            //将取到的数据放回到实体类 释放锁
            conf.setConf (s);
            latch.countDown ();
        }
    }

    @Override
    public void processResult (int rc, String path, Object ctx, Stat stat) {
        if (stat != null) {
            zk.getData ("/AppConf", this, this, "vvv");
        }
    }

    /**
     * 事件监听
     *
     * @param event
     */
    @Override
    public void process (WatchedEvent event) {
        switch (event.getType ()) {
            case None:
                break;
            case NodeCreated:
                zk.getData ("/AppConf", this, this, "vvv");
                break;
            case NodeDeleted:
                //TODO 节点被删
                conf.setConf ("");
                latch = new CountDownLatch (1);
                break;
            case NodeDataChanged://节点数据有变更
                zk.getData ("/AppConf", this, this, "vvv");
                break;
            case NodeChildrenChanged:
                break;
            case DataWatchRemoved:
                break;
            case ChildWatchRemoved:
                break;
        }
    }
}
