package com.alex.Streaming.Function.FailureAnalysis;

import com.alex.Streaming.Bean.FailureAnalysis.FailureAnalysisDataObj;
import com.alex.Streaming.Utils.DateUtil;
import com.alex.Streaming.Bean.SourceData.VehicleDataPartObj;
import com.google.common.collect.Lists;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * @author Alex_liu
 * @create 2023-01-13 11:19
 * @Description 自定义远程诊断实时故障分析窗口
 *            1.过滤迭代器中没有报警信息的ItcastDataPartObj对象
 *            2.设置充电标识 chargeFlag ，0：未充电 1：充电 2：异常
 *            3.计算geohash值，存入结果第四个字段
 *            4.判断是否有报警信息，分别设置返回对象（20个属性）
 *              4.1 返回对象20个属性分别为：车架号(vin)、纬度、经度、geohash值、里程表读数、
 *                  故障标知(0正常，1故障)、故障名称(多个故障用~分割)、终端时间、最早数据接收时间、
 *                  单体电池最高电压、单体电池最低电压、电池最高温度、电池最低温度、车速、SOC、
 *                  充电标识(0：未充电 1：充电 2：异常)、总电压(单位：V)、总电流(单位(A))、
 *                  单体电池电压列表、电池模块温度列表
 */
public class FailureAnalysisWindowFunction implements WindowFunction<VehicleDataPartObj, FailureAnalysisDataObj,String, TimeWindow> {

    private static Logger logger = LoggerFactory.getLogger(FailureAnalysisWindowFunction.class);
    /**
     *   自定义窗口操作
     * @param key
     * @param timeWindow
     * @param iterable
     * @param collector
     * @throws Exception
     */
    @Override
    public void apply(String key, TimeWindow timeWindow, Iterable<VehicleDataPartObj> iterable, Collector<FailureAnalysisDataObj> collector) throws Exception {
        //将迭代器中的数据转换成本地集合对象
        ArrayList<VehicleDataPartObj> vehicleDataPartObjArrayList = Lists.newArrayList(iterable);
        vehicleDataPartObjArrayList.sort(((o1, o2) -> {
            //如果第一个元素对象的时间戳大于第二个元素的时间戳，升序排序
            if (o1.getTerminalTimeStamp() > o2.getTerminalTimeStamp()) {
                return 1;
            } else if (o1.getTerminalTimeStamp() < o2.getTerminalTimeStamp()) {
                return -1;
            } else {
                return 0;
            }
        }));
        //获取开合中的第一条数据
        VehicleDataPartObj firstVehicleDataPartObj = vehicleDataPartObjArrayList.get(0);
        //循环遍历集合中的每条数据，将集合中存在异常指标的的数据过滤出来进行拼接到指定属性中存储
        //30s一个窗口，意味着每个车辆最多在该窗口中存在6条数据，因此6条中每条数据需要监测19个字段，如果19个字段中存在异常字段则进行字符串的拼接
        for (VehicleDataPartObj vehicleDataPartObj : vehicleDataPartObjArrayList) {
            //监测当前这条数据中是否存在异常的字段
            if (filterNoAlarm(vehicleDataPartObj)) {
                //没有报警字段
                FailureAnalysisDataObj failureDataObj = setFailureAnalysisDataObj(vehicleDataPartObj, firstVehicleDataPartObj, 0);
                logger.info("没有异常指标数据");
                collector.collect(failureDataObj);
            } else {
                FailureAnalysisDataObj failureDataObj = setFailureAnalysisDataObj(vehicleDataPartObj, firstVehicleDataPartObj, 1);
                logger.warn("存在异常指标数据");
                collector.collect(failureDataObj);
            }
        }
    }

