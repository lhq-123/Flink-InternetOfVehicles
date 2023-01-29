package com.alex.function;

import org.apache.kafka.common.protocol.types.Field;
import org.apache.spark.sql.api.java.UDF2;

import java.util.Random;

/**
 * @author Alex_liu
 * @create 2023-01-29 15:55
 * @Description
 */
public class RandomPrefixUDF implements UDF2<String, Integer, String> {

    private static final long serialVersionUID = 1L;
    @Override
    public String call(String area_name_road_id, Integer ranNum) throws Exception {
        Random random = new Random();
        int prefix = random.nextInt(ranNum);
        return prefix+"_"+area_name_road_id;
    }
}
