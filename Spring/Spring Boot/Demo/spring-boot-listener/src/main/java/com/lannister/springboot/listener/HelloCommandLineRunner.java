package com.lannister.springboot.listener;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by Lannister on 2019/10/22.
 */
@Component
public class HelloCommandLineRunner implements CommandLineRunner {

  @Override
  public void run(String... args) throws Exception {
    System.out.println("CommandLineRunner...run..." + Arrays.asList(args));
  }
}
