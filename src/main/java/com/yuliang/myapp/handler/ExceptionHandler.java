package com.yuliang.myapp.handler;

import com.yuliang.myapp.bean.ResultBean;
import com.yuliang.myapp.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by yuliangjin (530367387@qq.com) on 17/9/8.
 *
 * @description 统一异常处理(ClickController的方法除外)
 */
@Aspect
@Component
@Slf4j
public class ExceptionHandler {

  @Pointcut("execution(public * com.yuliang.myapp.controller.*.*(..)) && !execution(public * com.yuliang.myapp" +
      ".controller.TestController.*(..))")
  public void exceptionHandlerAspect(){}

  @Around("exceptionHandlerAspect()")
  public Object handlerControllerMethod(ProceedingJoinPoint joinPoint) {
    ResultBean<?> resultBean;
    try {
      resultBean = (ResultBean<?>)joinPoint.proceed();
    } catch (Throwable throwable) {
      resultBean = handleException(joinPoint, throwable);
    }
    return resultBean;
  }

  private ResultBean<?> handleException(ProceedingJoinPoint joinPoint, Throwable throwable) {
    ResultBean<?> resultBean = new ResultBean<>();
    if (throwable instanceof BusinessException) {
      resultBean = new ResultBean<>(throwable);
    } else {
      log.error(joinPoint.getSignature().getName() + " error ", throwable);//打印错误日志
      resultBean = new ResultBean<>(throwable);
    }
    return resultBean;
  }

}
