package com.lannister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by Lannister on 2019/11/5.
 */
@EnableScheduling
@EnableAsync //开启异步注解功能
@SpringBootApplication
public class SpringbootTaskApplication {
  public static void main(String[] args) {
    SpringApplication.run(SpringbootTaskApplication.class,args);
  }
}
