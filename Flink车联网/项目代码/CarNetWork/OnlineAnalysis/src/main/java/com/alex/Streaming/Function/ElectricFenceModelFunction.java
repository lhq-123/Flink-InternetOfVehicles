package com.alex.Streaming.Function;

import com.alex.Streaming.Bean.ElectricFenceModel;
import org.apache.flink.streaming.api.functions.co.CoFlatMapFunction;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;

/**
 * @author Alex_Alex_liu
 * @create 2023-01-12 21:18
 * @Description
 */
public class ElectricFenceModelFunction implements CoFlatMapFunction<ElectricFenceModel, HashMap<String, Long>, ElectricFenceModel> {

    Logger logger = LoggerFactory.getLogger(ElectricFenceModelFunction.class);
    HashMap vehicleInfo = new HashMap<String, Long>();

    /**
     * 电子围栏模型对象数据
     * @param electricFenceModel
     * @param collector
     * @throws Exception
     */
    @Override
    public void flatMap1(ElectricFenceModel electricFenceModel, Collector<ElectricFenceModel> collector) throws Exception {
        if (vehicleInfo.get(electricFenceModel.getVin()) != null) {
            long electricFenceId = Long.parseLong(vehicleInfo.get(electricFenceModel.getVin()).toString());
            electricFenceModel.setUuid(electricFenceId);
            electricFenceModel.setInMysql(true);
        } else {
            electricFenceModel.setUuid(Long.MAX_VALUE - new Date().getTime());
            electricFenceModel.setInMysql(false);
        }
        collector.collect(electricFenceModel);
    }

    /**
     *  电子围栏分析结果数据
     * @param value
     * @param collector
     * @throws Exception
     */
    @Override
    public void flatMap2(HashMap<String, Long> value, Collector<ElectricFenceModel> collector) throws Exception {
        vehicleInfo = value;
    }
}
