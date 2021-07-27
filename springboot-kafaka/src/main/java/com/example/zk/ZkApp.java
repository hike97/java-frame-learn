package com.example.zk;

import lombok.SneakyThrows;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author hike97
 * @create 2021-07-27 19:05
 * @desc zookeeper api
 **/
public class ZkApp {
    private final static String ZK_ADDRESS = "192.168.3.10:2181,192.168.3.11:2181,192.168.3.12:2181,192.168.3.13:2181";

    public static void main (String[] args) throws IOException, InterruptedException, KeeperException {
        System.out.println ("Hello World");
        //zk 是有session概念的
        //watch 观察 回调 watch 注册只发生在读
        //new zk的时候 传入的watch 这个watch session级别的 跟path node 没有关系。
        CountDownLatch latch = new CountDownLatch (1);
        ZooKeeper zooKeeper = new ZooKeeper (ZK_ADDRESS, 3000, new Watcher () {
            @Override
            public void process (WatchedEvent event) {
                Event.KeeperState state = event.getState ();
                Event.EventType type = event.getType ();
                String path = event.getPath ();
                System.out.println (event.toString ());
                switch (state) {
                    case Unknown:
                        break;
                    case Disconnected:
                        break;
                    case NoSyncConnected:
                        break;
                    case SyncConnected:
                        System.out.println ("Session事件监听::connected ~~~~~~");
                        latch.countDown ();
                        break;
                    case AuthFailed:
                        break;
                    case ConnectedReadOnly:
                        break;
                    case SaslAuthenticated:
                        break;
                    case Expired:
                        break;
                    case Closed:
                        break;
                    default:

                }

                switch (type) {
                    case None:
                        break;
                    case NodeCreated:
                        break;
                    case NodeDeleted:
                        break;
                    case NodeDataChanged:
                        break;
                    case NodeChildrenChanged:
                        break;
                    case DataWatchRemoved:
                        break;
                    case ChildWatchRemoved:
                        break;
                    default:

                }
            }
        });
        latch.await ();
        ZooKeeper.States state = zooKeeper.getState ();
        switch (state) {
            case CONNECTING:
                System.out.println ("ZooKeeper Connecting...........");
                break;
            case ASSOCIATING:
                break;
            case CONNECTED:
                System.out.println ("ZooKeeper Connected ..............");
                break;
            case CONNECTEDREADONLY:
                break;
            case CLOSED:
                break;
            case AUTH_FAILED:
                break;
            case NOT_CONNECTED:
                break;
            default:

        }
        //创建临时节点 创建节点没有watch watch 只发生在读类型调用 如 get exist
        String pathName = zooKeeper.create ("/ooxx", "olddata".getBytes (), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        //针对路径 的watch
        Stat stat = new Stat ();//stat 为元数据
        byte[] node = zooKeeper.getData ("/ooxx", new Watcher () {

            @Override
            public void process (WatchedEvent event) {
                System.out.println ("getData watch:" + event.toString ());
                //回调之后 继续检测 true:调用的是zk的watch
                try {
                    zooKeeper.getData ("/ooxx", this, stat);
                } catch (KeeperException e) {
                    e.printStackTrace ();
                } catch (InterruptedException e) {
                    e.printStackTrace ();
                }
            }
        }, stat);

        System.out.println ("获取【ooxx】的数据： " + new String (node));
        //触发回调
        Stat stat1 = zooKeeper.setData ("/ooxx", "newData".getBytes (), 0);
        //还会触发吗？
        Stat stat2 = zooKeeper.setData ("/ooxx", "newData01".getBytes (), stat1.getVersion ());

        /**
         * 异步回调
         */
        System.out.println ("````````````````async start`````````````````");
        zooKeeper.getData ("/ooxx", false, new AsyncCallback.DataCallback () {
            @Override
            public void processResult (int rc, String path, Object ctx, byte[] bytes, Stat stat) {
                System.out.println ("~~~~~~~~~~~~~async call back~~~~~~~~~~~~~");
                System.out.println (new String (bytes));
            }
        }, "abc");
        System.out.println ("````````````````async over`````````````````");
        Thread.sleep (222222);

    }
}
