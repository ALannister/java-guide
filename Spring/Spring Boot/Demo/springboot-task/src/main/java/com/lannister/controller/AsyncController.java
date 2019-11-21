package com.lannister.controller;

import com.lannister.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Lannister on 2019/11/5.
 */
@RestController
public class AsyncController {

  @Autowired
  private AsyncService asyncService;

  @GetMapping("/hello")
  public String hello() throws InterruptedException {
      asyncService.hello();
      return "success";
  }
}
