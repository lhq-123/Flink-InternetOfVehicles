package com.alex.flink.source.JsonProcess;

import com.alex.flink.source.JsonProcess.Bean.VehicleBean;
import org.json.JSONObject;

/**
 * @author liu
 * @Create 2022-11-05
 * @Description 解析简单Json
 */

/*{
        "batteryAlarm":0,
        "carMode":1,
        "minVoltageBattery":3.89,
        "chargeStatus":1,
        "vin":"LS5A3CJC0JF890971"
        }*/
public class JsonParse {
    public static void main(String[] args) {
    // 1.创建JSON字符串
        String json = "{\"batteryAlarm\": 0, \"carMode\": 1,\"minVoltageBattery\": 3.89, \"chargeStatus\": 1,\"vin\":\"LS5A3CJC0JF890971\"}";
    // 2.创建一个pojo类
        JSONObject jsonObject = new JSONObject(json);
    // 3.使用org.json解析JSON字符串,将JSON字符串解析的key:value封装到对象中
        VehicleBean vehicleBean = new VehicleBean(
                jsonObject.getInt("batteryAlarm"),
                jsonObject.getInt("carMode"),
                jsonObject.getDouble("minVoltageBattery"),
                jsonObject.getInt("chargeStatus"),
                jsonObject.getString("vin")
        );
    // 4.打印输出测试
        System.out.println(vehicleBean.toString());
    }
}
