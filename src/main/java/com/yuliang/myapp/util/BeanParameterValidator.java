package com.yuliang.myapp.util;

import com.google.common.base.Strings;


import com.yuliang.myapp.exception.BusinessException;

import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by yuliangjin (530367387@qq.com) on 17/4/5.
 *
 * @description A bean auto validation util.
 */
public class BeanParameterValidator {

  private static final String ANNOTATION_NAME = "javax.validation.constraints.NotNull";

  public static final String NULL_REQUESTPARAM = "1003"; // 必须参数有空值


  /**
   * 验证T类型的bean t的添加了@NotNull注解的Field是否满足一下条件：
   * 1. not null
   * 2. 如果是String类型，是否为空字符串
   * 3. 如果是Collection，是否包含元素
   * @param t
   * @param <T>
   */
  public static <T> void check(T t) throws BusinessException {
    Field[] declaredFields = t.getClass().getDeclaredFields();
    for (Field field : declaredFields) {
      Annotation[] annotations = field.getAnnotations();
      for (Annotation annotation : annotations) {
        Object object = executeGetter(field, t);
        if (ANNOTATION_NAME.equalsIgnoreCase(annotation.annotationType().getName())) {
          notNullOrEmpty(object, field);
        }
      }
    }
  }

  /**
   * 深度参数检查：
   * 1. 对于对象Field，检查对象不为null、并递归检查对象的各个Field；
   * 2. 对于Collection Field，检查collection有值，并递归检查collection的每个元素的各个Field。
   * @param t
   * @param <T>
   * @throws BusinessException
   */
  public static <T> void checkDeep(T t) throws BusinessException {
    Field[] declaredFields = t.getClass().getDeclaredFields();
    for (Field field : declaredFields) {
      Annotation[] annotations = field.getAnnotations();
      for (Annotation annotation : annotations) {
        Object object = executeGetter(field, t);
        if (ANNOTATION_NAME.equalsIgnoreCase(annotation.annotationType().getName())) {
          notNullOrEmptyDeep(object, field);
        }
      }
    }
  }


  /**
   * 返回一个bean的所有@NotNull信息，不抛出异常
   * @param t
   * @param <T>
   * @return
   */
  public static <T> List<String> getParamErrors(T t) {
    List<String> errorMsgs = new ArrayList<>();
    Field[] declaredFields = t.getClass().getDeclaredFields();
    for (Field field : declaredFields) {
      Annotation[] annotations = field.getAnnotations();
      for (Annotation annotation : annotations) {
        Object object = executeGetter(field, t);
        if (ANNOTATION_NAME.equalsIgnoreCase(annotation.annotationType().getName())) {
          String errorMsg = checkError(object, field);
          if (!(Strings.isNullOrEmpty(errorMsg))) {
            errorMsgs.add(errorMsg);
          }
        }
      }
    }
    return errorMsgs;
  }

  public static <T> List<String> getParamErrorsDeep(T t, List<String> errorMsgs) {
    if (errorMsgs == null) {
      errorMsgs = new ArrayList<>();
    }
    Field[] declaredFields = t.getClass().getDeclaredFields();
    for (Field field : declaredFields) {
      Annotation[] annotations = field.getAnnotations();
      for (Annotation annotation : annotations) {
        Object object = executeGetter(field, t);
        if (ANNOTATION_NAME.equalsIgnoreCase(annotation.annotationType().getName())) {
          errorMsgs = checkErrorDeep(object, field, errorMsgs);
        }
      }
    }
    return errorMsgs;
  }

  private static List<String> checkErrorDeep(Object object, Field field, final List<String> errorMsgs) {

    List<String> errors = errorMsgs;
    if (object == null) {
      errors.add(String.format("Field '%s' should not be null.", field.getName()));
    }

    if(object instanceof String && ((String) object).isEmpty()) {
      errors.add(String.format("Field '%s' is required.", field.getName()));
    }

    //对collection variable的errorMsg获取处理
    if(object instanceof Collection) {
      Collection object1 = (Collection) object;
      if(object1.isEmpty()) {
        errors.add(String.format("Field '%s' as a collection, has no values.", field.getName()));
      } else {
        for (Object o : object1) {
          return getParamErrorsDeep(o, errors);
        }
      }
    }

    return errors;
  }

  private static String checkError(Object object, Field field) {


    String errorMsg = "";
    if (object == null) {
      errorMsg = String.format("Field '%s' should not be null.", field.getName());
    }

    if(object instanceof String && ((String) object).isEmpty()) {
      errorMsg = String.format("Field '%s' is required.", field.getName());
    }

    if(object instanceof Collection && ((Collection) object).isEmpty()) {
      errorMsg = String.format("Field '%s' as a collection, has no values.", field.getName());
    }

    return errorMsg;
  }

  private static void notNullOrEmpty(Object object, Field field) throws BusinessException {
    if (object == null) {
      throw new BusinessException(NULL_REQUESTPARAM, String.format("Field '%s' should not be null.", field.getName()));
    }

    if(object instanceof String && ((String) object).isEmpty()) {
      throw new BusinessException(NULL_REQUESTPARAM, String.format("Field '%s' is required.", field.getName()));
    }

    if(object instanceof Collection && ((Collection) object).isEmpty()) {
      throw new BusinessException(NULL_REQUESTPARAM, String.format("Field '%s' as a collection, has no values.", field.getName()));
    }

    return;
  }

  /**
   * 深度判断
   * @param object
   * @param field
   * @throws BusinessException
   */
  private static void notNullOrEmptyDeep(Object object, Field field) throws BusinessException {
    if (object == null) {
      throw new BusinessException(NULL_REQUESTPARAM, String.format("Field '%s' should not be null.", field.getName()));
    }

    if(object instanceof String && ((String) object).isEmpty()) {
      throw new BusinessException(NULL_REQUESTPARAM, String.format("Field '%s' is required.", field.getName()));
    }

    if(object instanceof Collection) {
      Collection object1 = (Collection) object;
      if(object1.isEmpty()) {
        throw new BusinessException(NULL_REQUESTPARAM, String.format("Field '%s' as a collection, has no values.", field.getName()));
      } else {
        for (Object o : object1) {
          checkDeep(o);
        }
      }
    }

    checkDeep(object);
    return;
  }

  /**
   * 执行泛型M的field对应的get方法
   */

  public static <M> Object executeGetter(Field field, M m) {
    try {
      try {
        return getReadMethod(field, m).invoke(m);
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return null;
  }

  private static <M> Method getReadMethod(Field field, M m) {
    PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(m.getClass(), field.getName());
    return propertyDescriptor.getReadMethod();
  }
  private static <M> Method getTargetGetMethod(Field field, M m) {
    for (Method method : m.getClass().getMethods()) {
      if ((method.getName().startsWith("get")) &&
          (method.getName().length() == (field.getName().length() + 3)) &&
          method.getName().toLowerCase().endsWith(field.getName().toLowerCase())) {
        return method;
      }
    }
    return null;
  }
}
