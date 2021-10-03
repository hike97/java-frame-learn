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

    @RabbitListener (queues = "QD")
    public void receiveD(Message message, Channel channel) {
        String msg = new String(message.getBody());
        log.info("当前时间：{},收到死信队列信息{}", LocalDateTime.now ().format (DateTimeFormatter.ISO_DATE_TIME), msg);
    }

    @RabbitListener(queues = DELAYED_QUEUE_NAME)
    public void receiveDelayedQueue(Message message){
        String msg = new String(message.getBody());
        log.info("当前时间：{},收到延时队列的消息：{}", new Date ().toString(), msg);
    }

}
