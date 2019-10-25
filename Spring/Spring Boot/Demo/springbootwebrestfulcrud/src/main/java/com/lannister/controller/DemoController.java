package com.lannister.controller;

import com.lannister.dao.CacheDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hp on 2019/10/9.
 */

@Controller
public class DemoController {

  @Autowired
  CacheDao cacheDao;

  @ResponseBody
  @GetMapping("/demo/get")
  public String testGet(@RequestParam("str") String str){
    return str;
  }

  @ResponseBody
  @PostMapping("/demo/post")
  public String testPost(){
    return cacheDao.remove();
  }
}