    /**
     *  将vehicleDataPartObj转换成FailureAnalysisDataObj对象
     * @param vehicleDataPartObj
     * @param firstVehicleDataPartObj
     * @param isAlarm
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private FailureAnalysisDataObj setFailureAnalysisDataObj(VehicleDataPartObj vehicleDataPartObj, VehicleDataPartObj firstVehicleDataPartObj, int isAlarm) throws InvocationTargetException, IllegalAccessException {
        //定义需要返回的JavaBean对象
        FailureAnalysisDataObj failureAnalysisDataObj = new FailureAnalysisDataObj();
        //将vehicleDataPartObj的属性赋值给failureAnalysisDataObj
        BeanUtils.copyProperties(failureAnalysisDataObj, vehicleDataPartObj);
        failureAnalysisDataObj.setMileage(vehicleDataPartObj.getTotalOdometer());
        failureAnalysisDataObj.setIsAlarm(isAlarm);
        failureAnalysisDataObj.setAlarmName(String.join("~", addAlarmNameList(vehicleDataPartObj)));
        failureAnalysisDataObj.setEarliestTime(firstVehicleDataPartObj.getTerminalTime());
        failureAnalysisDataObj.setChargeFlag(getChargeState(vehicleDataPartObj.getChargeStatus()));
        failureAnalysisDataObj.setProcessTime(DateUtil.getCurrentDateTime());
        return failureAnalysisDataObj;
    }

    /**
     * 根据充电状态返回充电标记
     * @param chargeStatus
     * @return
     */
    private int getChargeState(int chargeStatus) {
        int chargeFlag = -999999;//充电状态的初始值
        //充电状态：0x01: 停车充电、 0x02: 行车充电
        if(chargeStatus == 1 || chargeStatus==2){
            chargeFlag = 1;
        }
        //0x04:充电完成 0x03: 未充电
        else if(chargeStatus == 4 || chargeStatus == 3){
            chargeFlag = 0;
        }else{
            chargeFlag = 2;
        }
        return chargeFlag;
    }

    /**
     *  将每条数据的故障名称追加到故障名称列表中
     * @param vehicleDataPartObj
     * @return
     */
    private ArrayList<String> addAlarmNameList(VehicleDataPartObj vehicleDataPartObj) {
        //定义故障名称列表对象
        ArrayList<String> alarmNameList = new ArrayList<>();
        //电池高温报警
        if(vehicleDataPartObj.getBatteryAlarm() == 1) {
            alarmNameList.add("电池高温报警");
        }
        //单体电池高压报警
        if(vehicleDataPartObj.getSingleBatteryOverVoltageAlarm() == 1) {
            alarmNameList.add("单体电池高压报警");
        }
        //电池单体一致性差报警
        if(vehicleDataPartObj.getBatteryConsistencyDifferenceAlarm() == 1) {
            alarmNameList.add("电池单体一致性差报警");
        }
        //绝缘报警
        if(vehicleDataPartObj.getInsulationAlarm() == 1) {
            alarmNameList.add("绝缘报警");
        }
        //高压互锁状态报警
        if(vehicleDataPartObj.getHighVoltageInterlockStateAlarm() == 1) {
            alarmNameList.add("高压互锁状态报警");
        }
        //SOC跳变报警
        if(vehicleDataPartObj.getSocJumpAlarm() == 1) {
            alarmNameList.add("SOC跳变报警");
        }
        //驱动电机控制器温度报警
        if(vehicleDataPartObj.getDriveMotorControllerTemperatureAlarm() == 1) {
            alarmNameList.add("驱动电机控制器温度报警");
        }
        //DC-DC温度报警（dc-dc可以理解为车辆动力智能系统转换器）
        if(vehicleDataPartObj.getDcdcTemperatureAlarm() == 1) {
            alarmNameList.add("DC-DC温度报警");
        }
        //SOC过高报警
        if(vehicleDataPartObj.getSocHighAlarm() == 1) {
            alarmNameList.add("SOC过高报警");
        }
        //SOC低报警
        if(vehicleDataPartObj.getSocLowAlarm() == 1) {
            alarmNameList.add("SOC低报警");
        }
        //温度差异报警
        if(vehicleDataPartObj.getTemperatureDifferenceAlarm() == 1) {
            alarmNameList.add("温度差异报警");
        }
        //车载储能装置欠压报警
        if(vehicleDataPartObj.getVehicleStorageDeviceUndervoltageAlarm() == 1) {
            alarmNameList.add("车载储能装置欠压报警");
        }
        //DC-DC状态报警
        if(vehicleDataPartObj.getDcdcStatusAlarm() == 1) {
            alarmNameList.add("DC-DC状态报警");
        }
        //单体电池欠压报警
        if(vehicleDataPartObj.getSingleBatteryUnderVoltageAlarm() == 1) {
            alarmNameList.add("单体电池欠压报警");
        }
        //可充电储能系统不匹配报警
        if(vehicleDataPartObj.getRechargeableStorageDeviceMismatchAlarm() == 1) {
            alarmNameList.add("可充电储能系统不匹配报警");
        }
        //车载储能装置过压报警
        if(vehicleDataPartObj.getVehicleStorageDeviceOvervoltageAlarm() == 1) {
            alarmNameList.add("车载储能装置过压报警");
        }
        //制动系统报警
        if(vehicleDataPartObj.getBrakeSystemAlarm() == 1) {
            alarmNameList.add("制动系统报警");
        }
        //驱动电机温度报警
        if(vehicleDataPartObj.getDriveMotorTemperatureAlarm() == 1) {
            alarmNameList.add("驱动电机温度报警");
        }
        //车载储能装置类型过充报警
        if(vehicleDataPartObj.getVehiclePureDeviceTypeOvercharge() == 1) {
            alarmNameList.add("车载储能装置类型过充报警");
        }
        return alarmNameList;
    }

