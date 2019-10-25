package com.lannister.controller;

import com.lannister.exception.UserNotExistException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by hp on 2019/9/20.
 */

@Controller
public class HelloController {

//  @RequestMapping("/")
//  public String index(){
//    return "login";
//  }

  //测试：http://localhost:8081/api/v1/hello?user=aaa
  @ResponseBody
  @RequestMapping("/hello")
  public String hello(@RequestParam("user") String user){
    if("aaa".equals(user)){
      throw new UserNotExistException();
    }
    return "Hello World!";
  }

  @RequestMapping("/success")
  public String success(Map<String,Object> map){
    map.put("welcome","欢迎！");
    map.put("hello","<h1>你好呀</h1>");
    map.put("users", Arrays.asList("zhang3","li4","wang5"));
    map.put("id","div001");
    map.put("class","class001");

    //返回的"success"字符串指定了templates目录下的success.html模板
    return "success";
  }
}
