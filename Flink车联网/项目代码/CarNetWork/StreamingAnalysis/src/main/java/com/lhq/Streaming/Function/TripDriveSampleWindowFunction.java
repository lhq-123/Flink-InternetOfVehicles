package com.lhq.Streaming.Function;

import com.google.common.collect.Lists;
import com.lhq.Streaming.Utils.VehicleDataObj;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import java.util.ArrayList;

/**
 * @author liu
 * @Create 2022-11-22
 * @Description
 *
 *  实现驾驶行程采样的自定义函数开发
 *  针对驾驶行程（某个车辆15分钟内所有的驾驶行程进行数据的获取及格式化）
 */
public class TripDriveSampleWindowFunction implements WindowFunction<VehicleDataObj, String[], String, TimeWindow> {
    /**
     * 重写apply方法，实现驾驶行程采样逻辑
     * @param key           分流的字段类型
     * @param timeWindow    窗口类型
     * @param iterable      某个车辆15分钟内所有的驾驶行程
     * @param collector     返回数据
     * @throws Exception
     */
    @Override
    public void apply(String key, TimeWindow timeWindow, Iterable<VehicleDataObj> iterable, Collector<String[]> collector) throws Exception {
        //窗口内的数据有水位线，因此需要对窗口内的数据进行排序，否则拼接出来的数据是不准确的
        //todo 1：先将迭代器转换成集合对象
        ArrayList<VehicleDataObj> vehicleDataObjArrayList = Lists.newArrayList(iterable);
        //todo 2：对每一个会话窗口内的元素进行排序操作
        vehicleDataObjArrayList.sort(((o1, o2) -> {
            //如果第一个元素对象的TerminalTimeStamp，大于第二个元素对象的TerminalTimeStamp
            if(o1.getTerminalTimeStamp()> o2.getTerminalTimeStamp()){
                //升序排序，就会交换两个对象的值
                return  1;
            }else if(o1.getTerminalTimeStamp() < o2.getTerminalTimeStamp()){
                return  -1;
            }else{
                return 0;
            }
        }));
        //todo 3：首先获取到排序后的第一条数据
        VehicleDataObj firstVehicleDataObj = vehicleDataObjArrayList.get(0);
        //采样的数据为5秒钟采样一次，采样数据的各个字段对应的属性值使用逗号进行拼接
        //soc:剩余电量百分比
        StringBuffer singleSoc = new StringBuffer(String.valueOf(firstVehicleDataObj.getSoc()));
        //mileage:总里程数
        StringBuffer singleMileage = new StringBuffer(String.valueOf(firstVehicleDataObj.getTotalOdometer()));
        //speed：速度
        StringBuffer singleSpeed = new StringBuffer(String.valueOf(firstVehicleDataObj.getVehicleSpeed()));
        //gps：地理位置
        StringBuffer gps = new StringBuffer(String.valueOf(firstVehicleDataObj.getLng()+"|"+firstVehicleDataObj.getLat()));
        //terminalTime：终端时间
        StringBuffer terminalTime = new StringBuffer(String.valueOf(firstVehicleDataObj.getTerminalTime()));
        //todo 4：获得排序后的最后一条数据
        VehicleDataObj lastVehicleDataObj = vehicleDataObjArrayList.get(vehicleDataObjArrayList.size() - 1);
        //todo 5：获取会话窗口内第一条数据的车辆终端时间
        Long startTime = firstVehicleDataObj.getTerminalTimeStamp();
        //todo 6：获取会话窗口内最后一条数据的车辆终端时间
        Long endTime = lastVehicleDataObj.getTerminalTimeStamp();
        //todo 7：遍历整个窗口内所有的数据
        for(VehicleDataObj VehicleDataObj : vehicleDataObjArrayList) {
            //获取当前数据的终端时间
            Long currentTimestamp = VehicleDataObj.getTerminalTimeStamp();
            //TODO 每5秒钟采样一次数据，采样的内容soc、mileage、speed、gps、terminalTime
            if ((currentTimestamp - startTime) >= 5 * 1000 || currentTimestamp == endTime) {
                singleSoc.append("," + VehicleDataObj.getSoc());
                singleMileage.append("," + VehicleDataObj.getTotalOdometer());
                singleSpeed.append("," + VehicleDataObj.getVehicleSpeed());
                gps.append("," + String.valueOf(VehicleDataObj.getLng() + "|" + VehicleDataObj.getLat()));
                terminalTime.append("," + String.valueOf(firstVehicleDataObj.getTerminalTime()));
            }
            //在list循环的内部，在采样逻辑判断的外部，当下一次采样之前，赋值开始时间为当前时间
            startTime = currentTimestamp;
        }
        //processTime：计算时间，分析结果的当前时间
        String[] result = new String[7];
        result[0] = firstVehicleDataObj.getVin();
        result[1] = String.valueOf(firstVehicleDataObj.getTerminalTimeStamp());
        result[2] = String.valueOf(singleSoc);
        result[3] = String.valueOf(singleMileage);
        result[4] = String.valueOf(singleSpeed);
        result[5] = String.valueOf(gps);
        result[6] = String.valueOf(firstVehicleDataObj.getTerminalTime());

        collector.collect(result);
    }
}