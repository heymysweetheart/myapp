package com.yuliang.myapp.handler;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuliangjin (530367387@qq.com) on 17/12/25.
 *
 * @description This is a java filter configuration bean.
 */
@Configuration
public class WebConfigurator extends WebMvcConfigurerAdapter {

  @Bean
  public FilterRegistrationBean getParamFilter() {
    ParameterFilter parameterFilter = new ParameterFilter();
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
    filterRegistrationBean.setFilter(parameterFilter);

    List<String> urlPatterns= new ArrayList<>();
    urlPatterns.add("/*"); //Apply to all requests
    filterRegistrationBean.setUrlPatterns(urlPatterns);
    filterRegistrationBean.setOrder(1);

    return filterRegistrationBean;
  }
}
