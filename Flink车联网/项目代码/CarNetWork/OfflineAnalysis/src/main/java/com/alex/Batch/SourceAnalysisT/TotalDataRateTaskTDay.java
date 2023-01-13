package com.alex.Batch.SourceAnalysisT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alex_liu
 * @create 2023-01-13 17:26
 * @Description 按天计算原始数据准确率
 */
public class TotalDataRateTaskTDay {
    private static Logger logger = LoggerFactory.getLogger(TotalDataRateTaskTDay.class);
    public static void main(String[] args) {
//        String day = DateUtil.getYearMonthDayDate();
        String day = "20200314";
        TotalDataRateT totalDataRateT = new TotalDataRateT(day);
        logger.info("DayDataRateTask start ...");
        long time = System.currentTimeMillis();
        totalDataRateT.executeTask();
        logger.info("DayDataRateTask end ,executing time: {}", (System.currentTimeMillis()-time));
    }
}
