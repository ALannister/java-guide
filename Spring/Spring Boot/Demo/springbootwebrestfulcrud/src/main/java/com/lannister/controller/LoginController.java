package com.lannister.controller;

import com.lannister.dao.UserDao;
import com.lannister.exception.UserNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;


/**
 * Created by hp on 2019/10/9.
 */
@Controller
public class LoginController {

  @Autowired UserDao userDao;

  @PostMapping("/usr/login")
  public String login(@RequestParam("username") String username,
                      @RequestParam("password") String password,
                      Map<String,String> map, HttpSession session){
    if(userDao.containsUser(username,password)){
      //登录成功,防止表单重复提交，可以重定向到主页
      session.setAttribute("loginUser",username);
      return "redirect:/main.html";
    }else{
      //登陆失败
      map.put("msg","用户名或密码错误");
      return "login";
    }
  }
}
