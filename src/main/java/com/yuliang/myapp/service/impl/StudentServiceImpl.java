package com.yuliang.myapp.service.impl;

import com.google.common.base.Strings;

import com.yuliang.myapp.domain.Student;
import com.yuliang.myapp.exception.BusinessException;
import com.yuliang.myapp.exception.ExceptionCode;
import com.yuliang.myapp.repository.StudentRepository;
import com.yuliang.myapp.service.InnerService;
import com.yuliang.myapp.service.StudentService;
import com.yuliang.myapp.util.UtilExample;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by yuliangjin (530367387@qq.com) on 17/12/22.
 *
 * @description
 */
@Service
@Slf4j
public class StudentServiceImpl implements StudentService{

  private static String STUDENT_SERVER_RUL = "http://gturnquist-quoters.cfapps.io/api/random";

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private InnerService innerService;

  @Autowired
  private RestTemplate restTemplate;

  @Override
  public List<Student> getAllStudent() {
    log.info("Get all Students...");
    List<Student> students = studentRepository.findAll();
    for (Student student : students) {
      if (student.getId() > 1000) {
        student.setPassportNumber(student.getName() + student.getPassportNumber());
      }
    }
    return students;
  }

  @Override
  public Student add(String name, String passportNumber) {
    if(Strings.isNullOrEmpty(passportNumber)) throw new BusinessException(ExceptionCode.PASSPORT_EMPTY_ERROR);
    Student student = new Student();
    student.setName(name);
    student.setPassportNumber(passportNumber);
    return studentRepository.save(student);
  }

  /**
   * Logic:
   * 1. get a student object from another application through http request.
   * 2. search the local database for Student with same passportNumber
   * @param passportNumber
   * @return
   */
  @Override
  public Student methodForTest(String passportNumber) {
    //This url actually will not return a Student object, this is only for unit test example. It doesn't matter in
    // real cases, because you will find the write web service which will return the right data.
    Student studentFromServer = restTemplate.getForObject(STUDENT_SERVER_RUL + passportNumber, Student.class);
    List<Student> localStudents = studentRepository.findByPassportNumber(passportNumber);

    //Some complex computations to mimic the real world logic
    //Call a static method that need to be mocker properly in real cases
    if (studentFromServer != null && !UtilExample.isEmpty(studentFromServer.getName())) {
      if (localStudents.size() < 1) {
        studentRepository.save(studentFromServer);
      } else {//Call another service to check student status
        boolean isValidStudent = innerService.checkStudent(studentFromServer);
        if (isValidStudent) {
          innerService.sendMessage(studentFromServer);
        } else {
          log.info("Student form server is not valid");
        }
      }

      return studentFromServer;

    } else {
      if(localStudents.size() > 0) return localStudents.get(0);
    }
    return null;
  }
}
