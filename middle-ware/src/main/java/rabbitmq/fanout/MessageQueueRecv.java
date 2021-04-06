package rabbitmq.fanout;

import com.rabbitmq.client.*;
import rabbitmq.common.ConnectionUtil;

import java.io.IOException;

/**
 * @ClassName Recv
 * @Description TODO
 * @Author hike97
 * @Date 2021/4/2 17:04
 * @Version 1.0
 **/
public class MessageQueueRecv {
    /**
     * 发消息到短信队列
     */
//    private final static String QUEUE_NAME = "fanout_exchange_queue_sms";//短信队列
//    private final static String EXCHANGE_NAME = "test_fanout_exchange";  
    //短信队列
    private final static String QUEUE_NAME = "direct_exchange_queue_sms";
    private final static String EXCHANGE_NAME = "test_direct_exchange";

    public static void main(String[] argv) throws Exception {
        // 获取到连接
        Connection connection = ConnectionUtil.getConnection();
        // 获取通道
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 绑定队列到交换机
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "sms");

        // 定义队列的消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            // 获取消息，并且处理，这个方法类似事件监听，如果有消息的时候，会被自动调用
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                // body 即消息体
                String msg = new String(body);
                System.out.println(" [短信服务] received : " + msg + "!");
            }
        };
        // 监听队列，自动返回完成
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
