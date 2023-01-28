package com.alex.utils;

import java.math.BigDecimal;

/**
 * @author Alex_liu
 * @create 2023-01-28 21:48
 * @Description 数字格工具类
 */
public class NumberUtil {
    public static double formatDouble(double num, int scale){
        BigDecimal bd = new BigDecimal(num);
        return bd.setScale(scale, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }
}
