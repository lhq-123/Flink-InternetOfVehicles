package com.alex.Streaming.Function.FailureAnalysis;

import com.alex.Streaming.Bean.FailureAnalysis.FailureAnalysisDataObj;
import com.alex.Streaming.Bean.FailureAnalysis.FailureAnalysisModel;
import org.apache.flink.streaming.api.functions.co.CoFlatMapFunction;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * @author Alex_liu
 * @create 2023-01-13 13:02
 * @Description 自定义在线故障分析的窗口数据与mysql数据库数据的关联
 *         基于窗口处理后20个属性对象结添加了5个属性，分别为：车系(series_name)、车型(model_name)、年限(liveTime)、
 *         销售日期(product_date)、用途(car_type)
 */
public class FailureAnalysisVehicleMapMysqlFunction implements CoFlatMapFunction<FailureAnalysisDataObj, HashMap<String, FailureAnalysisModel>,
        FailureAnalysisDataObj> {
    private static Logger logger = LoggerFactory.getLogger(FailureAnalysisVehicleMapMysqlFunction.class);
    //定义车辆相关信息Map对象
    HashMap<String, FailureAnalysisModel> failureAnalysisModelVehicleInfoMap = new HashMap<String, FailureAnalysisModel>();

    /**
     * 针对第一个数据集的操作：窗口流数据
     * @param failureAnalysisDataObj
     * @param collector
     * @throws Exception
     */
    @Override
    public void flatMap1(FailureAnalysisDataObj failureAnalysisDataObj, Collector<FailureAnalysisDataObj> collector) throws Exception {
        //根据车架号找到对应的车辆信息
        FailureAnalysisModel failureAnalysisModelVehicleInfo = failureAnalysisModelVehicleInfoMap.get(failureAnalysisDataObj.getVin());
        if(failureAnalysisModelVehicleInfo!=null){
            //车系
            failureAnalysisDataObj.setSeriesName(failureAnalysisModelVehicleInfo.getSeriesName());
            //车型
            failureAnalysisDataObj.setModelName(failureAnalysisModelVehicleInfo.getModelName());
            //年限(单位:月，未查到数据显示-1)
            failureAnalysisDataObj.setLiveTime(failureAnalysisModelVehicleInfo.getLiveTime());
            //销售日期
            failureAnalysisDataObj.setSalesDate(failureAnalysisModelVehicleInfo.getSalesDate());
            //车辆类型
            failureAnalysisDataObj.setCarType(failureAnalysisModelVehicleInfo.getCarType());

            //返回数据
            collector.collect(failureAnalysisDataObj);
        }else{
            logger.info("没有找到匹配的车辆数据，vin："+failureAnalysisDataObj.getVin());
        }

    }

    /**
     * 针对第二个数据集的操作：mysql的车辆广播流数据
     * @param inFailureAnalysisModelVehicleInfoMap
     * @param collector
     * @throws Exception
     */
    @Override
    public void flatMap2(HashMap<String, FailureAnalysisModel> inFailureAnalysisModelVehicleInfoMap, Collector<FailureAnalysisDataObj> collector) throws Exception {
        this.failureAnalysisModelVehicleInfoMap = inFailureAnalysisModelVehicleInfoMap;
    }
}
