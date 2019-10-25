package com.lannister.springboot.listener;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Created by Lannister on 2019/10/22.
 */
@Component
public class HelloApplicationRunner implements ApplicationRunner {

  @Override
  public void run(ApplicationArguments args) throws Exception {
    System.out.println("ApplicationRunner...run...");
  }
}
