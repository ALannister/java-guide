package com.lannister.component;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Created by hp on 2019/10/9.
 */
public class MyLocaleResolver implements LocaleResolver {
  @Override
  public Locale resolveLocale(HttpServletRequest httpServletRequest) {
    String l = httpServletRequest.getParameter("l");
    Locale locale = Locale.getDefault();
    if(!StringUtils.isEmpty(l)){
      String[] s = l.split("_");
      locale = new Locale(s[0], s[1]);
    }
    return locale;
  }

  @Override
  public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

  }
}