    /**
     *  判断是否存在报警的字段
     * @param vehicleDataPartObj
     * @return
     */
    private boolean filterNoAlarm(VehicleDataPartObj vehicleDataPartObj) {
        //电池高温报警
        if((vehicleDataPartObj.getBatteryAlarm() == 1) ||
                //单体电池高压报警
                vehicleDataPartObj.getSingleBatteryOverVoltageAlarm() == 1 ||
                //电池单体一致性差报警
                vehicleDataPartObj.getBatteryConsistencyDifferenceAlarm() == 1 ||
                //绝缘报警
                vehicleDataPartObj.getInsulationAlarm() == 1 ||
                //高压互锁状态报警
                vehicleDataPartObj.getHighVoltageInterlockStateAlarm() == 1 ||
                //SOC跳变报警
                vehicleDataPartObj.getSocJumpAlarm() == 1 ||
                //驱动电机控制器温度报警
                vehicleDataPartObj.getDriveMotorControllerTemperatureAlarm() == 1 ||
                //DC-DC温度报警（dc-dc可以理解为车辆动力智能系统转换器）
                vehicleDataPartObj.getDcdcTemperatureAlarm() ==1 ||
                //SOC过高报警
                vehicleDataPartObj.getSocHighAlarm() == 1||
                //SOC低报警
                vehicleDataPartObj.getSocLowAlarm() == 1 ||
                //温度差异报警
                vehicleDataPartObj.getTemperatureDifferenceAlarm() == 1||
                //车载储能装置欠压报警
                vehicleDataPartObj.getVehicleStorageDeviceUndervoltageAlarm() == 1||
                //DC-DC状态报警
                vehicleDataPartObj.getDcdcStatusAlarm() == 1||
                //单体电池欠压报警
                vehicleDataPartObj.getSingleBatteryUnderVoltageAlarm() == 1||
                //可充电储能系统不匹配报警
                vehicleDataPartObj.getRechargeableStorageDeviceMismatchAlarm() == 1||
                //车载储能装置过压报警
                vehicleDataPartObj.getVehicleStorageDeviceOvervoltageAlarm() == 1||
                //制动系统报警
                vehicleDataPartObj.getBrakeSystemAlarm() == 1 ||
                //驱动电机温度报警
                vehicleDataPartObj.getDriveMotorTemperatureAlarm() == 1 ||
                //车载储能装置类型过充报警
                vehicleDataPartObj.getVehiclePureDeviceTypeOvercharge() == 1
        )
            return  false;
        else
            return true;
    }

}
