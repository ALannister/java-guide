package com.lannister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Lannister on 2019/11/9.
 * 1、引入Spring Security
 * 2、编写Spring Security的配置类;
 *      @EnableWebMvcSecurity
 *      extends WebSecurityConfigurerAdapter
 * 3、控制请求的访问权限
 *
 */
@SpringBootApplication
public class SpringbootSecurityApplication {
  public static void main(String[] args) {
    SpringApplication.run(SpringbootSecurityApplication.class,args);
  }
}
