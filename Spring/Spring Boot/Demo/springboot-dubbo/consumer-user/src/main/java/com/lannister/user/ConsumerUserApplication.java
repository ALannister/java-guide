package com.lannister.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Lannister on 2019/11/11.
 * 1、引入依赖
 * 2、配置dubbo的注册中心服务
 * 3、引用服务
 */
@SpringBootApplication
public class ConsumerUserApplication {
  public static void main(String[] args) {
    SpringApplication.run(ConsumerUserApplication.class,args);
  }
}
