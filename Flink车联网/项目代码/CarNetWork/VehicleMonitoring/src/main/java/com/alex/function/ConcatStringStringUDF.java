package com.alex.function;

import org.apache.spark.sql.api.java.UDF3;

/**
 * @author Alex_liu
 * @create 2023-01-29 15:53
 * @Description 将两个字段拼接起来(使用指定的分隔符)
 *                海淀区:建材城西路
 */
public class ConcatStringStringUDF implements UDF3<String, String, String, String> {
    @Override
    public String call(String area_name, String road_id, String split) throws Exception {
        return area_name + split + road_id;
    }
}
