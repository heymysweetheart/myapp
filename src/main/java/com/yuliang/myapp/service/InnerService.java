package com.yuliang.myapp.service;

import com.yuliang.myapp.domain.Student;

/**
 * Created by yuliangjin (530367387@qq.com) on 17/12/26.
 *
 * @description An inner service for example to show how to use @Spy or @Mock
 */
public interface InnerService {

  /**
   * method with some actually logic which could be mocked or spied when test the consumer logic
   * @param student
   * @return
   */
  boolean checkStudent(Student student);

  /**
   * A method with void return type
   * @param student
   */
  void sendMessage(Student student);
}
