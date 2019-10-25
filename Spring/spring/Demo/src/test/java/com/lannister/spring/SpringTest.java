package com.lannister.spring;

import com.lannister.spring.service.BookService;
import com.lannister.spring.service.UserService;
import com.lannister.spring.service.impl.UserServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by hp on 2019/9/26.
 */
public class SpringTest {

  @Test
  //scope : 默认是singleton
  public void demo01(){
    String xmlPath = "applicationContext1.xml";
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
    UserService userService = (UserService)applicationContext.getBean("userService");
    UserService userService1 = (UserService)applicationContext.getBean("userService");
    System.out.println(userService);
    System.out.println(userService1);
    userService.addUser();
  }

  @Test
  //属性注入
  public void demo02(){
    String xmlPath = "applicationContext2.xml";
    ApplicationContext applicationContext = new  ClassPathXmlApplicationContext(xmlPath);
    BookService bookService = (BookService)applicationContext.getBean("bookService");
    bookService.addBook();
  }

  @Test
  //静态工厂
  public void demo03(){
    String xmlPath = "applicationContext3.xml";
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
    UserService userService = applicationContext.getBean("userService", UserServiceImpl.class);
    userService.addUser();
  }

  @Test
  //实例工厂
  public void demo04(){
    String xmlPath = "applicationContext4.xml";
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
    UserServiceImpl penService = applicationContext.getBean("userService", UserServiceImpl.class);
    penService.addUser();
  }

  @Test
  //scope=prototype
  public void demo05(){
    String xmlPath = "applicationContext5.xml";
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
    UserService userService = (UserService)applicationContext.getBean("userService");
    UserService userService2 = (UserService)applicationContext.getBean("userService");
    System.out.println(userService);
    System.out.println(userService2);
    userService.addUser();
    userService2.addUser();
  }

  @Test
  public void demo06(){
    String xmlPath = "applicationContext6.xml";
    ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
    UserService userService = (UserService)applicationContext.getBean("userService");
    userService.addUser();
    //要求：1.容器必须close，销毁方法执行
    //close方法接口ApplicationContext没有定义，实现类ClassPathXmlApplicationContext提供
    applicationContext.close();
  }


}
