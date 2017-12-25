package com.yuliang.myapp.repository;

import com.yuliang.myapp.domain.Student;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by yuliangjin (530367387@qq.com) on 17/12/22.
 *
 * @description
 */
public interface StudentRepository extends JpaRepository<Student, Long> {

  @Override
  List<Student> findAll();
}
