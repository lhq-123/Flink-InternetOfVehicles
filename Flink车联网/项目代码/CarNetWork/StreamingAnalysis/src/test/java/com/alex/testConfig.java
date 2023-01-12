package com.alex;

import com.alex.Streaming.Utils.ConfigLoader;


/**
 * @author Alex_liu
 * @Create 2022-11-19
 * @Description
 */
public class testConfig {
    public static void main(String[] args) {
        String key = "hdfsUri";
        String value = ConfigLoader.get(key);
        System.out.println(value);
    }

}
