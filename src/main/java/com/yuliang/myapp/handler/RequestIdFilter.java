package com.yuliang.myapp.handler;

import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Created by yuliangjin (530367387@qq.com) on 17/12/25.
 *
 * @description Filter that add a unique requestId to MDC which would be used in the log.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@WebFilter(filterName = "requestFilter", urlPatterns = "/*")
public class RequestIdFilter implements Filter {
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    try {
      String mdcData = String.format("requestId:%s", UUID.randomUUID().toString().replace("-", "").substring(0, 12));
      MDC.put("mdcData", mdcData);
      filterChain.doFilter(servletRequest, servletResponse);
    } finally {
      MDC.clear();
    }
  }

  @Override
  public void destroy() {

  }
}
