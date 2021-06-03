package com.atguigu.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author hike97
 * @create 2021-06-03 18:31
 * @desc
 **/
@SpringBootApplication (exclude = {DataSourceAutoConfiguration.class})
public class BootRedis01Application {
    public static void main (String[] args) {
        SpringApplication.run (BootRedis01Application.class);
    }
}

