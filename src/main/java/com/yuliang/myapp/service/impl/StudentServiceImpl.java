package com.yuliang.myapp.service.impl;

import com.google.common.base.Strings;

import com.yuliang.myapp.domain.Student;
import com.yuliang.myapp.exception.BusinessException;
import com.yuliang.myapp.exception.ExceptionCode;
import com.yuliang.myapp.repository.StudentRepository;
import com.yuliang.myapp.service.StudentService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yuliangjin (530367387@qq.com) on 17/12/22.
 *
 * @description
 */
@Service
@Slf4j
public class StudentServiceImpl implements StudentService{

  @Autowired
  private StudentRepository studentRepository;

  @Override
  public List<Student> getAllStudent() {
    log.info("Get all Students...");
    return studentRepository.findAll();
  }

  @Override
  public Student add(String name, String passportNumber) {
    if(Strings.isNullOrEmpty(passportNumber)) throw new BusinessException(ExceptionCode.PASSPORT_EMPTY_ERROR);
    Student student = new Student();
    student.setName(name);
    student.setPassportNumber(passportNumber);
    return studentRepository.save(student);
  }
}
