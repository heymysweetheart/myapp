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
import org.mockito.Spy;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

  //This dependency tended to be tested for real.
  @Spy
  private InnerService innerService = new InnerServiceImpl();

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

    Student studentResult = studentService.methodForTest(STUDENT_PASSPORT_NUMBER);
    Assert.assertEquals(STUDENT_PASSPORT_NUMBER, studentResult.getPassportNumber());
    Mockito.verify(studentRepository, times(0)).findAll();
    Mockito.verify(studentRepository, times(1)).findByPassportNumber(anyString());
    Mockito.verify(studentRepository, times(0)).save(student);
    Mockito.verify(restTemplate, times(1)).getForObject(anyString(), any());
    Mockito.verify(innerService, times(1)).checkStudent(student);
    Mockito.verify(innerService, times(1)).sendMessage(student);
  }

  /**
   * We use java reflection to test the extracted private method which don't have outer service dependencies.
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   * @throws IllegalAccessException
   */
  @Test
  public void testProcessData() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Method processData = StudentServiceImpl.class.getDeclaredMethod("processData", Student.class, List.class);
    processData.setAccessible(true);

    Student student = new Student();
    student.setId(25L);
    student.setName("jack");

    List<Student> students = new ArrayList<>();
    students.add(student);

    Student invoke = (Student) processData.invoke(studentService, student, students);
    Mockito.verify(studentRepository, times(0)).save(student);
    Mockito.verify(innerService, times(1)).checkStudent(student);
    Mockito.verify(innerService, times(1)).sendMessage(student);
  }

  /**
   * Cover one branch
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   * @throws IllegalAccessException
   */
  @Test
  public void testProcessData1() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Method processData = StudentServiceImpl.class.getDeclaredMethod("processData", Student.class, List.class);
    processData.setAccessible(true);

    Student student = new Student();
    student.setId(25L);
    student.setName("jack");

    List<Student> students = new ArrayList<>();

    when(studentRepository.save(student)).thenReturn(student);

    Student invoke = (Student) processData.invoke(studentService, student, students);
    Mockito.verify(studentRepository, times(1)).save(student);
    Mockito.verify(innerService, times(0)).checkStudent(student);
    Mockito.verify(innerService, times(0)).sendMessage(student);
  }

  /**
   * Cover one branch
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   * @throws IllegalAccessException
   */
  @Test
  public void testProcessData2() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Method processData = StudentServiceImpl.class.getDeclaredMethod("processData", Student.class, List.class);
    processData.setAccessible(true);

    Student student = new Student();
    student.setId(25L);
    student.setName("jack");

    List<Student> students = new ArrayList<>();

    when(studentRepository.save(student)).thenReturn(student);

    Student invoke = (Student) processData.invoke(studentService, null, students);
    Mockito.verify(studentRepository, times(0)).save(student);
    Mockito.verify(innerService, times(0)).checkStudent(student);
    Mockito.verify(innerService, times(0)).sendMessage(student);
    Assert.assertNull(invoke);
  }

  /**
   * Cover one branch.
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   * @throws IllegalAccessException
   */
  @Test
  public void testProcessData3() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Method processData = StudentServiceImpl.class.getDeclaredMethod("processData", Student.class, List.class);
    processData.setAccessible(true);

    Student student = new Student();
    student.setId(25L);
    student.setName("jack");

    List<Student> students = new ArrayList<>();
    students.add(student);

    when(studentRepository.save(student)).thenReturn(student);

    Student invoke = (Student) processData.invoke(studentService, null, students);
    Mockito.verify(studentRepository, times(0)).save(student);
    Mockito.verify(innerService, times(0)).checkStudent(student);
    Mockito.verify(innerService, times(0)).sendMessage(student);
    Assert.assertNotNull(invoke);
    Assert.assertEquals("jack", invoke.getName());
    Assert.assertEquals(25l, invoke.getId().longValue());
  }

  /**
   * After testing the computation method, it's easy to test the outer method once and cover all the branches. This case
   * here we don't need to focus much on the complex computation logic.
   */
  @Test
  public void methodEasyToTest() {

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

    Student studentResult = studentService.methodEasyToTest(STUDENT_PASSPORT_NUMBER);
    Assert.assertEquals(STUDENT_PASSPORT_NUMBER, studentResult.getPassportNumber());
    Mockito.verify(studentRepository, times(0)).findAll();
    Mockito.verify(studentRepository, times(1)).findByPassportNumber(anyString());
    Mockito.verify(studentRepository, times(1)).save(student);
    Mockito.verify(restTemplate, times(1)).getForObject(anyString(), any());
    Mockito.verify(innerService, times(0)).checkStudent(student);
  }
}
