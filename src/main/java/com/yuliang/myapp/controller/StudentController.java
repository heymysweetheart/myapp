package com.yuliang.myapp.controller;

import com.yuliang.myapp.bean.ResultBean;
import com.yuliang.myapp.domain.Student;
import com.yuliang.myapp.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by yuliangjin (530367387@qq.com) on 17/12/22.
 *
 * @description
 */
@Controller
public class StudentController {

  @Autowired
  private StudentService studentService;

  @GetMapping("/students")
  @ResponseBody
  public ResultBean<List<Student>> getStudents() {
    return new ResultBean<>(studentService.getAllStudent());
  }

  @GetMapping("/student")
  @ResponseBody
  public ResultBean<Student> addStudent(String name, String passportNumber) {
    return new ResultBean<>(studentService.add(name, passportNumber));
  }
}
