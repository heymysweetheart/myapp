package com.yuliang.myapp.job;

/**
 * Created by yuliangjin (530367387@qq.com) on 18/1/10.
 *
 * @description
 */
public enum WorkerState {

  RUNNING(0),
  SUCCESS(1),
  EXCEPTION(1<<2);
  private int value;

  WorkerState(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
