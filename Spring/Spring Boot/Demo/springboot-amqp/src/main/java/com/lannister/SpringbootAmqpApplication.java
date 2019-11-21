package com.lannister;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 自动配置：
 * 1、RabbitAutoConfiguration
 * 2、自动配置了连接工厂 ConnectionFactory
 * 3、RabbitProperties 封装了Rabbie
 * 4、RabbitTemplate:给RabbitMQ发送和接收消息
 * 5、AmqpAdmin ： RabbitMQ系统管理组件
 *    AmqpAdmin:创建和删除 Queue，Exchange.Binding
* 6、@EnableRabbit + @RabbitListener 监听消息队列
 * Created by Lannister on 2019/11/3.
 */
@EnableRabbit  //开启基于注解的RabbitMQ模式
@SpringBootApplication
public class SpringbootAmqpApplication {
  public static void main(String[] args) {
    SpringApplication.run(SpringbootAmqpApplication.class,args);
  }
}
