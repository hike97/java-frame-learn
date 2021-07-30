package com.example.zk;

import com.google.errorprone.annotations.Var;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author hike97
 * @create 2021-07-28 19:34
 * @desc
 **/
public class ZkUtils {

    private static ZooKeeper zk;

    private static String address = "192.168.3.10:2181,192.168.3.11:2181,192.168.3.12:2181,192.168.3.13:2181";

    private static DefaultWatch watch = new DefaultWatch ();

    private static CountDownLatch init = new CountDownLatch (1);

    public static ZooKeeper getZk (String businessPath) {
        try {
            zk = new ZooKeeper (address + businessPath, 1000, watch);
        } catch (IOException e) {
            e.printStackTrace ();
        }
        try {
            init.await ();
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }
        return zk;
    }


    private static class DefaultWatch implements Watcher {
        public DefaultWatch () {
            System.out.println ("Session Watch init ~~~");
        }

        @Override
        public void process (WatchedEvent event) {

            System.out.println (event.toString ());

            switch (event.getState ()) {
                case Unknown:
                    break;
                case Disconnected:
                    break;
                case NoSyncConnected:
                    break;
                case SyncConnected:
                    init.countDown ();
                    System.out.println ("SessionConnected");
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
                    break;
            }
        }
    }
}
