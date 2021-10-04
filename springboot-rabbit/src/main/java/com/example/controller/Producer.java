package com.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @ClassName Producer
 * @Description  发布确认Springboot 版本
 * @Author hike97
 * @Date 2021/10/4 21:47
 * @Version 1.0
 **/
@RestController
@RequestMapping ("/confirm")
@Slf4j
public class Producer {
    public static final String CONFIRM_EXCHANGE_NAME = "confirm.exchange";
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 回调函数 包括消息到exchange的回调 和 到queue的回调
     */
    @PostConstruct
    public void init(){
        /**
         * exchange 回调函数
         */
        rabbitTemplate.setConfirmCallback (new RabbitTemplate.ConfirmCallback () {
            @Override
            public void confirm (CorrelationData correlationData, boolean ack, String cause) {
                String id=correlationData!=null?correlationData.getId():"";
                if(ack){
                    log.info("交换机已经收到 id 为:{}的消息",id);
                }else{
                    log.info("交换机回调函数检测！！！,交换机还未收到 id 为:{}消息,由于原因:{}",id,cause);
                }
            }
            //交换机回调函数检测！！！,交换机还未收到 id 为:2消息,
            // 由于原因:channel error; protocol method:
            // #method<channel.close>(reply-code=404,
            // reply-text=NOT_FOUND - no exchange 'confirm.exchange11'
            // in vhost '/', class-id=60, method-id=40)
        });
        /*
         * true：
         * 交换机无法将消息进行路由时，会将该消息返回给生产者
         * false：
         * 如果发现消息无法进行路由，则直接丢弃
         */
        rabbitTemplate.setMandatory (true);

        /**
         * 设置回退消息 回调函数
         */
        rabbitTemplate.setReturnCallback (new RabbitTemplate.ReturnCallback () {
            @Override
            public void returnedMessage (Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.error(" 回退消息回调函数检测！！！消息{}, 被交换机 {} 退回，退回原因 :{}, 路 由 key:{}",new String(message.getBody()),exchange,replyText,routingKey);
            }
        });
        //回退消息回调函数检测！！！消息继续测试key2, 被交换机 confirm.exchange 退回，退回原因 :NO_ROUTE, 路 由 key:key2

    }
    @GetMapping ("sendMessage/{message}")
    public void sendMessage(@PathVariable String message){

        //指定消息 id 为 1
        CorrelationData correlationData1=new CorrelationData("1");
        String routingKey="key1";
        rabbitTemplate.convertAndSend(CONFIRM_EXCHANGE_NAME,routingKey,message+routingKey,correlationData1);

        //此处故意写错一个没有的Exchange
        CorrelationData correlationData2=new CorrelationData("2");
        routingKey="key1";
        rabbitTemplate.convertAndSend(CONFIRM_EXCHANGE_NAME+"11",routingKey,message+routingKey,correlationData2);

        //此处故意写错一个没有的routeKey
        CorrelationData correlationData3=new CorrelationData("3");
        routingKey="key2";
        rabbitTemplate.convertAndSend(CONFIRM_EXCHANGE_NAME,routingKey,message+routingKey,correlationData3);
        log.info("发送消息内容:{}",message);
    }
}