package com.alex.Streaming.Function;

import com.alex.Streaming.Bean.ElectricFenceModel;
import com.alex.Streaming.Bean.ElectricFenceResultTmp;
import com.alex.Streaming.Utils.DateUtil;
import com.alex.Streaming.Utils.DistanceCalculateUtil;
import com.alex.Streaming.Utils.VehicleDataPartObj;
import org.apache.flink.streaming.api.functions.co.CoFlatMapFunction;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;



/**
 * @author Alex_Alex_liu
 * @create 2023-01-12 19:59
 * @Description 电子围栏中自定义对象实现CoFlatMap函数
 *      自定义coFlatMapFunction的函数对象，实现原始车辆和电子围栏广播流数据的合并，返回电子围栏规则模型流数据
 */
public class ElectricFenceRulesFunction implements CoFlatMapFunction<VehicleDataPartObj, HashMap<String, ElectricFenceResultTmp>,ElectricFenceModel> {

    private static Logger logger = LoggerFactory.getLogger(ElectricFenceRulesFunction.class);
    //定义电子围栏广播流数据的对象
    HashMap<String, ElectricFenceResultTmp> vehicleInfoMap = new HashMap<>();

    /**
     * 作用于vehicleJsonDataStream流的flatmap操作
     * @param vehicleDataPartObj
     * @param collector
     * @throws Exception
     */
    @Override
    public void flatMap1(VehicleDataPartObj vehicleDataPartObj, Collector<ElectricFenceModel> collector) throws Exception {
        //判断经度、维度、gpsTime判断，是否为空，如不为空才可以进行数据处理
        if(vehicleDataPartObj.getLng() != 0 && vehicleDataPartObj.getLat() != 0 && vehicleDataPartObj.getLng() != -999999.0 && vehicleDataPartObj.getLat() != -999999.0
                && !vehicleDataPartObj.getGpsTime().isEmpty()){
            logger.info("验证通过, dataPartObj："+vehicleDataPartObj);
            ElectricFenceModel fenceModel = new ElectricFenceModel();
            //根据车架id得到该车架id对应的电子围栏规则
            ElectricFenceResultTmp electricFenceResultTmp = vehicleInfoMap.get(vehicleDataPartObj.getVin());
            logger.info("electricFenceResultTmp:"+electricFenceResultTmp);
            logger.info("vehicleInfoMap:"+vehicleInfoMap);

            //如果当前车辆是被监控的车辆
            if(electricFenceResultTmp!=null){
                //获取gpsTime（位置时间）判断是否在电子围栏的有效时间内
                long eventTime = DateUtil.convertStringToDateTime(vehicleDataPartObj.getGpsTime()).getTime();
                logger.info("eventTime:"+eventTime+", EndTime:"+electricFenceResultTmp.getEndTime().getTime()+", StartTime:"+electricFenceResultTmp.getStartTime());
                if(eventTime< electricFenceResultTmp.getEndTime().getTime() && eventTime > electricFenceResultTmp.getStartTime().getTime()){
                    //车辆的位置时间在电子围栏的有效时间内
                    fenceModel.setVin(vehicleDataPartObj.getVin());
                    fenceModel.setGpsTime(vehicleDataPartObj.getGpsTime());
                    //车辆的位置经度
                    fenceModel.setLng(vehicleDataPartObj.getLng());
                    //车辆的维度
                    fenceModel.setLat(vehicleDataPartObj.getLat());
                    //车辆终端时间
                    fenceModel.setTerminalTime(vehicleDataPartObj.getTerminalTime());
                    fenceModel.setTerminalTimestamp(vehicleDataPartObj.getTerminalTimeStamp());
                    //电子围栏id
                    fenceModel.setEleId(electricFenceResultTmp.getId());
                    //电子围栏名称
                    fenceModel.setEleName(electricFenceResultTmp.getName());
                    //电子围栏地址
                    fenceModel.setAddress(electricFenceResultTmp.getAddress());
                    //电子围栏半径
                    fenceModel.setRadius(electricFenceResultTmp.getRadius());
                    //电子围栏经度
                    fenceModel.setLongitude(electricFenceResultTmp.getLongitude());
                    //电子围栏的维度
                    fenceModel.setLatitude(electricFenceResultTmp.getLatitude());
                    //计算车辆位置和电子围栏中心点的距离
                    Double distance = DistanceCalculateUtil.getDistance(
                            electricFenceResultTmp.getLatitude(), electricFenceResultTmp.getLongitude(),
                            vehicleDataPartObj.getLat(), vehicleDataPartObj.getLng());
                    //判断当前车辆是否在电子围栏范围内，半径单位是：km
                    if(distance/ 1000 < electricFenceResultTmp.getRadius()){
                        //在电子围栏以内
                        fenceModel.setNowStatus(0);
                    }else{
                        //在电子围栏以外
                        fenceModel.setNowStatus(1);
                    }
                    logger.info("关联成功，fenceModel："+fenceModel);
                    //返回数据
                    collector.collect(fenceModel);
                }  else{
                    logger.info("电子围栏规则有效时间不在原始数据的位置时间内，eventTime："+eventTime+"，electricFenceResultTmp："+electricFenceResultTmp);
                }
            } else{
                logger.info("没有获取到匹配的车辆电子围栏配置信息，vin："+vehicleDataPartObj.getVin()+"，ElectricFenceResultTmp：" + electricFenceResultTmp);
            }
        }
        else{
            logger.error("验证失败，dataPartObj："+vehicleDataPartObj);
        }
    }

    @Override
    public void flatMap2(HashMap<String, ElectricFenceResultTmp> stringElectricFenceResultTmpHashMap, Collector<ElectricFenceModel> collector) throws Exception {

    }
}
