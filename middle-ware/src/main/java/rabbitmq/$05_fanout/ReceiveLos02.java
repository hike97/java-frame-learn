package rabbitmq.$05_fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import rabbitmq.uitls.RabbitMqUtils;

import java.io.IOException;

/**
 * @author hike97
 * @create 2021-09-27 21:32
 * @desc
 **/
public class ReceiveLos02 {
    //交换机的名称
    public static final String EXCHANGE_NAME = "logs";

    public static void main (String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel ();
        channel.exchangeDeclare (EXCHANGE_NAME,"fanout");
        //声明一个临时队列
        String queueName = channel.queueDeclare ().getQueue ();
        //绑定交换机与队列
        channel.queueBind (queueName,EXCHANGE_NAME,"");
        System.out.println ("等待接收消息,把接收到消息打印在屏幕上······");
        //接收消息的回调函数
        DeliverCallback deliverCallback = new DeliverCallback () {
            @Override
            public void handle (String consumerTag, Delivery message) throws IOException {
                System.out.println ("控制台ReceiveLos02打印接收到的消息：" + new String (message.getBody (), "UTF-8"));
            }
        };
        channel.basicConsume (queueName, true,deliverCallback,consumerTag -> {});
    }
}
