package com.lannister.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by Lannister on 2019/10/15.
 */
public class MyListener implements ServletContextListener {
  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    System.out.println("contextInitialized...web应用启动");
  }

  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {
    System.out.println("contextDestroyed...当前web项目销毁");
  }
}
