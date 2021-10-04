package com.example.handler;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @ClassName DeadLetterQueueConsumer
 * @Description TODO
 * @Author hike97
 * @Date 2021/10/3 23:45
 * @Version 1.0
 **/
@Component
@Slf4j
public class DeadLetterQueueConsumer {
    public static final String DELAYED_QUEUE_NAME = "delayed.queue";

    /**
     * 死信队列模拟延迟队列
     * @param message
     * @param channel
     */
    @RabbitListener (queues = "QD")
    public void receiveD(Message message, Channel channel) {
        String msg = new String(message.getBody());
        log.info("当前时间：{},收到死信队列信息{}", LocalDateTime.now ().format (DateTimeFormatter.ISO_DATE_TIME), msg);
    }

    /**
     * 延迟队列监听
     * @param message
     */
    @RabbitListener(queues = DELAYED_QUEUE_NAME)
    public void receiveDelayedQueue(Message message){
        String msg = new String(message.getBody());
        log.info("当前时间：{},收到延时队列的消息：{}", new Date ().toString(), msg);
    }

    /**
     * 消息确认监听
     */
    public static final String CONFIRM_QUEUE_NAME = "confirm.queue";
    @RabbitListener(queues =CONFIRM_QUEUE_NAME)
    public void receiveMsg(Message message){
        String msg=new String(message.getBody());
        log.info("》》》》》》》》》》》》》》》》》接受到队列 confirm.queue 消息:{}",msg);
    }

    /**
     * 备份交换机 报警队列
     * ~~~~~~~~~~~~~~~~~~~~~~~~~报警发现不可路由消息：继续测试key2
     * 备份交换机 优先级高于 回退消息回调函数
     */
    public static final String WARNING_QUEUE_NAME = "warning.queue";
    @RabbitListener(queues = WARNING_QUEUE_NAME) public void receiveWarningMsg(Message message) {
        String msg = new String(message.getBody());
        log.error("~~~~~~~~~~~~~~~~~~~~~~~~~报警发现不可路由消息：{}", msg);
    }

    /**
     * 当消费失败后将此消息存到 Redis，记录消费次数，如果消费了三次还是失败，就丢弃掉消息，记录日志落库保存
     * 直接填 false ，不重回队列，记录日志、发送邮件等待开发手动处理
     * 不启用手动 ack ，使用 SpringBoot 提供的消息重试
     */

}
