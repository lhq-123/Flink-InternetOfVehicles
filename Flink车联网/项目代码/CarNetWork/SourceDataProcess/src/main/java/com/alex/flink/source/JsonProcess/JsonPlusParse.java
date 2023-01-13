package com.alex.flink.source.JsonProcess;

import com.alex.flink.source.JsonProcess.Bean.VehicleBeanPlus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liu
 * @Create 2022-11-05
 * @Description 解析复杂Json
 */

public class JsonPlusParse {
    public static void main(String[] args) {
        // 1.创建JSON字符串
        String json = "{\"batteryAlarm\": 0,\"carMode\": 1,\"minVoltageBattery\": 3.89,\"chargeStatus\": 1,\"vin\": \"LS5A3CJC0JF890971\",\"nevChargeSystemTemperatureDtoList\": [{\"probeTemperatures\": [25, 23, 24, 21, 24, 21, 23, 21, 23, 21, 24, 21, 24, 21, 25, 21],\"chargeTemperatureProbeNum\": 16,\"childSystemNum\": 1}]}";
        // 2.创建一个pojo类
        JSONObject jsonObject = new JSONObject(json);
        //初始化List
        List<Integer> probeTempList = new ArrayList<>();
        // 3.使用org.json解析JSON字符串,将JSON字符串解析的key:value封装到对象中
        int batteryAlarm = jsonObject.getInt("batteryAlarm");
        int carMode = jsonObject.getInt("carMode");
        double minVoltageBattery = jsonObject.getDouble("minVoltageBattery");
        int chargeStatus = jsonObject.getInt("chargeStatus");
        String vin = jsonObject.getString("vin");
        //获取探针温度的数组列表
        JSONArray nevChargeSystemTempDtoList = jsonObject.getJSONArray("nevChargeSystemTemperatureDtoList");
        //获取第一个数组中的值
        String firstArrJson = nevChargeSystemTempDtoList.get(0).toString();
        //将JSON字符串转换成对象
        JSONObject nevChargeSysTempObj = new JSONObject(firstArrJson);
        //将值取出来
        int chargeTemperatureProbeNum = nevChargeSysTempObj.getInt("chargeTemperatureProbeNum");
        int childSystemNum = nevChargeSysTempObj.getInt("childSystemNum");
        //将探针列表值取出来,并封装对List
        JSONArray probeTempArr = nevChargeSysTempObj.getJSONArray("probeTemperatures");
        for (Object pt : probeTempArr) {
            probeTempList.add(Integer.parseInt(pt.toString()));
        }

        VehicleBeanPlus vehicleBeanPlus = new VehicleBeanPlus(
            batteryAlarm,
            carMode,
            minVoltageBattery,
            chargeStatus,
            vin,
            probeTempList,
            chargeTemperatureProbeNum,
            childSystemNum
        );
        System.out.println(vehicleBeanPlus.toString());
    }
}
