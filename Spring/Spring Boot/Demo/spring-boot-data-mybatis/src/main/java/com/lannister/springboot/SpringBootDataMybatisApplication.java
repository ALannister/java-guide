package com.lannister.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Lannister on 2019/10/21.
 */
//将接口扫描装配到容器中
@MapperScan(basePackages = "com.lannister.springboot.mapper")
@SpringBootApplication
public class SpringBootDataMybatisApplication {
  public static void main(String[] args) {
    SpringApplication.run(SpringBootDataMybatisApplication.class,args);
  }
}
