package com.lannister.actuator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Lannister on 2019/11/11.
 * 自定义健康状态指示器
 * 1、编写一个指示器 实现 HealthIndicator接口
 * 2、指示器的名字 xxxHealthIndicator
 * 3、加入容器
 */
@SpringBootApplication
public class SpringbootActuatorApplication {
  public static void main(String[] args) {
    SpringApplication.run(SpringbootActuatorApplication.class,args);
  }
}
