package com.lannister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by hp on 2019/9/17.
 *
 * @ImportResource：用来导入Spring配置文件
 */

@ImportResource(locations = {"classpath:beans.xml"})
@SpringBootApplication
public class HelloWorldApplication {

  public static void main(String[] args) {

    //Spring应用启动起来
    SpringApplication.run(HelloWorldApplication.class,args);
  }
}
