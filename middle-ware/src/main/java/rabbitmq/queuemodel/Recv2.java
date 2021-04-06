package rabbitmq.queuemodel;

import com.rabbitmq.client.*;
import rabbitmq.common.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName Recv2
 * @Description TODO
 * @Author hike97
 * @Date 2021/4/2 15:49
 * @Version 1.0
 **/
public class Recv2 {
    private final static String QUEUE_NAME = "simple_queue";

    public static void main(String[] argv) throws Exception {
        // 获取到连接
        Connection connection = ConnectionUtil.getConnection();
        // 创建通道
        final Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //设置一个channel 同一时间 只能执行一个任务
        channel.basicQos(1);
        // 定义队列的消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            // 获取消息，并且处理，这个方法类似事件监听，如果有消息的时候，会被自动调用
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                int i = 1/0;
//                System.out.println(i);
                // body 即消息体
                String msg = new String(body);
                System.out.println(" [x] received : " + msg + "!");
                // 手动进行ACK
                /*
                 *  void basicAck(long deliveryTag, boolean multiple) throws IOException;
                 *  deliveryTag:用来标识消息的id
                 *  multiple：是否批量.true:将一次性ack所有小于deliveryTag的消息。
                 */
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        // 监听队列，第二个参数false，手动进行ACK
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}
