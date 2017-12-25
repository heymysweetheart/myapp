package com.yuliang.myapp.handler;

import com.yuliang.myapp.util.ContextParams;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by yuliangjin (530367387@qq.com) on 17/9/6.
 *
 * @description 参数处理filter，处理app端的请求参数
 */
@Slf4j
public class ParameterFilter implements Filter {
  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    Map<String, String[]> parameterMap = servletRequest.getParameterMap();
    for (Map.Entry<String, String[]> stringEntry : parameterMap.entrySet()) {
      log.info("key: {}, value: {}", stringEntry.getKey(), stringEntry.getValue());
    }
    HttpServletRequest req = (HttpServletRequest)servletRequest;
    RequestParamWrapper wrapper = new RequestParamWrapper(req);

    try {
      clearContextParam();
      ContextParams.token.set(req.getParameter("token"));
      ContextParams.uri.set(req.getRequestURI());
      handleClientIp(wrapper);
      filterChain.doFilter(wrapper, servletResponse);
    } catch (Throwable e) {
      e.printStackTrace();
      throw new RuntimeException("filter发生异常");
    }
  }

  /**
   * 获取客户端的ip
   * @param wrapper http参数封装数据
   */
  private void handleClientIp(RequestParamWrapper wrapper) {
    ContextParams.clientIp.set(wrapper.getClientIp());
  }

  protected static String doUrlDecode(String value) {
    try {
      if (value.contains("%")) {
        value = URLDecoder.decode(value, "UTF-8");
      }
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("UnsupportedEncodingException: value :"+value, e);
    }
    return value;
  }

  protected void clearContextParam() {
    ContextParams.uri.set(null);
    ContextParams.token.set(null);
    ContextParams.clientIp.set(null);
    ContextParams.paramMap.set(null);
    ContextParams.userId.set(null);
  }

  @Override
  public void destroy() {
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }
}
