package com.lannister.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by hp on 2019/9/18.
 * 将配置文件中配置的每一个属性的值，映射到这个组件中
 * @ConfigurationProperties：告诉SpringBoot将本类中的属性和配置文件中相关的配置进行绑定
 * prefix="person":配置文件中哪个key下面的所有属性进行一一映射
 *
 * @Component:只有这个组件是容器中的组件，才能使用容器提供的@ConfigurationProperties功能
 */
@Component
//@ConfigurationProperties(prefix = "person2")
@ConfigurationProperties(prefix = "person3")
public class Person {
  private String name;
  private Integer age;
  private Date birthDay;
  private Map<String,Object> scores;
  private List<String> friends;

  @Override
  public String toString() {
    return "Person{" +
        "name='" + name + '\'' +
        ", age=" + age +
        ", birthDay=" + birthDay +
        ", scores=" + scores +
        ", friends=" + friends +
        '}';
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public void setBirthDay(Date birthDay) {
    this.birthDay = birthDay;
  }

  public void setScores(Map<String, Object> scores) {
    this.scores = scores;
  }

  public void setFriends(List<String> friends) {
    this.friends = friends;
  }

  public String getName() {
    return name;
  }

  public Integer getAge() {
    return age;
  }

  public Date getBirthDay() {
    return birthDay;
  }

  public Map<String, Object> getScores() {
    return scores;
  }

  public List<String> getFriends() {
    return friends;
  }
}
