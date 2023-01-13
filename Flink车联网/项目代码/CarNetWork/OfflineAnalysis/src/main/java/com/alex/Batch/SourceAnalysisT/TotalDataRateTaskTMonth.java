package com.alex.Batch.SourceAnalysisT;

import com.alex.Batch.Util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alex_liu
 * @create 2023-01-13 17:35
 * @Description 按月计算数据准确率
 */
public class TotalDataRateTaskTMonth {
    private static Logger logger = LoggerFactory.getLogger(TotalDataRateTaskTMonth.class);

    public static void main(String[] args) {
        String month = DateUtil.getYearMonthDate() + "00";
        TotalDataRateT totalDataRateT = new TotalDataRateT(month);
        logger.info("MonthDataRateTask start ...");
        Long time = System.currentTimeMillis();
        totalDataRateT.executeTask();
        logger.info("MonthDataRateTask end ,executing time: {}", (System.currentTimeMillis()-time));
    }
}
