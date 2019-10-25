package com.lannister.springboot.listener;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by Lannister on 2019/10/22.
 */
public class HelloApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>{

  @Override
  public void initialize(ConfigurableApplicationContext applicationContext) {
    System.out.println("ApplicationContextInitializer...initialize..." + applicationContext);
  }
}
