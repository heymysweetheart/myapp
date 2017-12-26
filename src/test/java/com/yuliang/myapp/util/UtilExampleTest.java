package com.yuliang.myapp.util;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yuliangjin (530367387@qq.com) on 17/12/26.
 *
 * @description
 */
public class UtilExampleTest {

  @Test
  public void test() throws IOException {
    List<String> strings = UtilExample.readFile2List("/Users/leo/Downloads/myapp/src/test/java/com/yuliang/myapp/util/data.txt");
    Assert.assertEquals(3, strings.size());
    Assert.assertEquals("## This is a test data file.", strings.get(0));
  }

  @Test
  public void testIsEmptyString() {
    Assert.assertTrue(UtilExample.isEmpty(""));
    Assert.assertFalse(UtilExample.isEmpty("____"));
    Assert.assertFalse(UtilExample.isEmpty("    "));
  }

  @Test
  public void testCollection() {
    ArrayList<String> strings = new ArrayList<>();
    Assert.assertEquals(true, UtilExample.isEmptyCollection(strings));

    LinkedList<String> strings1 = new LinkedList<>();
    Assert.assertTrue(UtilExample.isEmptyCollection(strings1));
    strings1.add("test string");
    Assert.assertFalse(UtilExample.isEmptyCollection(strings1));
  }
}
