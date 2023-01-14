package com.alex.Streaming.Bean.SourceData;

/**
 * @author Alex_liu
 * @Create 2022-11-19
 * @Description  定义时间格式的枚举类
 *
 *  1：yyyy-MM-dd HH:mm:ss
 *  2：yyyyMMdd
 *  3：yyyy-MM-dd
 */
public enum DateFormatDefine {

    //通过构造方法的方式为常量进行赋值

    //定义时间格式的常量
    DATE_TIME_FORMAT("yyyy-MM-dd HH:mm:ss"),
    DATE_FORMAT("yyyyMMdd"),
    DATE2_FORMAT("yyyy-MM-dd");

    //定义变量接收常量的参数
    private String format;
    /**
     * 定义构造方法，目的是为了给常量进行赋值
     * @param format
     */
    DateFormatDefine(String format) {
        this.format = format;
    }

    /**
     * 返回常量定义的参数
     * @return
     */
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}

