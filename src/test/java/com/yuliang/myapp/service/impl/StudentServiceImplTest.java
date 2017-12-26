package com.yuliang.myapp.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import com.yuliang.myapp.domain.Student;
import com.yuliang.myapp.repository.StudentRepository;
import com.yuliang.myapp.service.InnerService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuliangjin (530367387@qq.com) on 17/12/25.
 *
 * @description student service test cases
 */
public class StudentServiceImplTest {

  private static String STUDENT_PASSPORT_NUMBER = "E12340987";

  @Mock
  private StudentRepository studentRepository;

  @Mock
  private RestTemplate restTemplate;

//  @Spy
  @Mock
  private InnerService innerService;

  @InjectMocks
  private StudentServiceImpl studentService;

  @Before
  public void before() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void methodForTest() {

    Student student = new Student();
    student.setId(25L);
    student.setName("jack");
    student.setPassportNumber(STUDENT_PASSPORT_NUMBER);

    List<Student> students = new ArrayList<>();
    students.add(student);

    //Mock settings
    when(restTemplate.getForObject(anyString(), any())).thenReturn(student);
    when(studentRepository.findAll()).thenReturn(null);
    when(studentRepository.findByPassportNumber(anyString())).thenReturn(new ArrayList<Student>());
    when(studentRepository.save(student)).thenReturn(student);

    Student studentResult = studentService.methodForTest(STUDENT_PASSPORT_NUMBER);
    Assert.assertEquals(STUDENT_PASSPORT_NUMBER, studentResult.getPassportNumber());
    Mockito.verify(studentRepository, times(0)).findAll();
    Mockito.verify(studentRepository, times(1)).findByPassportNumber(anyString());
    Mockito.verify(studentRepository, times(1)).save(student);
    Mockito.verify(restTemplate, times(1)).getForObject(anyString(), any());
    Mockito.verify(innerService, times(0)).checkStudent(student);
  }

  @Test
  public void methodForTest1() {

    Student student = new Student();
    student.setId(25L);
    student.setName("jack");
    student.setPassportNumber(STUDENT_PASSPORT_NUMBER);

    Student student1 = new Student();
    student.setId(2L);
    student.setName("jackie");
    student.setPassportNumber(STUDENT_PASSPORT_NUMBER);
    List<Student> existingStudents = new ArrayList<>();
    existingStudents.add(student1);

    List<Student> students = new ArrayList<>();
    students.add(student);

    //Mock settings
    when(restTemplate.getForObject(anyString(), any())).thenReturn(student);
    when(studentRepository.findAll()).thenReturn(existingStudents);
    when(studentRepository.findByPassportNumber(anyString())).thenReturn(existingStudents);
    when(studentRepository.save(student)).thenReturn(student);
    when(innerService.checkStudent(student)).thenReturn(true);

    Student studentResult = studentService.methodForTest(STUDENT_PASSPORT_NUMBER);
    Assert.assertEquals(STUDENT_PASSPORT_NUMBER, studentResult.getPassportNumber());
    Mockito.verify(studentRepository, times(0)).findAll();
    Mockito.verify(studentRepository, times(1)).findByPassportNumber(anyString());
    Mockito.verify(studentRepository, times(0)).save(student);
    Mockito.verify(restTemplate, times(1)).getForObject(anyString(), any());
    Mockito.verify(innerService, times(1)).checkStudent(student);
    Mockito.verify(innerService, times(1)).sendMessage(student);
  }
}
