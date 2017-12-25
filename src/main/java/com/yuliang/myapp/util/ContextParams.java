package com.yuliang.myapp.util;

import java.util.Map;

/**
 * Created by yuliangjin (530367387@qq.com) on 17/9/11.
 *
 * @description 客户端访问本服务的参数封装
 */
public class ContextParams {
    public static final ThreadLocal<String> clientIp = new ThreadLocal<>();
    public static final ThreadLocal<String> uri = new ThreadLocal<>();
    public static final ThreadLocal<String> token = new ThreadLocal<>();
    public static final ThreadLocal<Map<String, String>> paramMap = new ThreadLocal<>();
    public static final ThreadLocal<String> userId = new ThreadLocal<>();
}
