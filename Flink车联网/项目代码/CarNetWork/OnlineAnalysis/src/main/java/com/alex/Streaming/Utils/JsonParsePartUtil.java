package com.alex.Streaming.Utils;

import com.alex.Streaming.Bean.SourceData.VehicleDataPartObj;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @Auther: alex
 * @Date: 2022/12/17 18:13
 * @Description:  定义json字符串解析的工具类
 */
public class JsonParsePartUtil {

    private static Logger logger = LoggerFactory.getLogger("JsonParsePartUtil");

    /**
     * @desc:解析json成为ItcastDataObj对象
     * @param jsonString
     * @return 转换后的VehicleDataObj对象
     */
    public static VehicleDataPartObj parseJsonToObject(String jsonString) {
        VehicleDataPartObj vehicleDataPartObj = new VehicleDataPartObj();
        try {
            HashMap<String, Object> vehicleMap = jsonToMap(jsonString);
            vehicleDataPartObj.setBatteryConsistencyDifferenceAlarm(convertIntType("batteryConsistencyDifferenceAlarm", vehicleMap));
            vehicleDataPartObj.setSoc(convertIntType("soc", vehicleMap));
            vehicleDataPartObj.setSocJumpAlarm(convertIntType("socJumpAlarm", vehicleMap));
            vehicleDataPartObj.setSocLowAlarm(convertIntType("socLowAlarm", vehicleMap));
            vehicleDataPartObj.setTerminalTime(convertStringType("terminalTime", vehicleMap));
            vehicleDataPartObj.setSingleBatteryOverVoltageAlarm(convertIntType("singleBatteryOverVoltageAlarm", vehicleMap));
            vehicleDataPartObj.setVehicleStorageDeviceOvervoltageAlarm(convertIntType("vehicleStorageDeviceOvervoltageAlarm", vehicleMap));
            vehicleDataPartObj.setBrakeSystemAlarm(convertIntType("brakeSystemAlarm", vehicleMap));
            vehicleDataPartObj.setVin(convertStringType("vin", vehicleMap).toUpperCase());
            vehicleDataPartObj.setChargeStatus(convertIntType("chargeStatus", vehicleMap));
            vehicleDataPartObj.setDriveMotorTemperatureAlarm(convertIntType("driveMotorTemperatureAlarm", vehicleMap));
            vehicleDataPartObj.setDcdcStatusAlarm(convertIntType("dcdcStatusAlarm", vehicleMap));
            vehicleDataPartObj.setLat(convertDoubleType("lat", vehicleMap));
            vehicleDataPartObj.setVehicleSpeed(convertDoubleType("vehicleSpeed", vehicleMap));
            vehicleDataPartObj.setLng(Double.parseDouble(convertStringType("lng", vehicleMap)));
            vehicleDataPartObj.setGpsTime(convertStringType("gpsTime", vehicleMap));
            vehicleDataPartObj.setSingleBatteryUnderVoltageAlarm(convertIntType("singleBatteryUnderVoltageAlarm", vehicleMap));
            vehicleDataPartObj.setTotalVoltage(convertDoubleType("totalVoltage", vehicleMap));
            vehicleDataPartObj.setTemperatureDifferenceAlarm(convertIntType("temperatureDifferenceAlarm", vehicleMap));
            vehicleDataPartObj.setMinVoltageBattery(convertDoubleType("minVoltageBattery", vehicleMap));
            vehicleDataPartObj.setValidGps(convertStringType("validGps", vehicleMap));
            vehicleDataPartObj.setTotalOdometer(convertDoubleType("totalOdometer", vehicleMap));
            vehicleDataPartObj.setSpeed(convertDoubleType("speed", vehicleMap));
            vehicleDataPartObj.setSocHighAlarm(convertIntType("socHighAlarm", vehicleMap));
            vehicleDataPartObj.setVehicleStorageDeviceUndervoltageAlarm(convertIntType("vehicleStorageDeviceUndervoltageAlarm", vehicleMap));
            vehicleDataPartObj.setTotalCurrent(convertDoubleType("totalCurrent", vehicleMap));
            vehicleDataPartObj.setBatteryAlarm(convertIntType("batteryAlarm", vehicleMap));
            vehicleDataPartObj.setBattPoleTOver(convertStringType("battPoleTOver", vehicleMap));
            vehicleDataPartObj.setAcSystemFailure(convertIntType("acSystemFailure", vehicleMap));
            vehicleDataPartObj.setAirBagWarningLamp(convertIntType("airBagWarningLamp", vehicleMap));
            vehicleDataPartObj.setBatteryChargeStatus(convertIntType("batteryChargeStatus", vehicleMap));
            vehicleDataPartObj.setChargingGunConnectionState(convertIntType("chargingGunConnectionState", vehicleMap));
            vehicleDataPartObj.setRechargeableStorageDeviceMismatchAlarm(convertIntType("rechargeableStorageDeviceMismatchAlarm", vehicleMap));
            vehicleDataPartObj.setVehiclePureDeviceTypeOvercharge(convertIntType("vehiclePureDeviceTypeOvercharge", vehicleMap));
            vehicleDataPartObj.setMaxVoltageBattery(convertDoubleType("maxVoltageBattery", vehicleMap));
            vehicleDataPartObj.setDcdcTemperatureAlarm(convertIntType("dcdcTemperatureAlarm", vehicleMap));
            vehicleDataPartObj.setIsValidGps(convertStringType("isValidGps", vehicleMap));
            vehicleDataPartObj.setDriveMotorControllerTemperatureAlarm(convertIntType("driveMotorControllerTemperatureAlarm", vehicleMap));
            vehicleDataPartObj.setRepay(convertStringType("repay", vehicleMap));
            vehicleDataPartObj.setEnergyRecoveryStatus(convertIntType("energyRecoveryStatus", vehicleMap));
            vehicleDataPartObj.setFireStatus(convertIntType("fireStatus", vehicleMap));
            vehicleDataPartObj.setCarMode(convertIntType("carMode", vehicleMap));
            vehicleDataPartObj.setHighVoltageInterlockStateAlarm(convertIntType("highVoltageInterlockStateAlarm", vehicleMap));
            vehicleDataPartObj.setInsulationAlarm(convertIntType("insulationAlarm", vehicleMap));
            vehicleDataPartObj.setMileageInformation(convertIntType("mileageInformation", vehicleMap));
            vehicleDataPartObj.setRemainPower(convertDoubleType("remainPower", vehicleMap));
            vehicleDataPartObj.setEngineStatus(convertStringType("engineStatus", vehicleMap));
            vehicleDataPartObj.setAbsStatus(convertStringType("absStatus", vehicleMap));
            vehicleDataPartObj.setVcuBrkErr(convertStringType("VcuBrkErr", vehicleMap));
            vehicleDataPartObj.setESP_TCSFailStatus(convertStringType("ESP_TCSFailStatus", vehicleMap));
            vehicleDataPartObj.setEPS_EPSFailed(convertStringType("EPS_EPSFailed", vehicleMap));
            vehicleDataPartObj.setMaxTemperatureValue(convertDoubleType("maxTemperatureValue", vehicleMap));
            vehicleDataPartObj.setMinTemperatureValue(convertDoubleType("minTemperatureValue", vehicleMap));

            /* ------------------------------------------nevChargeSystemVoltageDtoList 可充电储能子系统电压信息列表-------------------------------------- */
            List<Map<String, Object>> nevChargeSystemVoltageDtoList = jsonToList(vehicleMap.getOrDefault("nevChargeSystemVoltageDtoList", new ArrayList<Object>()).toString());
            if (!nevChargeSystemVoltageDtoList.isEmpty()) {
                // 只取list中的第一个map集合
                Map<String, Object> nevChargeSystemVoltageDtoMap = nevChargeSystemVoltageDtoList.get(0);
                vehicleDataPartObj.setBatteryVoltage(convertJoinStringType("batteryVoltage", nevChargeSystemVoltageDtoMap));
                vehicleDataPartObj.setChargeSystemVoltage(convertDoubleType("chargeSystemVoltage",nevChargeSystemVoltageDtoMap));
                vehicleDataPartObj.setChargeSystemCurrent(convertDoubleType("chargeSystemCurrent", nevChargeSystemVoltageDtoMap));
            }
            /* -------------------------------------------------------------------------------------------------------------- */

            /* -----------------------------------------nevChargeSystemTemperatureDtoList------------------------------------ */
            List<Map<String, Object>> nevChargeSystemTemperatureDtoList = jsonToList(vehicleMap.getOrDefault("nevChargeSystemTemperatureDtoList", new ArrayList()).toString());
            if (!nevChargeSystemTemperatureDtoList.isEmpty()) {
                Map<String, Object> nevChargeSystemTemperatureMap = nevChargeSystemTemperatureDtoList.get(0);
                vehicleDataPartObj.setProbeTemperatures(convertJoinStringType("probeTemperatures", nevChargeSystemTemperatureMap));
            }
            /* -------------------------------------------------------------------------------------------------------------- */

            /* --------------------------------------------ecuErrCodeDataList------------------------------------------------ */
            Map<String, Object> xcuerrinfoMap = jsonToMap(vehicleMap.getOrDefault("xcuerrinfo", new HashMap<String, Object>()).toString());
            if (!xcuerrinfoMap.isEmpty()) {
                List<Map<String, Object>> ecuErrCodeDataList = jsonToList(xcuerrinfoMap.getOrDefault("ecuErrCodeDataList", new ArrayList()).toString()) ;
                if (ecuErrCodeDataList.size() > 4) {
                    vehicleDataPartObj.setVcuFaultCode(convertJoinStringType("errCodes", ecuErrCodeDataList.get(0)));
                    vehicleDataPartObj.setBcuFaultCodes(convertJoinStringType("errCodes", ecuErrCodeDataList.get(1)));
                    vehicleDataPartObj.setDcdcFaultCode(convertJoinStringType("errCodes", ecuErrCodeDataList.get(2)));
                    vehicleDataPartObj.setIpuFaultCodes(convertJoinStringType("errCodes", ecuErrCodeDataList.get(3)));
                    vehicleDataPartObj.setObcFaultCode(convertJoinStringType("errCodes", ecuErrCodeDataList.get(4)));
                }
            }
            /* -------------------------------------------------------------------------------------------------------------- */

            // carStatus不在有效范围，设置值为255
            if (convertStringType("carStatus", vehicleMap).length() > 3) {
                vehicleDataPartObj.setCarStatus(255);
            } else {
                vehicleDataPartObj.setCarStatus(convertIntType("carStatus", vehicleMap));
            }
            // terminalTime字段不为空，设置标记时间为terminalTime时间
            if(!vehicleDataPartObj.getTerminalTime().isEmpty()){
                vehicleDataPartObj.setTerminalTimeStamp(DateUtil.convertStringToDateTime(vehicleDataPartObj.getTerminalTime()).getTime());
                // 终端时间延迟五个小时，设置为默认值
                if(vehicleDataPartObj.getTerminalTimeStamp() > (System.currentTimeMillis() + 1000 * 60 * 5)){
                    vehicleDataPartObj.setTerminalTimeStamp(-999999L);
                }
            }
        } catch (Exception e){
            vehicleDataPartObj.setErrorData(jsonString);
            logger.error("json 数据格式错误...", e);
        }
        // 如果没有VIN号和终端时间，则为无效数据
        if(vehicleDataPartObj.getVin().isEmpty() || vehicleDataPartObj.getTerminalTime().isEmpty() || vehicleDataPartObj.getTerminalTimeStamp() < 1){
            if(vehicleDataPartObj.getVin().isEmpty()){
                logger.error("vin.isEmpty");
            }
            if(vehicleDataPartObj.getTerminalTime().isEmpty()){
                logger.error("terminalTime.isEmpty");
            }
            vehicleDataPartObj.setErrorData(jsonString);
        }
        return vehicleDataPartObj;
    }

