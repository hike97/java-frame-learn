package rabbitmq.$03_ack;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import rabbitmq.uitls.RabbitMqUtils;

import java.util.Scanner;

/**
 * @author hike97
 * @create 2021-09-27 12:54
 * @desc ack手动应答 demo
 **/
public class AckTask {
    private static final String TASK_QUEUE_NAME = "ack_queue";

    public static void main (String[] argv) throws Exception {
        try (Channel channel = RabbitMqUtils.getChannel ()) {
            //开启发布确认
            channel.confirmSelect ();
            //创建队列持久化
            boolean durable = true;
            channel.queueDeclare (TASK_QUEUE_NAME, durable, false, false, null);
            Scanner sc = new Scanner (System.in);
            System.out.println ("请输入信息");
            while (sc.hasNext ()) {
                String message = sc.nextLine ();
                //MessageProperties.PERSISTENT_TEXT_PLAIN 发送消息持久化
                channel.basicPublish ("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes ("UTF-8"));
                System.out.println ("生产者发出消息" + message);
            }
        }
    }
}
