package com.alex.Batch.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description: 日期处理工具类
	相比在实时项目中用到的日期处理工具类，添加了获得周一的方法：getNowWeekStart
 */
public class DateUtil {

    private final static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static SimpleDateFormat DATE_KEY_FORMAT = new SimpleDateFormat("yyyyMMdd");

    /**
     * 获取当天日期 格式:yyyy-MM-dd
     * @return 当天日期
     */
    public static String getTodayDate() {
        return TIME_FORMAT.format(new Date());
    }

    /**
     * @desc:格式化时间字符串
     * @param dateStr
     * @return 日期时间
     * @throws ParseException
     */
    public static Date formatStringToDate(String dateStr) throws ParseException {
        return TIME_FORMAT.parse(dateStr);
    }

    /**
     * 格式化日期 date string转yyyyMMdd日期,没有-间隔
     * @param date "yyyy-MM-dd HH:mm:ss"
     * @return yyyyMMdd
     */
    public static String formatKeyDate(String date) throws ParseException {
        return DATE_KEY_FORMAT.format(TIME_FORMAT.parse(date));
    }

    /**
     * 格式化日期 date string转yyyyMMdd日期,没有-间隔
     */
    public static String getYearMonthDayDate() {
        return getTodayDate().substring(0,10).replace("-","");
    }

    /**
     * 格式化日期 date string转yyyyMM日期,没有-间隔
     */
    public static String getYearMonthDate() {
        return getTodayDate().substring(0,7).replace("-","");
    }

    public static String getNowWeekStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        //获取本周一的日期
        return DATE_KEY_FORMAT.format(calendar.getTime());
    }

}