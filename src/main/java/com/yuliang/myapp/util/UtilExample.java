package com.yuliang.myapp.util;

import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by yuliangjin (530367387@qq.com) on 17/12/26.
 *
 * @description Util as examples of how to add self defined utils.
 */
public class UtilExample {

  private static String DEFAULT_CHARSET = "UTF-8";

  public static boolean isEmpty(CharSequence sequence) {//Use parent class or interface as much as possible
    return StringUtils.isEmpty(sequence);
  }

  public static boolean isEmptyCollection(Collection<?> collection) {//Use Collection instead ArrayList, List etc
    return collection == null || collection.size() == 0;
  }

  public static List<String> readFile2List(String fileName) throws IOException{
    return readFile2List(fileName, DEFAULT_CHARSET);
  }

  public static List<String> readFile2List(File file) throws IOException {
    return readFile2List(file, DEFAULT_CHARSET);
  }

  public static List<String> readFile2List(String fileName, String charSet) throws IOException {
    return readFile2List(new File(fileName), charSet);
  }

  public static List<String> readFile2List(File fileName, String charSet) throws IOException{
    return readFile2List(new FileInputStream(fileName), charSet);
  }

  public static List<String> readFile2List(InputStream inputStream) throws IOException {
    return readFile2List(inputStream, DEFAULT_CHARSET);
  }

  public static List<String> readFile2List(InputStream fileInputStream, String charSet) throws IOException {
    ArrayList<String> strings = new ArrayList<>();
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, charSet));
    String temp;
    while ( (temp = bufferedReader.readLine()) != null) {
      strings.add(temp);
    }
    return strings;
  }
}
