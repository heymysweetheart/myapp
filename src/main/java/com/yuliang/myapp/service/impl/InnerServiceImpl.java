package com.yuliang.myapp.service.impl;

import com.yuliang.myapp.domain.Student;
import com.yuliang.myapp.service.InnerService;

import org.springframework.stereotype.Service;

/**
 * Created by yuliangjin (530367387@qq.com) on 17/12/26.
 *
 * @description Mimic real project cases for unit test.
 */
@Service
public class InnerServiceImpl implements InnerService{

  @Override
  public boolean checkStudent(Student student) {
    if (student.getId() > 100 || student.getName() == student.getPassportNumber()) {
      return false;
    } else {
      return true;
    }
  }

  @Override
  public void sendMessage(Student student) {
    //send message logic
  }
}
