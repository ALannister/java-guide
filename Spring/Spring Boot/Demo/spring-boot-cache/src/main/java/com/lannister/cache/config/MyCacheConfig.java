package com.lannister.cache.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.Arrays;


/**
 * Created by Lannister on 2019/10/31.
 */
@Configuration
public class MyCacheConfig {

  @Bean("myKeyGenerator")
  public KeyGenerator keyGenerator(){
    return new KeyGenerator() {

      @Override
      public Object generate(Object target, Method method, Object... params) {
        return method.getName() + Arrays.asList(params).toString();
      }
    };
  }
}
