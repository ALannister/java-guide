package com.lannister.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by hp on 2019/9/18.
 *
 * @Value一个一个注入值
 */

@Component
public class Dog {

  @Value("${dog.name}")
  private String name;
  @Value("#{1*2}")
  private Integer age;
  @Value("2002/02/05")
  private Date birthDay;
  //@Value不支持复杂类型封装
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
    return "Dog{" +
        "name='" + name + '\'' +
        ", age=" + age +
        ", birthDay=" + birthDay +
        ", families=" + families +
        ", friends=" + friends +
        '}';
  }
}
