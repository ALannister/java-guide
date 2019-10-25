package com.lannister.controller;

import com.lannister.exception.UserNotExistException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lannister on 2019/10/15.
 */
@ControllerAdvice
public class MyExceptionHandler {

  @ExceptionHandler(UserNotExistException.class)
  public String handleException(Exception e, HttpServletRequest request){
    Map<String,Object> map = new HashMap<>();
    request.setAttribute("javax.servlet.error.status_code",500);
    map.put("code","user not exist");
    map.put("message","用户出错了");
    request.setAttribute("ext",map);
    return "forward:/error";
  }
}
