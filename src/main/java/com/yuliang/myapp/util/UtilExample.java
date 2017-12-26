package com.yuliang.myapp.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 靳玉亮 (yuliangjin@creditease.cn) on 17/12/26.
 *
 * @description String util as examples of how to add self defined utils.
 */
public class StringUtil {
  public static List<String> readFile2List(String fileName) throws IOException{
    return readFile2List(fileName, "UTF-8");
  }

  public static List<String> readFile2List(String fileName, String charSet) throws IOException {
    return readFile2List(new File(fileName), charSet);
  }

  public static List<String> readFile2List(File fileName, String charSet) throws IOException{
    return readFile2List(new FileInputStream(fileName), charSet);
  }

  public static List<String> readFile2List(FileInputStream fileInputStream, String charSet) throws IOException {
    ArrayList<String> strings = new ArrayList<>();
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, charSet));

    String temp;
    while ( (temp = bufferedReader.readLine()) != null) {
      strings.add(temp);
    }
    return strings;
  }


}
