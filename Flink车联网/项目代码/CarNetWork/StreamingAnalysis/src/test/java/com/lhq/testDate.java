package com.lhq;

import static com.lhq.Streaming.Utils.DateUtil.*;

/**
 * @author liu
 * @Create 2022-11-19
 * @Description
 */
public class testDate {
    public static void main(String[] args) {
        System.out.println(getCurrentDateTime());
        System.out.println(getCurrentDate());
        System.out.println(convertStringToDate("20210301"));
        System.out.println(convertStringToDateTime("2021-03-01 10:21:32"));
        System.out.println(convertStringToDateString("2021-03-01 10:21:32"));
    }
}
