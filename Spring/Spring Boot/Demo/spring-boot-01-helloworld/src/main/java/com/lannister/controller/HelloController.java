package com.lannister.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by hp on 2019/9/17.
 */

@ResponseBody
@Controller
public class HelloController {

  @RequestMapping("/hello")
  public String hello(){
    return "Hello World!";
  }
}
