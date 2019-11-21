package com.lannister.amqp;

import com.lannister.amqp.bean.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lannister on 2019/11/3.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootAmqpApplicationTests {

  @Autowired
  RabbitTemplate rabbitTemplate;
  @Autowired
  AmqpAdmin amqpAdmin;

  private Logger logger = LoggerFactory.getLogger(getClass());

  /**
   * 1、单播（点对点）
   */
  @Test
  public void sendTest(){
    //Message需要自己构造一个；定义消息体内容和消息头
    //rabbitTemplate.send(exchange,routeKey,message);

    //只需要传入要发送的对象，自动序列化发送给rabbitMQ
    //rabbitTemplate.convertAndSend(exchange,routeKey,object);

    Map<String,Object> map = new HashMap<>();
    map.put("msg","这是第一个美人鱼");
    map.put("msg", Arrays.asList("小青",123,true));
    //rabbitTemplate.convertAndSend("exchange.direct","lannister.news",map);
    rabbitTemplate.convertAndSend(
        "exchange.direct",
        "lannister.news",
        new Book("湖畔","东野圭吾"));
  }

  //接受消息，如何将数据自动转为json发送出去
  @Test
  public void receiveTest(){
    Object o = rabbitTemplate.receiveAndConvert("lannister.news");
    logger.info(o.getClass().toString());
    logger.info(o.toString());
  }

  /**
   * 2、广播
   */
  @Test
  public void sendTest02(){
    rabbitTemplate.convertAndSend(
        "exchange.fanout",
        "",
        new Book("恶意","东野圭吾"));
  }

  @Test
  public void createExchange(){
    amqpAdmin.declareExchange(new DirectExchange("amqpadmin.exchange"));
    logger.info("exchange 创建完成");
  }

  @Test
  public void createQueue(){
    amqpAdmin.declareQueue(new Queue("amqpadmin.queue",true));
    logger.info("queue 创建完成");
  }

  @Test
  public void createBinding(){
    amqpAdmin.declareBinding(new Binding(
        "amqpadmin.queue",
        Binding.DestinationType.QUEUE,
        "amqpadmin.exchange",
        "amqp.hello",
        null));
  }

}
