package com.yuliang.myapp.handler;

import com.yuliang.myapp.util.IpUtil;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Created by yuliangjin (530367387@qq.com) on 17/9/6.
 *
 * @description 请求参数封装类
 */
public class RequestParamWrapper extends HttpServletRequestWrapper {

  private Map<String, String[]> paramMap = new HashMap<>();
  private String clientIp;

  public RequestParamWrapper(HttpServletRequest request) {
    super(request);
    paramMap.putAll(request.getParameterMap());
    clientIp = request.getHeader("clientIp") == null ? IpUtil.getIpAddr(request) : request.getHeader("clientIp");
  }

  public String getClientIp() {
    return clientIp;
  }

  public void setClientIp(String clientIp) {
    this.clientIp = clientIp;
  }

  @Override
  public String getParameter(String name) {
    String[] values = paramMap.get(name);
    if (values == null || values.length == 0) {
      return null;
    }
    return values[0];
  }

  public String[] getParameterValues(String name) {// 同上
    return paramMap.get(name);
  }


  public void addAllParameters(Map<String, String> otherParams) {
    for (Map.Entry<String, String> entry : otherParams.entrySet()) {
      addParameter(entry.getKey(), entry.getValue());
    }
  }

  public void addParameter(String name, String value) {
    if (value != null) {
      paramMap.put(name, new String[] {String.valueOf(value)});
    }
  }
}
