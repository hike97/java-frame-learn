package rabbitmq.$08_dead_queue;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import rabbitmq.uitls.RabbitMqUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName DeadQueueConsumer01
 * @Description 拒绝消息模拟场景
 * @Author hike97
 * @Date 2021/10/3 21:41
 * @Version 1.0
 **/
public class DeadQueueConsumer01_Reject {
    //普通交换机名称
    private static final String NORMAL_EXCHANGE = "normal_exchange";
    //死信交换机名称
    private static final String DEAD_EXCHANGE = "dead_exchange";

    public static void main (String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel ();
        //绑定普通队列交换机和死信队列交换机
        channel.exchangeDeclare (NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare (DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);
        //声明死信队列
        String deadQueue = "dead-queue";
        channel.queueDeclare (deadQueue,false,false,false,null);
        channel.queueBind (deadQueue,DEAD_EXCHANGE,"lisi");

        //正常队列绑定死信队列
        String normalQueue = "normal-queue";
        //绑定死信队列的参数
        Map<String, Object> params = new HashMap<> ();
        params.put ("x-dead-letter-exchange",DEAD_EXCHANGE);
        params.put ("x-dead-letter-routing-key","lisi");
//        params.put ("x-max-length",6);
        channel.queueDeclare (normalQueue,false,false,false,params);
        channel.queueBind (normalQueue,NORMAL_EXCHANGE,"zhangsan");

        //常规操作
        System.out.println("等待接收消息.....");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            if(message.equals("info5")){
                System.out.println("DeadQueueConsumer01_Reject 接收到消息" + message + "并拒绝签收该消息");
                //requeue 设置为 false 代表拒绝重新入队 该队列如果配置了死信交换机将发送到死信队列中
                channel.basicReject(delivery.getEnvelope().getDeliveryTag(), false);
            }else {
                System.out.println("DeadQueueConsumer01_Reject 接收到消息"+message);
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        boolean autoAck = false;
        channel.basicConsume(normalQueue, autoAck, deliverCallback, consumerTag -> {
        });
    }
}
