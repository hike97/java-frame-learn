package io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author hike97
 * @create 2021-08-31 12:10
 * @desc nio单线程
 **/
public class Server {
    public static void main (String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open ();
        ssc.socket ().bind (new InetSocketAddress ("127.0.0.1", 8888));
        ssc.configureBlocking (false);

        System.out.println ("server started ,listening on :" + ssc.getLocalAddress ());
        Selector selector = Selector.open ();
        ssc.register (selector, SelectionKey.OP_ACCEPT);
        //轮询
        while (true) {
            //阻塞方法
            selector.select ();
            //放多个监听器
            Set<SelectionKey> keys = selector.selectedKeys ();
            Iterator<SelectionKey> it = keys.iterator ();
            while (it.hasNext ()) {
                SelectionKey key = it.next ();
                //处理当前时间后把当前监听器remove掉，防止重复处理
                it.remove ();
                handle (key);
            }
        }
    }

    private static void handle (SelectionKey key) throws IOException {
        if (key.isAcceptable ()) {
            try {
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel ();
                SocketChannel sc = null;
                sc = ssc.accept ();
                sc.configureBlocking (false);
                sc.register (key.selector (), SelectionKey.OP_READ);
            } catch (IOException e) {
                e.printStackTrace ();
            } finally {

            }
        } else if (key.isReadable ()) {
            SocketChannel sc = null;
            try {
                sc = (SocketChannel) key.channel ();
                ByteBuffer buffer = ByteBuffer.allocate (512);
                buffer.clear ();
                int len = sc.read (buffer);
                if (len != -1) {
                    System.out.println (new String (buffer.array (), 0, len));
                }
                ByteBuffer bufferToWrite = ByteBuffer.wrap ("HelloClient".getBytes ());
                sc.write (bufferToWrite);
            } catch (IOException e) {
                e.printStackTrace ();
            } finally {
                if (sc != null) {
                    sc.close ();
                }
            }
        }
    }
}
