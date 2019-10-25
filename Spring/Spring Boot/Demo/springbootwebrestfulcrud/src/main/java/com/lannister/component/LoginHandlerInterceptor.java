package com.lannister.component;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hp on 2019/10/9.
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {

  //目标方法执行之前
  @Override
  public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
    Object user = httpServletRequest.getSession().getAttribute("loginUser");
    if(user == null) {
      //未登录,返回登录页面
      httpServletRequest.setAttribute("msg","没有权限，请先登录");
      httpServletRequest.getRequestDispatcher("/login.html").forward(httpServletRequest,httpServletResponse);
      return false;
    }
    else{
      //已登录，放行请求
      return true;
    }
  }

  @Override
  public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

  }

  @Override
  public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

  }
}
