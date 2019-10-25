package com.lannister.config;

import com.lannister.filter.MyFilter;
import com.lannister.listener.MyListener;
import com.lannister.servlet.MyServlet;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * Created by Lannister on 2019/10/15.
 */
@Configuration
public class MyServerConfig {

  //注册三大组件
  @Bean
  public ServletRegistrationBean myservlet(){
    ServletRegistrationBean registrationBean = new ServletRegistrationBean(new MyServlet(),"/myServlet");
    return registrationBean;
  }

  @Bean
  public FilterRegistrationBean myFilter(){
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
    filterRegistrationBean.setFilter(new MyFilter());
    filterRegistrationBean.setUrlPatterns(Arrays.asList("/hello","/myServlet"));
    return filterRegistrationBean;
  }

  @Bean
  public ServletListenerRegistrationBean myListener(){
    ServletListenerRegistrationBean<MyListener> myListenerServletListenerRegistrationBean = new ServletListenerRegistrationBean<>(new MyListener());
    return myListenerServletListenerRegistrationBean;
  }
  //配置嵌入式的Servlet容器
  @Bean
  public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer(){
    return new EmbeddedServletContainerCustomizer() {
      @Override
      public void customize(ConfigurableEmbeddedServletContainer container) {
        container.setPort(8083);
      }
    };
  }
}
