package com.lannister.config;

import com.lannister.service.HelloService2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by hp on 2019/9/18.
 *
 * @Configuration:指明当前类是一个配置类，就是来替代之前的Spring配置文件beans.xml
 */

@Configuration
public class MyAppConfig {

  //将方法的返回值添加到容器中；
  //容器中这个组件默认的id就是方法名
  @Bean
  public HelloService2 helloService2(){
    System.out.println("配置类@Bean给容器添加组件了");
    return new HelloService2();
  }
}
