package com.lannister.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Lannister on 2019/10/15.
 */
public class MyFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    System.out.println("MyFilter process...");
    filterChain.doFilter(servletRequest,servletResponse);
  }

  @Override
  public void destroy() {

  }
}
