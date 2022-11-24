package cn.lhq.flink.source.JsonProcess;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author liu
 * @Create 2022-11-05
 * @Description 将json字符串转换成对象，将key-value转换成HashMap
 *              将JSON字符串进行封装
 */
public class JsonPlusParseOptimize {
    public static void main(String[] args) {
        // 1.定义JSON字符串
        String json = "{\"batteryAlarm\": 0,\"carMode\": 1,\"minVoltageBattery\": 3.89,\"chargeStatus\": 1,\"vin\": \"LS5A3CJC0JF890971\",\"nevChargeSystemTemperatureDtoList\": [{\"probeTemperatures\": [25, 23, 24, 21, 24, 21, 23, 21, 23, 21, 24, 21, 24, 21, 25, 21],\"chargeTemperatureProbeNum\": 16,\"childSystemNum\": 1}]}";
        // 2.将JSONObject 字符串转换成HashMap
        JSONObject jsonObject = new JSONObject(json);
        HashMap<String,Object> vehicleHashMap = toHashMap(jsonObject);
        // 3.将JSON转换成list<HashMap<String,Object>>
        String nevChargeSystemTemperatureDtoList = vehicleHashMap.get("nevChargeSystemTemperatureDtoList").toString();
        List<HashMap<String,Object>> lists = toList(nevChargeSystemTemperatureDtoList);
        // 4.解析JSON字符串
        // 5.转换成对象
        Integer.parseInt(vehicleHashMap.getOrDefault("batteryAlarm",-9999999).toString());
        for (HashMap<String, Object> list : lists) {
            System.out.println(list.getOrDefault("chargeTemperatureProbeNum",-9999).toString());
        }
    }

    /**
     * 将JSON字符串转换成list hashmap
     * @param value
     * @return
     */

    private static List<HashMap<String, Object>> toList(String value) {
        List<HashMap<String,Object>> lists = new ArrayList<>();
        JSONArray objects = new JSONArray(value);
        // 遍历数组，取出所有的对象转换成HaspMap
        for (Object object : objects) {
            String s = object.toString();
            JSONObject jsonObject = new JSONObject(s);
            lists.add(toHashMap(jsonObject));
        }
        return lists;
    }


    /**
     *
     * 将JSONObject 转换成HashMap 对象
     * @param jsonObject
     * @return
     */
    private static HashMap<String, Object> toHashMap(JSONObject jsonObject) {
        HashMap<String,Object> kv = new HashMap<String,Object>();
        // 读取JSONObject 所有key
        Set<String> keys = jsonObject.keySet();
        // 遍历所有kv，得到所有值
        for (String key : keys) {
            kv.put(key,jsonObject.get(key));

        }
        //将其保存到KV
        return kv;
    }
}
