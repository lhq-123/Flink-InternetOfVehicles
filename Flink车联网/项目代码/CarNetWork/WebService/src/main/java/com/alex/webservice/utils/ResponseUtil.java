package com.alex.webservice.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Alex_liu
 * @create 2023-01-14 17:30
 * @Description 响应工具类
 */

public class ResponseUtil {
    private static final String ATTR_NAME_DATA = "data";
    private static final String ATTR_NAME_TOTAL = "total";
    private static final String ATTR_NAME_CODE = "code";
    private static final String ATTR_NAME_MSG = "msg";

    private static final int CODE_SUCCESS = 0;
    private static final int CODE_FAIL = -1;

    public static Map buildSuccessResult(String msg) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put(ATTR_NAME_CODE, CODE_SUCCESS);
        result.put(ATTR_NAME_MSG, msg);
        return result;
    }

    public static Map buildResult(String msg, Object data) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put(ATTR_NAME_CODE, CODE_SUCCESS);
        result.put(ATTR_NAME_MSG, msg);
        result.put(ATTR_NAME_DATA, data);
        return result;
    }

    public static Map buildSuccessResult(long total, String msg, Object data) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put(ATTR_NAME_CODE, CODE_SUCCESS);
        result.put(ATTR_NAME_TOTAL, total);
        result.put(ATTR_NAME_MSG, msg);
        result.put(ATTR_NAME_DATA, data);
        return result;
    }

    public static Map buildFailResult(String msg) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put(ATTR_NAME_CODE, CODE_FAIL);
        result.put(ATTR_NAME_MSG, msg);
        return result;
    }
}
