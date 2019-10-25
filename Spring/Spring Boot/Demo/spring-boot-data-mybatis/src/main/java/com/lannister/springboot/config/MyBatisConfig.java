package com.lannister.springboot.config;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Lannister on 2019/10/22.
 */
@Configuration
public class MyBatisConfig {

  @Bean
  public ConfigurationCustomizer configurationCustomizer(){
    return new ConfigurationCustomizer() {
      @Override
      public void customize(org.apache.ibatis.session.Configuration configuration) {
        configuration.setMapUnderscoreToCamelCase(true);
      }
    };
  }
}
