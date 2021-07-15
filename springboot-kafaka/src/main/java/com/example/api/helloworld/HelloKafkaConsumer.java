package com.example.api.helloworld;

import com.example.api.constant.ApiConstant;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * @author hike97
 * @create 2021-07-12 0:53
 * @desc consumer helloworld
 **/
public class HelloKafkaConsumer {
    public static void main (String[] args) {
        Properties properties = new Properties ();
        //broker key value 反序列化
        properties.put (ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.3.10:9092");
        properties.put (ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put (ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        //消费群组 非必须
        properties.put (ConsumerConfig.GROUP_ID_CONFIG, "test1");
        KafkaConsumer<Object, Object> consumer = new KafkaConsumer<> (properties);
        try {
            //消费者订阅可以多个主题
            consumer.subscribe (Collections.singletonList (ApiConstant.HELLO_TOPIC));
            while (true) {
                //500毫秒
                var records = consumer.poll (Duration.ofMillis (500));
                for (var record : records) {
                    System.out.println ("获取到了message:" + record);
                    //do my work 打包任务投入线程池

                }
            }
        } catch (Exception e) {
            e.printStackTrace ();
        } finally {
        }
    }
}
