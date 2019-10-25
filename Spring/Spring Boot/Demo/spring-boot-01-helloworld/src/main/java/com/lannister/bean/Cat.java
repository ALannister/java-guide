package com.lannister.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by hp on 2019/9/18.
 *
 * @PropertySource：加载指定的配置文件
 */

@PropertySource(value = {"classpath:cat.properties"})
@Component
@ConfigurationProperties(prefix = "cat")
public class Cat {

  private String name;
  private Integer age;
  private Date birthDay;
  private Map<String,Object> families;
  private List<String> friends;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public Date getBirthDay() {
    return birthDay;
  }

  public void setBirthDay(Date birthDay) {
    this.birthDay = birthDay;
  }

  public Map<String, Object> getFamilies() {
    return families;
  }

  public void setFamilies(Map<String, Object> families) {
    this.families = families;
  }

  public List<String> getFriends() {
    return friends;
  }

  public void setFriends(List<String> friends) {
    this.friends = friends;
  }

  @Override
  public String toString() {
    return "Cat{" +
        "name='" + name + '\'' +
        ", age=" + age +
        ", birthDay=" + birthDay +
        ", families=" + families +
        ", friends=" + friends +
        '}';
  }
}
