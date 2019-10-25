package com.lannister.springboot;

import com.lannister.bean.Cat;
import com.lannister.bean.Dog;
import com.lannister.bean.Person;
import com.lannister.service.HelloService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by hp on 2019/9/18.
 *
 * SpringBoot单元测试
 * 可以在测试期间很方便地进行自动注入等容器的功能
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootApplicationTest {

  @Autowired
  private Person person;
  @Autowired
  private Dog dog;
  @Autowired
  private Cat cat;
  @Autowired
  ApplicationContext ioc;

  Logger logger = LoggerFactory.getLogger(getClass());

  @Test
  public void contextLoads1(){
    System.out.println(person);
  }

  @Test
  public void contextLoads2(){
    System.out.println(dog);
  }

  @Test
  public void contextLoads3(){
    System.out.println(cat);
  }

  @Test
  public void testHelloService(){
    boolean b = ioc.containsBean("helloService");
    System.out.println(b);
  }

  @Test
  public void testHelloService2(){
    boolean b = ioc.containsBean("helloService2");
    System.out.println(b);
  }

  @Test
  public void testLogback(){

    //日志级别
    //由低到高  trace<debug<info<warn<error
    //可以调整输出的日志级别
    logger.trace("这是trace日志");
    logger.debug("这是debug日志");
    //SpringBoot默认使用的是info级别
    logger.info("这是info日志");
    logger.warn("这是warn日志");
    logger.error("这是error日志");

  }

}
