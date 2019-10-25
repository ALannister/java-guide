package com.lannister.springboot.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Lannister on 2019/10/21.
 */
@Configuration
public class DruidConfig {
  @ConfigurationProperties(prefix = "spring.datasource")
  @Bean
  public DataSource druid(){
    return new DruidDataSource();
  }

  //配置Druid的监控
  //1、配置一个管理后台的Servlet
  @Bean
  public ServletRegistrationBean statViewServlet(){
    ServletRegistrationBean registrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
    HashMap<String, String> initParams = new HashMap<>();

    initParams.put("loginUsername","admin");
    initParams.put("loginPassword","123456");
    initParams.put("allow","");//默认就是允许所有访问
    initParams.put("deny","10.170.22.76");

    registrationBean.setInitParameters(initParams);
    return registrationBean;
  }

  //2.配置一个web监控的filter
  @Bean
  public FilterRegistrationBean webStatFilter(){
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
    filterRegistrationBean.setFilter(new WebStatFilter());

    HashMap<String, String> initParams = new HashMap<>();
    initParams.put("exclusions","*.js,*.css,/druid/*");

    filterRegistrationBean.setInitParameters(initParams);

    filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));

    return filterRegistrationBean;
  }
}
