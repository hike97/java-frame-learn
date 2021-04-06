package rabbitmq.queuemodel;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import rabbitmq.common.ConnectionUtil;

/**
 * @ClassName Send
 * @Description TODO
 * @Author hike97
 * @Date 2021/4/2 10:42
 * @Version 1.0
 **/
public class Send {
    private final static String QUEUE_NAME = "simple_queue";

    public static void main(String[] args) throws Exception {
        //1.获取到连接
        Connection connection = ConnectionUtil.getConnection();
        //2.创建channel
        Channel channel = connection.createChannel();
        //3.声明队列
        /**
         * Declare a queue
         * Params:
         * queue – the name of the queue
         * durable – true if we are declaring a durable queue (the queue will survive a server restart)
         * exclusive – true if we are declaring an exclusive queue (restricted to this connection)
         * autoDelete – true if we are declaring an autodelete queue (server will delete it when no longer in use)
         * arguments – other properties (construction arguments) for the queue
         * Returns:
         * a declaration-confirm method to indicate the queue was successfully declared
         * Throws:
         * IOException – if an error is encountered
         */
//        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
//        String message = "Hello World";
//        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
//        System.out.println("[x] Sent " + message);

        for (int i = 0; i < 50; i++) {
            // 消息内容
            String message = "task .. " + i;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

            Thread.sleep(i * 2);
        }

        channel.close();
        connection.close();
    }
}
