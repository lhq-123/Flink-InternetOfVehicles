package com.lhq.Streaming.Phoenix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author liu
 * @Create 2022-11-20
 * @Description
 *    调用flink工具类初始化task作业
 */

public class TripDivisionPhoenixAnalysis {
    private static Logger logger = LoggerFactory.getLogger(TripDivisionPhoenixAnalysis.class);

    public static void main(String[] args) {
        try {
            // 驾驶行程分析指标1
            tripDivisionAnalysis1();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @desc 驾驶行程里程、soc、行程消耗时间分析
     * @throws SQLException
     */
    private static void tripDivisionAnalysis1() throws SQLException {
        tripDivisionAnalysis("select \"rowNum\",\"mileage\",\"soc_comsuption\",\"time_comsuption\" from TRIPDB.\"trip_division\"", "驾驶行程里程-soc-行程消耗时间分析" , 1);
    }

    /**
     * @desc 驾驶行程划分共用逻辑处理代码
     * @param sql
     * @param divisionName
     * @throws SQLException
     */
    private static void tripDivisionAnalysis(String sql, String divisionName, float analyzeType) throws SQLException {
        List<DivisionAnalysis> divisionResultList = sqlToTripDivisionEntity(sql, divisionName, analyzeType);
        logger.warn("phoenix查询，分析的指标名称:{}, hbase on sql:{}", divisionName, sql);
        String insertSql = "insert into vehicle_networking.t_division_result(vin, name, analyze_value1, analyze_value2, analyze_value3, analyze_type, terminalTime, processTime) VALUES (?,?,?,?,?,?,?,?)";
        List<ArrayList<Object>> paramList = new ArrayList<>();
        divisionResultList.forEach(divisionResult -> {
            ArrayList<Object> tempList = new ArrayList<>();
            tempList.add(0, divisionResult.getVin());
            tempList.add(1, divisionResult.getName());
            tempList.add(2, divisionResult.getAnalyzeValue1());
            tempList.add(3, divisionResult.getAnalyzeValue2());
            tempList.add(4, divisionResult.getAnalyzeValue3());
            tempList.add(5, divisionResult.getAnalyzeType());
            tempList.add(6, divisionResult.getTerminalTime());
            tempList.add(7, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            paramList.add(tempList);
        });
        JDBCUtil.executeBatch(insertSql, paramList);
        logger.warn("插入数据到行程划分结果表中成功,sql:{}", insertSql);
    }

    /**
     * @desc 把车速分析sql结果转换为行程划分结果对象集
     * @return  List<DivisionAnalysis>
     * @throws SQLException
     */
    private static List<DivisionAnalysis> sqlToTripDivisionEntity(String sql, String divisionName, float analyzeType) throws SQLException {
        List<String[]> list = PhoenixJDBCUtil.select(sql);
        List<DivisionAnalysis> resultList = new ArrayList<>();
        DivisionAnalysis divisionResult = null;
        for (String[] results : list) {
            // rowNum、mileage、soc_comsuption、time_comsuption
            String rowNum = results[0];
            String mileage = results[1];
            String socComsuption = results[2];
            String timeComsuption = results[3];
            String[] split = rowNum.split("_");
            String vin = split[0];
            Date terminalTimeTemp = new Date(Long.MAX_VALUE - Long.parseLong(split[1]));
            String terminalTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(terminalTimeTemp);

            divisionResult = new DivisionAnalysis(vin ,divisionName, mileage, socComsuption, timeComsuption, analyzeType, terminalTime);
            resultList.add(divisionResult);
        }
        return resultList;
    }
}