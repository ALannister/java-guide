package com.lannister.config;

import com.lannister.component.LoginHandlerInterceptor;
import com.lannister.component.MyLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by hp on 2019/10/8.
 */

// 使用WebMvcConfigurerAdapter可以扩展SpringMVC的功能
// @EnableWebMvc  不要全面接管SpringMVC
@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {

    // 浏览器发送"/lannister"请求映射到index.html页面
    registry.addViewController("/lannister").setViewName("index");

  }

  //所有的WebMvcConfigurerAdapter组件都会一起起作用
  @Bean //将组件注册在容器
  public WebMvcConfigurerAdapter webMvcConfigurerAdapter(){
    WebMvcConfigurerAdapter adapter = new WebMvcConfigurerAdapter() {
      //注册拦截器
      @Override
      public void addInterceptors(InterceptorRegistry registry) {
        //静态资源：*.css *.js
        //SpringBoot已经做好了静态资源映射
//        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
//            .excludePathPatterns("/login.html","/","/usr/login");
      }

      //注册视图控制器
      @Override
      public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/login.html").setViewName("login");
        registry.addViewController("/main.html").setViewName("dashboard");
      }
    };
    return adapter;
  }

  @Bean
  public LocaleResolver localeResolver(){
    return new MyLocaleResolver();
  }
}