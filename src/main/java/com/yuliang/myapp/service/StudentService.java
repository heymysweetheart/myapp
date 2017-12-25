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
}
