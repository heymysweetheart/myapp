package com.yuliang.myapp.handler;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yuliangjin (530367387@qq.com) on 17/9/8.
 *
 * @description 该AOP类用来记录日志
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

  ThreadLocal<Long> startTime = new ThreadLocal<>();

  @Pointcut("execution(public * com.yuliang.myapp.controller.*.*(..))")
  public void webLog(){}

  @Before("webLog()")
  public void before(JoinPoint joinPoint) {
    ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = requestAttributes.getRequest();
    startTime.set(System.currentTimeMillis());
    log.info("Url: {}, IP: {}, method: {}, args: {}", request.getRequestURL(), request.getRemoteAddr(), joinPoint
        .getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
  }

  @AfterReturning(returning = "ret", pointcut = "webLog()")
  public void after(Object ret) {
    log.info("Response: {}", ret);
    log.info("Spend time: {}ms.", System.currentTimeMillis() - startTime.get());
  }
}
