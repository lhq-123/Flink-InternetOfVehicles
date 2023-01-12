package com.alex.Streaming.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @Auther: alex
 * @Date: 2022/12/17 18:13
 * @Description:
 */
public class JsonParsePartUtil {

    private static Logger logger = LoggerFactory.getLogger("JsonParsePartUtil");

    /**
     * @desc:解析json成为ItcastDataObj对象
     * @param jsonString
     * @return 转换后的ItcastDataObj对象
     */
    public static VehicleDataPartObj parseJsonToObject(String jsonString) {
        VehicleDataPartObj itcastPartObj = new VehicleDataPartObj();
        try {
            HashMap<String, Object> vehicleMap = jsonToMap(jsonString);
            itcastPartObj.setBatteryConsistencyDifferenceAlarm(convertIntType("batteryConsistencyDifferenceAlarm", vehicleMap));
            itcastPartObj.setSoc(convertIntType("soc", vehicleMap));
            itcastPartObj.setSocJumpAlarm(convertIntType("socJumpAlarm", vehicleMap));
            itcastPartObj.setSocLowAlarm(convertIntType("socLowAlarm", vehicleMap));
            itcastPartObj.setTerminalTime(convertStringType("terminalTime", vehicleMap));
            itcastPartObj.setSingleBatteryOverVoltageAlarm(convertIntType("singleBatteryOverVoltageAlarm", vehicleMap));
            itcastPartObj.setVehicleStorageDeviceOvervoltageAlarm(convertIntType("vehicleStorageDeviceOvervoltageAlarm", vehicleMap));
            itcastPartObj.setBrakeSystemAlarm(convertIntType("brakeSystemAlarm", vehicleMap));
            itcastPartObj.setVin(convertStringType("vin", vehicleMap).toUpperCase());
            itcastPartObj.setChargeStatus(convertIntType("chargeStatus", vehicleMap));
            itcastPartObj.setDriveMotorTemperatureAlarm(convertIntType("driveMotorTemperatureAlarm", vehicleMap));
            itcastPartObj.setDcdcStatusAlarm(convertIntType("dcdcStatusAlarm", vehicleMap));
            itcastPartObj.setLat(convertDoubleType("lat", vehicleMap));
            itcastPartObj.setVehicleSpeed(convertDoubleType("vehicleSpeed", vehicleMap));
            itcastPartObj.setLng(Double.parseDouble(convertStringType("lng", vehicleMap)));
            itcastPartObj.setGpsTime(convertStringType("gpsTime", vehicleMap));
            itcastPartObj.setSingleBatteryUnderVoltageAlarm(convertIntType("singleBatteryUnderVoltageAlarm", vehicleMap));
            itcastPartObj.setTotalVoltage(convertDoubleType("totalVoltage", vehicleMap));
            itcastPartObj.setTemperatureDifferenceAlarm(convertIntType("temperatureDifferenceAlarm", vehicleMap));
            itcastPartObj.setMinVoltageBattery(convertDoubleType("minVoltageBattery", vehicleMap));
            itcastPartObj.setValidGps(convertStringType("validGps", vehicleMap));
            itcastPartObj.setTotalOdometer(convertDoubleType("totalOdometer", vehicleMap));
            itcastPartObj.setSpeed(convertDoubleType("speed", vehicleMap));
            itcastPartObj.setSocHighAlarm(convertIntType("socHighAlarm", vehicleMap));
            itcastPartObj.setVehicleStorageDeviceUndervoltageAlarm(convertIntType("vehicleStorageDeviceUndervoltageAlarm", vehicleMap));
            itcastPartObj.setTotalCurrent(convertDoubleType("totalCurrent", vehicleMap));
            itcastPartObj.setBatteryAlarm(convertIntType("batteryAlarm", vehicleMap));
            itcastPartObj.setBattPoleTOver(convertStringType("battPoleTOver", vehicleMap));
            itcastPartObj.setAcSystemFailure(convertIntType("acSystemFailure", vehicleMap));
            itcastPartObj.setAirBagWarningLamp(convertIntType("airBagWarningLamp", vehicleMap));
            itcastPartObj.setBatteryChargeStatus(convertIntType("batteryChargeStatus", vehicleMap));
            itcastPartObj.setChargingGunConnectionState(convertIntType("chargingGunConnectionState", vehicleMap));
            itcastPartObj.setRechargeableStorageDeviceMismatchAlarm(convertIntType("rechargeableStorageDeviceMismatchAlarm", vehicleMap));
            itcastPartObj.setVehiclePureDeviceTypeOvercharge(convertIntType("vehiclePureDeviceTypeOvercharge", vehicleMap));
            itcastPartObj.setMaxVoltageBattery(convertDoubleType("maxVoltageBattery", vehicleMap));
            itcastPartObj.setDcdcTemperatureAlarm(convertIntType("dcdcTemperatureAlarm", vehicleMap));
            itcastPartObj.setIsValidGps(convertStringType("isValidGps", vehicleMap));
            itcastPartObj.setDriveMotorControllerTemperatureAlarm(convertIntType("driveMotorControllerTemperatureAlarm", vehicleMap));
            itcastPartObj.setRepay(convertStringType("repay", vehicleMap));
            itcastPartObj.setEnergyRecoveryStatus(convertIntType("energyRecoveryStatus", vehicleMap));
            itcastPartObj.setFireStatus(convertIntType("fireStatus", vehicleMap));
            itcastPartObj.setCarMode(convertIntType("carMode", vehicleMap));
            itcastPartObj.setHighVoltageInterlockStateAlarm(convertIntType("highVoltageInterlockStateAlarm", vehicleMap));
            itcastPartObj.setInsulationAlarm(convertIntType("insulationAlarm", vehicleMap));
            itcastPartObj.setMileageInformation(convertIntType("mileageInformation", vehicleMap));
            itcastPartObj.setRemainPower(convertDoubleType("remainPower", vehicleMap));
            itcastPartObj.setEngineStatus(convertStringType("engineStatus", vehicleMap));
            itcastPartObj.setAbsStatus(convertStringType("absStatus", vehicleMap));
            itcastPartObj.setVcuBrkErr(convertStringType("VcuBrkErr", vehicleMap));
            itcastPartObj.setESP_TCSFailStatus(convertStringType("ESP_TCSFailStatus", vehicleMap));
            itcastPartObj.setEPS_EPSFailed(convertStringType("EPS_EPSFailed", vehicleMap));
            itcastPartObj.setMaxTemperatureValue(convertDoubleType("maxTemperatureValue", vehicleMap));
            itcastPartObj.setMinTemperatureValue(convertDoubleType("minTemperatureValue", vehicleMap));

            /* ------------------------------------------nevChargeSystemVoltageDtoList 可充电储能子系统电压信息列表-------------------------------------- */
            List<Map<String, Object>> nevChargeSystemVoltageDtoList = jsonToList(vehicleMap.getOrDefault("nevChargeSystemVoltageDtoList", new ArrayList<Object>()).toString());
            if (!nevChargeSystemVoltageDtoList.isEmpty()) {
                // 只取list中的第一个map集合
                Map<String, Object> nevChargeSystemVoltageDtoMap = nevChargeSystemVoltageDtoList.get(0);
                itcastPartObj.setBatteryVoltage(convertJoinStringType("batteryVoltage", nevChargeSystemVoltageDtoMap));
                itcastPartObj.setChargeSystemVoltage(convertDoubleType("chargeSystemVoltage",nevChargeSystemVoltageDtoMap));
                itcastPartObj.setChargeSystemCurrent(convertDoubleType("chargeSystemCurrent", nevChargeSystemVoltageDtoMap));
            }
            /* -------------------------------------------------------------------------------------------------------------- */

            /* -----------------------------------------nevChargeSystemTemperatureDtoList------------------------------------ */
            List<Map<String, Object>> nevChargeSystemTemperatureDtoList = jsonToList(vehicleMap.getOrDefault("nevChargeSystemTemperatureDtoList", new ArrayList()).toString());
            if (!nevChargeSystemTemperatureDtoList.isEmpty()) {
                Map<String, Object> nevChargeSystemTemperatureMap = nevChargeSystemTemperatureDtoList.get(0);
                itcastPartObj.setProbeTemperatures(convertJoinStringType("probeTemperatures", nevChargeSystemTemperatureMap));
            }
            /* -------------------------------------------------------------------------------------------------------------- */

            /* --------------------------------------------ecuErrCodeDataList------------------------------------------------ */
            Map<String, Object> xcuerrinfoMap = jsonToMap(vehicleMap.getOrDefault("xcuerrinfo", new HashMap<String, Object>()).toString());
            if (!xcuerrinfoMap.isEmpty()) {
                List<Map<String, Object>> ecuErrCodeDataList = jsonToList(xcuerrinfoMap.getOrDefault("ecuErrCodeDataList", new ArrayList()).toString()) ;
                if (ecuErrCodeDataList.size() > 4) {
                    itcastPartObj.setVcuFaultCode(convertJoinStringType("errCodes", ecuErrCodeDataList.get(0)));
                    itcastPartObj.setBcuFaultCodes(convertJoinStringType("errCodes", ecuErrCodeDataList.get(1)));
                    itcastPartObj.setDcdcFaultCode(convertJoinStringType("errCodes", ecuErrCodeDataList.get(2)));
                    itcastPartObj.setIpuFaultCodes(convertJoinStringType("errCodes", ecuErrCodeDataList.get(3)));
                    itcastPartObj.setObcFaultCode(convertJoinStringType("errCodes", ecuErrCodeDataList.get(4)));
                }
            }
            /* -------------------------------------------------------------------------------------------------------------- */

            // carStatus不在有效范围，设置值为255
            if (convertStringType("carStatus", vehicleMap).length() > 3) {
                itcastPartObj.setCarStatus(255);
            } else {
                itcastPartObj.setCarStatus(convertIntType("carStatus", vehicleMap));
            }
            // terminalTime字段不为空，设置标记时间为terminalTime时间
            if(!itcastPartObj.getTerminalTime().isEmpty()){
                itcastPartObj.setTerminalTimeStamp(DateUtil.convertStringToDateTime(itcastPartObj.getTerminalTime()).getTime());
                // 终端时间延迟五个小时，设置为默认值
                if(itcastPartObj.getTerminalTimeStamp() > (System.currentTimeMillis() + 1000 * 60 * 5)){
                    itcastPartObj.setTerminalTimeStamp(-999999L);
                }
            }
        } catch (Exception e){
            itcastPartObj.setErrorData(jsonString);
            logger.error("json 数据格式错误...", e);
        }
        // 如果没有VIN号和终端时间，则为无效数据
        if(itcastPartObj.getVin().isEmpty() || itcastPartObj.getTerminalTime().isEmpty() || itcastPartObj.getTerminalTimeStamp() < 1){
            if(itcastPartObj.getVin().isEmpty()){
                logger.error("vin.isEmpty");
            }
            if(itcastPartObj.getTerminalTime().isEmpty()){
                logger.error("terminalTime.isEmpty");
            }
            itcastPartObj.setErrorData(jsonString);
        }
        return itcastPartObj;
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