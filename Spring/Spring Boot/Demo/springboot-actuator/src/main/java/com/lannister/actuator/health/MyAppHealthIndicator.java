package com.lannister.actuator.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Created by Lannister on 2019/11/11.
 */
@Component
public class MyAppHealthIndicator implements HealthIndicator {
  @Override
  public Health health() {

    //自定义的检查方法
    //Health.up().build()代表健康
    return Health.down().withDetail("msg","服务异常").build();
  }
}
