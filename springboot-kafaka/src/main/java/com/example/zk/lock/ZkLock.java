package com.example.zk.lock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author hike97
 * @create 2021-07-29 0:27
 * @desc zk 分布式锁
 **/
@Data
public class ZkLock implements Watcher, AsyncCallback.StringCallback, AsyncCallback.Children2Callback, AsyncCallback.StatCallback {

    private ZooKeeper zk;

    String threadName;

    CountDownLatch latch = new CountDownLatch (1);

    String pathName;

    public ZkLock () {
    }

    public ZkLock (ZooKeeper zk, String threadName) {
        this.zk = zk;
        this.threadName = threadName;
    }

    public void tryLock () {
        //acl 访问控制列表
        /**
         * OPEN_ACL_UNSAFE  : 完全开放的ACL，任何连接的客户端都可以操作该属性znode
         * CREATOR_ALL_ACL : 只有创建者才有ACL权限
         * READ_ACL_UNSAFE：只能读取ACL
         */
        /**
         * CreateMode类型分为4种
         * 1.PERSISTENT--持久型
         * 2.PERSISTENT_SEQUENTIAL--持久顺序型
         * 3.EPHEMERAL--临时型
         * 4.EPHEMERAL_SEQUENTIAL--临时顺序型
         */
        try {
            zk.create ("/lock", threadName.getBytes (), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, this, "create_lock_ctx");
            latch.await ();
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }
    }

    public void unLock () {
        try {
            zk.delete (pathName, -1);
            System.out.println (threadName + " over work......");
        } catch (InterruptedException e) {
            e.printStackTrace ();
        } catch (KeeperException e) {
            e.printStackTrace ();
        }
    }

    /**
     * zk.exists WatchCallBack
     * 如果第一个释放了 只有第二个收到回调事件
     * 不是第一个也会造成后边的通知
     *
     * @param event
     */
    @Override
    public void process (WatchedEvent event) {
        //关注节点删除时间
        switch (event.getType ()) {
            case None:
                break;
            case NodeCreated:
                break;
            case NodeDeleted:
                zk.getChildren ("/", false, this, "children_ctx");
                break;
            case NodeDataChanged:
                break;
            case NodeChildrenChanged:
                break;
            case DataWatchRemoved:
                break;
            case ChildWatchRemoved:
                break;
        }
    }

    /**
     * zk.create#StringCallBack
     *
     * @param rc
     * @param path
     * @param ctx
     * @param name
     */
    @Override
    public void processResult (int rc, String path, Object ctx, String name) {
        if (null != name) {
            System.out.println ("[" + threadName + "]" + "create callback pathName " + name);
            pathName = name;
            //看看自己是不是id最小 不关注父目录所以是false
            zk.getChildren ("/", false, this, "children_ctx");
        }
    }

    /**
     * zk.getChildren#Children2Callback
     *
     * @param rc
     * @param path
     * @param ctx
     * @param children
     * @param stat
     */
    @Override
    public void processResult (int rc, String path, Object ctx, List<String> children, Stat stat) {
        //每个线程都创建完了并且能看到自己前面的节点 但是 是乱序的
        System.out.println (threadName + " look locks");
        //        for (String child : children) {
        //            System.out.println (child);
        //        }
        Collections.sort (children);
        //  pathName /0000000022
        //  children节点 0000000022
        int i = children.indexOf (pathName.substring (1));
        //是不是第一个
        if (i == 0) {
            //yes
            try {
                zk.setData ("/", threadName.getBytes (), -1);
            } catch (KeeperException e) {
                e.printStackTrace ();
            } catch (InterruptedException e) {
                e.printStackTrace ();
            } finally {
                latch.countDown ();
            }
            System.out.println (threadName + " i am 1st `````````");
        } else {
            //no 判断前一个lock存不存在 watch也只关注该节点前一个
            zk.exists ("/" + children.get (i - 1), this, this, "judge_ctx");
        }
    }

    /**
     * StatCallback#zk.exists
     *
     * @param rc
     * @param path
     * @param ctx
     * @param stat
     */
    @Override
    public void processResult (int rc, String path, Object ctx, Stat stat) {

    }
}
