package com.lannister.springboot.controller;

import com.lannister.starter.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Lannister on 2019/10/28.
 */
@RestController
public class HelloController {

  @Autowired
  HelloService helloService;

  @GetMapping("/hello")
  public String hello(){
    return helloService.sayHelloLannister("xiaolin");
  }
}
