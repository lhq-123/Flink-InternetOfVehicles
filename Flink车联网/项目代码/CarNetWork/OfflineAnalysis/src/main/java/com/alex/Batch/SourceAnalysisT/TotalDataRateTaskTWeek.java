package com.alex.Batch.SourceAnalysisT;

import com.alex.Batch.Util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alex_liu
 * @create 2023-01-13 17:32
 * @Description 按周计算每周的数据准确率（从周一开始）
 */
public class TotalDataRateTaskTWeek {
    private static Logger logger = LoggerFactory.getLogger(TotalDataRateTaskTWeek.class);

    public static void main(String[] args) {
        String week = DateUtil.getNowWeekStart();
        //String week = "20200314";
        TotalDataRateT totalDataRateT = new TotalDataRateT(week);
        logger.info("WeekDataRateTask start ...");
        long time = System.currentTimeMillis();
        totalDataRateT.executeTask();
        logger.info("WeekDataRateTask end ,executing time: {}", (System.currentTimeMillis()-time));
    }
}
