package com.yuliang.myapp.exception;

/**
 * Created by yuliangjin (530367387@qq.com) on 17/7/6.
 *
 * @description: 业务异常枚举
 */

public enum ExceptionCode {

  PASSPORT_EMPTY_ERROR("1012", "护照号不能为空");

  private String code;
  private String message;

  ExceptionCode(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
