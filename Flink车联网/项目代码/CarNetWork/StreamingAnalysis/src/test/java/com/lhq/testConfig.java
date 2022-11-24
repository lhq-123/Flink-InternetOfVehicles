package com.lhq;

import com.lhq.Streaming.Utils.ConfigLoader;


/**
 * @author liu
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