    /**
     * @desc:将Json对象转换成Map
     * @param jsonString
     * @return Map对象
     * @throws JSONException
     */
    public static HashMap jsonToMap(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        HashMap result = new HashMap();
        Iterator iterator = jsonObject.keys();
        String key = null;
        Object value = null;
        while (iterator.hasNext()) {
            key = (String) iterator.next();
            value = jsonObject.get(key);
            result.put(key, value);
        }
        return result;
    }

    /**
     * @desc:将数组转换为List，数组内部的json字符串转换为map
     * @param jsonString
     * @return List<Map<String, Object>>
     * @throws JSONException
     */
    public static List<Map<String, Object>> jsonToList(String jsonString) throws JSONException {
        List<Map<String, Object>> resultList = new ArrayList();
        JSONArray jsonArray = new JSONArray(jsonString);
        for (int i = 0; i < jsonArray.length(); i++) {
            HashMap map =jsonToMap(jsonArray.get(i).toString());
            resultList.add(map);
        }
        return resultList;
    }

    /**
     * 提取类型重复转换代码
     * @param fieldName
     * @param map
     * @return 对应数据类型的值
     */
    private static int convertIntType(String fieldName,Map map) {
        return Integer.parseInt(map.getOrDefault(fieldName, -999999).toString());
    }
    private static String convertStringType(String fieldName,Map map) {
        return map.getOrDefault(fieldName, "").toString();
    }
    private static double convertDoubleType(String fieldName,Map map) {
        return Double.parseDouble(map.getOrDefault(fieldName, -999999).toString());
    }
    private static String convertJoinStringType(String fieldName, Map map) {
        return String.join("~", convertStringToArray(map.getOrDefault(fieldName, new ArrayList()).toString()));
    }

    /**
     * 解决类型转换异常：string转数组,字符串以","分割
     * @param str
     * @return list
     */
    private static List convertStringToArray(String str) {
        return Arrays.asList(str.split(","));
    }

}