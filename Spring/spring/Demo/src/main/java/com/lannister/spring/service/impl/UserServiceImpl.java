package com.lannister.spring.service.impl;

import com.lannister.spring.service.UserService;

/**
 * Created by hp on 2019/9/26.
 */
public class UserServiceImpl implements UserService {

  public UserServiceImpl() {
    System.out.println("UserServiceImpl Constructor");
  }

  @Override
  public void addUser() {
    System.out.println("[ioc] add a user!");
  }


  //生命周期
  public void myInit(){
    System.out.println("UserServiceImpl myInit!");
  }
  public void myDestroy(){
    System.out.println("UserServiceImpl myDestory!");
  }
}
