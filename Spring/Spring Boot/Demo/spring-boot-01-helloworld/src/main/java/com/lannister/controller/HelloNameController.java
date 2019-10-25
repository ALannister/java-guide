package com.lannister.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hp on 2019/9/18.
 *
 * @Value注解的用法
 */

@RestController
public class HelloNameController {

  @Value("${person.name}")
  private String name;

  @RequestMapping("/sayHello")
  public String sayHello(){
    return "Hello," + name;
  }
}
