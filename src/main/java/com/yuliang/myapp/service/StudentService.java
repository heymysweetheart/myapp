package com.yuliang.myapp.service;

import com.yuliang.myapp.domain.Student;

import java.util.List;

/**
 * Created by yuliangjin (530367387@qq.com) on 17/12/22.
 *
 * @description
 */
public interface StudentService {

  public List<Student> getAllStudent();

  Student add(String name, String passportNumber);

  /**
   * I add some logic in this method to mimic the real cases concerning:
   *  IO operations: like a http request, access a database
   *  Computing operations: loops, conditions operations, etc
   *  so that I can give an example to show how to code and write proper unit test cases.
   * @param passportNumber
   * @return
   */
  Student methodForTest(String passportNumber);
}
