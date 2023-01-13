package com.alex.Streaming.Function;

import com.alex.Streaming.Bean.FailureAnalysisDataObj;
import com.alex.Streaming.Bean.FailureAnalysisLocationInfo;
import com.alex.Streaming.Utils.GeoHashUtil;
import com.alex.Streaming.Utils.RedisUtil;
import com.alibaba.fastjson.JSON;
import org.apache.flink.api.common.functions.MapFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alex_liu
 * @create 2023-01-13 13:23
 * @Description 自定义flatMap函数，将窗口流数据与车辆基础表的数据进行关联，关联后的结果返回
 */
public class FailureAnalysisVehicleInfoRedisFunction  implements MapFunction<FailureAnalysisDataObj, FailureAnalysisDataObj> {
    private static Logger logger = LoggerFactory.getLogger(FailureAnalysisVehicleInfoRedisFunction.class);

    @Override
    public FailureAnalysisDataObj map(FailureAnalysisDataObj failureAnalysisDataObj) throws Exception {
        //根据经度和维度获取到geoHash（可以将GeoHash作为redis的key进行存储）
        String geoHash = GeoHashUtil.encode(failureAnalysisDataObj.getLat(), failureAnalysisDataObj.getLng());
        byte[] locationInfo = RedisUtil.get(geoHash.getBytes());
        if(locationInfo!=null) {
            logger.info("locationInfo.toString():" + new String(locationInfo));
            FailureAnalysisLocationInfo FailureAnalysisVehicleLocationInfo = JSON.parseObject(new String(locationInfo), FailureAnalysisLocationInfo.class);

            if (FailureAnalysisVehicleLocationInfo != null) {
                failureAnalysisDataObj.setProvince(FailureAnalysisVehicleLocationInfo.getProvince());
                failureAnalysisDataObj.setCity(FailureAnalysisVehicleLocationInfo.getCity());
                failureAnalysisDataObj.setCounty(FailureAnalysisVehicleLocationInfo.getCountry());
            } else {
                failureAnalysisDataObj.setProvince(null);
                failureAnalysisDataObj.setCity(null);
                failureAnalysisDataObj.setCounty(null);
                logger.info("根据GeoHash没有获取到对应的省份、城市和地区，geoHash：" + geoHash);
            }
        }
        //返回数据
        return failureAnalysisDataObj;
    }
}
