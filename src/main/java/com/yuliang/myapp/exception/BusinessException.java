package com.yuliang.myapp.exception;

import lombok.Data;

/**
 * Created by yuliangjin (530367387@qq.com) on 17/9/8.
 *
 * @description 系统的业务异常
 */
@Data
public class BusinessException extends RuntimeException {

  private String code; //错误码

  private String message; //错误信息

  public BusinessException(String code, String message) {
    super(message);
    this.code = code;
    this.message = message;
  }

  public BusinessException(ExceptionCode exceptionCode) {
    super(exceptionCode.getMessage());
    this.code = exceptionCode.getCode();
    this.message = exceptionCode.getMessage();
  }

  public BusinessException(String message, String code, String message1) {
    super(message);
    this.code = code;
    this.message = message1;
  }

  public BusinessException(String message, Throwable cause, String code, String message1) {
    super(message, cause);
    this.code = code;
    this.message = message1;
  }

  public BusinessException(Throwable cause, String code, String message) {
    super(cause);
    this.code = code;
    this.message = message;
  }
}
