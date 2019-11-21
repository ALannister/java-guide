package com.lannister.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by Lannister on 2019/11/5.
 */
@Service
public class AsyncService {

  @Async
  public void hello() throws InterruptedException {
    TimeUnit.SECONDS.sleep(3);
    System.out.println("hello.....");
  }
}
