package com.lannister.amqp.service;

import com.lannister.amqp.bean.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * Created by Lannister on 2019/11/4.
 */
@Service
public class BookService {

  private Logger logger = LoggerFactory.getLogger(getClass());

  @RabbitListener(queues = "lannister.news")
  public void receive(Book book){
    logger.info("收到消息: " + book);
  }

  @RabbitListener(queues = "lannister")
  public void receive02(Message message){
    logger.info(message.getBody().toString());
    logger.info(message.getMessageProperties().toString());
  }
}
