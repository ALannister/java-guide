package com.atguigu.deploy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/abc")
    public String hello(){
        return "hello";
    }
}
