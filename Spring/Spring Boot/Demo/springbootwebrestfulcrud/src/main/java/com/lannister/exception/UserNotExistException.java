package com.lannister.exception;

/**
 * Created by Lannister on 2019/10/15.
 */
public class UserNotExistException extends RuntimeException{
  public UserNotExistException() {
    super("用户不存在");
  }
}
