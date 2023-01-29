package com.alex.function;

import org.apache.spark.sql.api.java.UDF1;

/**
 * @author Alex_liu
 * @create 2023-01-29 15:57
 * @Description
 */
public class RemoveRandomPrefixUDF implements UDF1<String, String> {

    private static final long serialVersionUID = 1L;
    @Override
    public String call(String value) throws Exception {
        return value.split("_")[1];
    }
}
