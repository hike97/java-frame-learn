package rabbitmq.$05_fanout;

import com.rabbitmq.client.Channel;
import rabbitmq.uitls.RabbitMqUtils;

import java.util.Scanner;

/**
 * @ClassName EmitLog
 * @Description 发消息给交换机
 * @Author hike97
 * @Date 2021/10/3 19:31
 * @Version 1.0
 **/
public class EmitLog {
    private static final String EXCHANGE_NAME = "logs";
    public static void main(String[] argv) throws Exception {
        try (Channel channel = RabbitMqUtils.getChannel()) {
            /**
             * 声明一个 exchange
             * 1.exchange 的名称
             * 2.exchange 的类型
             */
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            Scanner sc = new Scanner(System.in);
            System.out.println("请输入信息");
            while (sc.hasNext()) {
                String message = sc.nextLine();
                channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
                System.out.println("生产者发出消息" + message);
            }
        }
    }
}
