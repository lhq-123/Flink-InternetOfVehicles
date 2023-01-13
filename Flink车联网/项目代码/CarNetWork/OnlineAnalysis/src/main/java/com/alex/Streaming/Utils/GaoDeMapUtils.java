package com.alex.Streaming.Utils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Alex_liu
 * @create 2023-01-13 13:29
 * @Description 定义高德地图api工具类
 */
public class GaoDeMapUtils {
     //高德地图请求秘钥
    private static final String KEY = "5cdf4ecd315292bbce3bc65fd228ce61";
     //返回值类型
    private static final String OUTPUT = "JSON";
     //根据地名获取高德经纬度
    private static final String GET_LNG_LAT_URL = "http://restapi.amap.com/v3/geocode/geo";
     //根据高德经纬度获取地名
    private static final String GET_ADDRESS_URL = "http://restapi.amap.com/v3/geocode/regeo";

    /**
     * 根据高德经纬度获取地址信息
     * @param gdLon 高德地图经度
     * @param gdLat 高德地图纬度
     * @return
     */
    public static String getUrlByLonLat(double gdLon, double gdLat) {
        String location = gdLon + "," + gdLat;
        Map<String, String> params = new HashMap<>();
        params.put("location", location);

        try {
            // 拼装url
            String url = jointUrl(params, OUTPUT, KEY, GET_ADDRESS_URL);
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 拼接请求字符串
     *
     * @param params
     * @param output
     * @param key
     * @param url
     * @return
     * @throws IOException
     */
    private static String jointUrl(Map<String, String> params, String output, String key, String url) throws IOException {
        StringBuilder baseUrl = new StringBuilder();
        baseUrl.append(url);

        int index = 0;
        Set<Map.Entry<String, String>> entrys = params.entrySet();
        for (Map.Entry<String, String> param : entrys) {
            // 判断是否是第一个参数
            if (index == 0) {
                baseUrl.append("?");
            } else {
                baseUrl.append("&");
            }
            baseUrl.append(param.getKey()).append("=").append(URLEncoder.encode(param.getValue(), "utf-8"));
            index++;
        }
        baseUrl.append("&output=").append(output).append("&key=").append(key);
        return baseUrl.toString();
    }
}
