package com.alex.Streaming.Utils;

import com.alex.Streaming.Bean.VehicleDataObj;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

/**
 * @author Alex_liu
 * @Create 2022-11-19
 * @Description
 */
/**
 * 日志json解析工具类
 * 将消费到的json字符串解析成javaBean对象返回
 */
public class JsonParseUtil {
    //使用指定的类（JsonParseUtil）初始化日志对象，方便在日志输出的时候，打印出来日志信息所属的类
    private final static Logger logger = LoggerFactory.getLogger(JsonParseUtil.class);

    /**
     * 传递json字符串，返回解析后的javaBean对象
     * {"gearDriveForce":0,"batteryConsistencyDifferenceAlarm":0,"soc":94,"socJumpAlarm":0,"satNum":10,"caterpillaringFunction":0,"socLowAlarm":0,"chargingGunConnectionState":0,"minTemperatureSubSystemNum":1,"chargedElectronicLockStatus":0,"terminalTime":"2019-11-20 15:33:41","maxVoltageBatteryNum":49,"singleBatteryOverVoltageAlarm":0,"otherFaultCount":0,"vehicleStorageDeviceOvervoltageAlarm":0,"brakeSystemAlarm":0,"serverTime":"2019-11-20 15:34:00.154","vin":"LS6A2E0E3KA006811","rechargeableStorageDevicesFaultCount":0,"driveMotorTemperatureAlarm":0,"remainedPowerMile":0,"dcdcStatusAlarm":0,"gearBrakeForce":1,"lat":29.0646,"driveMotorFaultCodes":"","vehicleSpeed":0.0,"lng":119.50206,"gpsTime":"2019-11-20 15:33:41","nevChargeSystemVoltageDtoList":[{"currentBatteryStartNum":1,"batteryVoltage":[4.15,4.15,4.147,4.148,4.149,4.148,4.139,4.147,4.146,4.152,4.149,4.149,4.15,4.149,4.146,4.148,4.152,4.147,4.149,4.15,4.146,4.15,4.149,4.148,4.148,4.149,4.148,4.149,4.152,4.141,4.146,4.151,4.152,4.154,4.15,4.147,4.15,4.144,4.146,4.145,4.149,4.148,4.147,4.148,4.144,4.143,4.147,4.141,4.156,4.155,4.15,4.15,4.151,4.156,4.153,4.145,4.151,4.144,4.15,4.152,4.145,4.15,4.148,4.149,4.151,4.156,4.152,4.152,4.151,4.142,4.149,4.151,4.148,4.145,4.148,4.146,4.148,4.146,4.151,4.138,4.147,4.138,4.146,4.142,4.149,4.15,4.146,4.148,4.143,4.146,4.147,4.147,4.155,4.151,4.141,4.147],"chargeSystemVoltage":398.2,"currentBatteryCount":96,"batteryCount":96,"childSystemNum":1,"chargeSystemCurrent":0.2999878}],"engineFaultCount":0,"currentElectricity":94,"singleBatteryUnderVoltageAlarm":0,"maxVoltageBatterySubSystemNum":1,"minTemperatureProbe":13,"driveMotorNum":1,"totalVoltage":398.2,"maxAlarmLevel":0,"temperatureDifferenceAlarm":0,"averageEnergyConsumption":0.0,"minVoltageBattery":4.138,"driveMotorData":[{"controllerInputVoltage":399.0,"controllerTemperature":38,"revolutionSpeed":0,"num":1,"controllerDcBusCurrent":0.0,"length":0,"temperature":40,"torque":0.0,"state":4,"type":0,"MAX_BYTE_VALUE":127}],"shiftPositionStatus":0,"minVoltageBatteryNum":80,"engineFaultCodes":"","minTemperatureValue":17,"chargeStatus":3,"deviceTime":"2019-11-20 15:33:41","shiftPosition":0,"totalOdometer":38595.0,"alti":57.0,"speed":0.0,"socHighAlarm":0,"vehicleStorageDeviceUndervoltageAlarm":0,"totalCurrent":0.3,"batteryAlarm":0,"rechargeableStorageDeviceMismatchAlarm":0,"isHistoryPoi":0,"maxVoltageBattery":4.156,"vehiclePureDeviceTypeOvercharge":0,"dcdcTemperatureAlarm":0,"isValidGps":true,"lastUpdatedTime":"2019-11-20 15:34:00.154","driveMotorControllerTemperatureAlarm":0,"nevChargeSystemTemperatureDtoList":[{"probeTemperatures":[18,18,18,20,19,19,19,20,19,19,18,19,17,17,17,17,17,17,18,18,18,17,18,18,17,17,17,17,17,17,18,18],"chargeTemperatureProbeNum":32,"childSystemNum":1}],"igniteCumulativeMileage":0.0,"dcStatus":1,"maxTemperatureSubSystemNum":1,"carStatus":2,"minVoltageBatterySubSystemNum":1,"heading":2.68,"driveMotorFaultCount":0,"tuid":"50003001190517140000000518553162","energyRecoveryStatus":0,"targetType":"VehicleRealtimeDto","maxTemperatureProbe":4,"rechargeableStorageDevicesFaultCodes":"","carMode":1,"highVoltageInterlockStateAlarm":0,"insulationAlarm":0,"maxTemperatureValue":20,"otherFaultCodes":"","remainPower":94.00001,"insulateResistance":6417,"batteryLowTemperatureHeater":0}
     * @param jsonStr
     * @return
     */
    public static VehicleDataObj parseJsonToObject(String jsonStr){
        //定义需要返回的javaBean对象
        VehicleDataObj vehicleDataObj = new VehicleDataObj();

        try {
            //将json字符串解析成map对象
            HashMap<String, Object> vehicleMap = jsonToMap(jsonStr);
            vehicleDataObj.setGearDriveForce(convertIntType("gearDriveForce", vehicleMap));
            vehicleDataObj.setBatteryConsistencyDifferenceAlarm(convertIntType("batteryConsistencyDifferenceAlarm", vehicleMap));
            vehicleDataObj.setSoc(convertIntType("soc", vehicleMap));
            vehicleDataObj.setSocJumpAlarm(convertIntType("socJumpAlarm", vehicleMap));
            vehicleDataObj.setCaterpillaringFunction(convertIntType("caterpillaringFunction", vehicleMap));
            vehicleDataObj.setSatNum(convertIntType("satNum", vehicleMap));
            vehicleDataObj.setSocLowAlarm(convertIntType("socLowAlarm", vehicleMap));
            vehicleDataObj.setChargingGunConnectionState(convertIntType("chargingGunConnectionState", vehicleMap));
            vehicleDataObj.setMinTemperatureSubSystemNum(convertIntType("minTemperatureSubSystemNum", vehicleMap));
            vehicleDataObj.setChargedElectronicLockStatus(convertIntType("chargedElectronicLockStatus", vehicleMap));
            vehicleDataObj.setMaxVoltageBatteryNum(convertIntType("maxVoltageBatteryNum", vehicleMap));
            vehicleDataObj.setTerminalTime(convertStringType("terminalTime", vehicleMap));
            vehicleDataObj.setSingleBatteryOverVoltageAlarm(convertIntType("singleBatteryOverVoltageAlarm", vehicleMap));
            vehicleDataObj.setOtherFaultCount(convertIntType("otherFaultCount", vehicleMap));
            vehicleDataObj.setVehicleStorageDeviceOvervoltageAlarm(convertIntType("vehicleStorageDeviceOvervoltageAlarm", vehicleMap));
            vehicleDataObj.setBrakeSystemAlarm(convertIntType("brakeSystemAlarm", vehicleMap));
            vehicleDataObj.setServerTime(convertStringType("serverTime", vehicleMap));
            vehicleDataObj.setVin(convertStringType("vin", vehicleMap).toUpperCase());
            vehicleDataObj.setRechargeableStorageDevicesFaultCount(convertIntType("rechargeableStorageDevicesFaultCount", vehicleMap));
            vehicleDataObj.setDriveMotorTemperatureAlarm(convertIntType("driveMotorTemperatureAlarm", vehicleMap));
            vehicleDataObj.setGearBrakeForce(convertIntType("gearBrakeForce", vehicleMap));
            vehicleDataObj.setDcdcStatusAlarm(convertIntType("dcdcStatusAlarm", vehicleMap));
            vehicleDataObj.setLat(convertDoubleType("lat", vehicleMap));
            vehicleDataObj.setDriveMotorFaultCodes(convertStringType("driveMotorFaultCodes", vehicleMap));
            vehicleDataObj.setDeviceType(convertStringType("deviceType", vehicleMap));
            vehicleDataObj.setVehicleSpeed(convertDoubleType("vehicleSpeed", vehicleMap));
            vehicleDataObj.setLng(convertDoubleType("lng", vehicleMap));
            vehicleDataObj.setChargingTimeExtensionReason(convertIntType("chargingTimeExtensionReason", vehicleMap));
            vehicleDataObj.setGpsTime(convertStringType("gpsTime", vehicleMap));
            vehicleDataObj.setEngineFaultCount(convertIntType("engineFaultCount", vehicleMap));
            vehicleDataObj.setCarId(convertStringType("carId", vehicleMap));
            vehicleDataObj.setCurrentElectricity(convertDoubleType("vehicleSpeed", vehicleMap));
            vehicleDataObj.setSingleBatteryUnderVoltageAlarm(convertIntType("singleBatteryUnderVoltageAlarm", vehicleMap));
            vehicleDataObj.setMaxVoltageBatterySubSystemNum(convertIntType("maxVoltageBatterySubSystemNum", vehicleMap));
            vehicleDataObj.setMinTemperatureProbe(convertIntType("minTemperatureProbe", vehicleMap));
            vehicleDataObj.setDriveMotorNum(convertIntType("driveMotorNum", vehicleMap));
            vehicleDataObj.setTotalVoltage(convertDoubleType("totalVoltage", vehicleMap));
            vehicleDataObj.setTemperatureDifferenceAlarm(convertIntType("temperatureDifferenceAlarm", vehicleMap));
            vehicleDataObj.setMaxAlarmLevel(convertIntType("maxAlarmLevel", vehicleMap));
            vehicleDataObj.setStatus(convertIntType("status", vehicleMap));
            vehicleDataObj.setGeerPosition(convertIntType("geerPosition", vehicleMap));
            vehicleDataObj.setAverageEnergyConsumption(convertDoubleType("averageEnergyConsumption", vehicleMap));
            vehicleDataObj.setMinVoltageBattery(convertDoubleType("minVoltageBattery", vehicleMap));
            vehicleDataObj.setGeerStatus(convertIntType("geerStatus", vehicleMap));
            vehicleDataObj.setMinVoltageBatteryNum(convertIntType("minVoltageBatteryNum", vehicleMap));
            vehicleDataObj.setValidGps(convertStringType("validGps", vehicleMap));
            vehicleDataObj.setEngineFaultCodes(convertStringType("engineFaultCodes", vehicleMap));
            vehicleDataObj.setMinTemperatureValue(convertDoubleType("minTemperatureValue", vehicleMap));
            vehicleDataObj.setChargeStatus(convertIntType("chargeStatus", vehicleMap));
            vehicleDataObj.setIgnitionTime(convertStringType("ignitionTime", vehicleMap));
            vehicleDataObj.setTotalOdometer(convertDoubleType("totalOdometer", vehicleMap));
            vehicleDataObj.setAlti(convertDoubleType("alti", vehicleMap));
            vehicleDataObj.setSpeed(convertDoubleType("speed", vehicleMap));
            vehicleDataObj.setSocHighAlarm(convertIntType("socHighAlarm", vehicleMap));
            vehicleDataObj.setVehicleStorageDeviceUndervoltageAlarm(convertIntType("vehicleStorageDeviceUndervoltageAlarm", vehicleMap));
            vehicleDataObj.setTotalCurrent(convertDoubleType("totalCurrent", vehicleMap));
            vehicleDataObj.setBatteryAlarm(convertIntType("batteryAlarm", vehicleMap));
            vehicleDataObj.setRechargeableStorageDeviceMismatchAlarm(convertIntType("rechargeableStorageDeviceMismatchAlarm", vehicleMap));
            vehicleDataObj.setIsHistoryPoi(convertIntType("isHistoryPoi", vehicleMap));
            vehicleDataObj.setVehiclePureDeviceTypeOvercharge(convertIntType("vehiclePureDeviceTypeOvercharge", vehicleMap));
            vehicleDataObj.setMaxVoltageBattery(convertDoubleType("maxVoltageBattery", vehicleMap));
            vehicleDataObj.setDcdcTemperatureAlarm(convertIntType("dcdcTemperatureAlarm", vehicleMap));
            vehicleDataObj.setIsValidGps(convertStringType("isValidGps", vehicleMap));
            vehicleDataObj.setLastUpdatedTime(convertStringType("lastUpdatedTime", vehicleMap));
            vehicleDataObj.setDriveMotorControllerTemperatureAlarm(convertIntType("driveMotorControllerTemperatureAlarm", vehicleMap));
            vehicleDataObj.setIgniteCumulativeMileage(convertDoubleType("igniteCumulativeMileage", vehicleMap));
            vehicleDataObj.setDcStatus(convertIntType("dcStatus", vehicleMap));
            vehicleDataObj.setRepay(convertStringType("repay", vehicleMap));
            vehicleDataObj.setMaxTemperatureSubSystemNum(convertIntType("maxTemperatureSubSystemNum", vehicleMap));
            vehicleDataObj.setMinVoltageBatterySubSystemNum(convertIntType("minVoltageBatterySubSystemNum", vehicleMap));
            vehicleDataObj.setHeading(convertDoubleType("heading", vehicleMap));
            vehicleDataObj.setTuid(convertStringType("tuid", vehicleMap));
            vehicleDataObj.setEnergyRecoveryStatus(convertIntType("energyRecoveryStatus", vehicleMap));
            vehicleDataObj.setFireStatus(convertIntType("fireStatus", vehicleMap));
            vehicleDataObj.setTargetType(convertStringType("targetType", vehicleMap));
            vehicleDataObj.setMaxTemperatureProbe(convertIntType("maxTemperatureProbe", vehicleMap));
            vehicleDataObj.setRechargeableStorageDevicesFaultCodes(convertStringType("rechargeableStorageDevicesFaultCodes", vehicleMap));
            vehicleDataObj.setCarMode(convertIntType("carMode", vehicleMap));
            vehicleDataObj.setHighVoltageInterlockStateAlarm(convertIntType("highVoltageInterlockStateAlarm", vehicleMap));
            vehicleDataObj.setInsulationAlarm(convertIntType("insulationAlarm", vehicleMap));
            vehicleDataObj.setMileageInformation(convertIntType("mileageInformation", vehicleMap));
            vehicleDataObj.setMaxTemperatureValue(convertDoubleType("maxTemperatureValue", vehicleMap));
            vehicleDataObj.setOtherFaultCodes(convertStringType("otherFaultCodes", vehicleMap));
            vehicleDataObj.setRemainPower(convertDoubleType("remainPower", vehicleMap));
            vehicleDataObj.setInsulateResistance(convertIntType("insulateResistance", vehicleMap));
            vehicleDataObj.setBatteryLowTemperatureHeater(convertIntType("batteryLowTemperatureHeater", vehicleMap));
            vehicleDataObj.setFuelConsumption100km(convertStringType("fuelConsumption100km", vehicleMap));
            vehicleDataObj.setFuelConsumption(convertStringType("fuelConsumption", vehicleMap));
            vehicleDataObj.setEngineSpeed(convertStringType("engineSpeed", vehicleMap));
            vehicleDataObj.setEngineStatus(convertStringType("engineStatus", vehicleMap));
            vehicleDataObj.setTrunk(convertIntType("trunk", vehicleMap));
            vehicleDataObj.setLowBeam(convertIntType("lowBeam", vehicleMap));
            vehicleDataObj.setTriggerLatchOverheatProtect(convertStringType("triggerLatchOverheatProtect", vehicleMap));
            vehicleDataObj.setTurnLndicatorRight(convertIntType("turnLndicatorRight", vehicleMap));
            vehicleDataObj.setHighBeam(convertIntType("highBeam", vehicleMap));
            vehicleDataObj.setTurnLndicatorLeft(convertIntType("turnLndicatorLeft", vehicleMap));
            vehicleDataObj.setBcuSwVers(convertIntType("bcuSwVers", vehicleMap));
            vehicleDataObj.setBcuHwVers(convertIntType("bcuHwVers", vehicleMap));
            vehicleDataObj.setBcuOperMod(convertIntType("bcuOperMod", vehicleMap));
            vehicleDataObj.setChrgEndReason(convertIntType("chrgEndReason", vehicleMap));
            vehicleDataObj.setBCURegenEngDisp(convertStringType("BCURegenEngDisp", vehicleMap));
            vehicleDataObj.setBCURegenCpDisp(convertIntType("BCURegenCpDisp", vehicleMap));
            vehicleDataObj.setBcuChrgMod(convertIntType("bcuChrgMod", vehicleMap));
            vehicleDataObj.setBatteryChargeStatus(convertIntType("batteryChargeStatus", vehicleMap));
            vehicleDataObj.setBcuFltRnk(convertIntType("bcuFltRnk", vehicleMap));
            vehicleDataObj.setBattPoleTOver(convertStringType("battPoleTOver", vehicleMap));
            vehicleDataObj.setBcuSOH(convertDoubleType("bcuSOH", vehicleMap));
            vehicleDataObj.setBattIntrHeatActive(convertIntType("battIntrHeatActive", vehicleMap));
            vehicleDataObj.setBattIntrHeatReq(convertIntType("battIntrHeatReq", vehicleMap));
            vehicleDataObj.setBCUBattTarT(convertStringType("BCUBattTarT", vehicleMap));
            vehicleDataObj.setBattExtHeatReq(convertIntType("battExtHeatReq", vehicleMap));
            vehicleDataObj.setBCUMaxChrgPwrLongT(convertStringType("BCUMaxChrgPwrLongT", vehicleMap));
            vehicleDataObj.setBCUMaxDchaPwrLongT(convertStringType("BCUMaxDchaPwrLongT", vehicleMap));
            vehicleDataObj.setBCUTotalRegenEngDisp(convertStringType("BCUTotalRegenEngDisp", vehicleMap));
            vehicleDataObj.setBCUTotalRegenCpDisp(convertStringType("BCUTotalRegenCpDisp ", vehicleMap));
            vehicleDataObj.setDcdcFltRnk(convertIntType("dcdcFltRnk", vehicleMap));
            vehicleDataObj.setDcdcOutpCrrt(convertDoubleType("dcdcOutpCrrt", vehicleMap));
            vehicleDataObj.setDcdcOutpU(convertDoubleType("dcdcOutpU", vehicleMap));
            vehicleDataObj.setDcdcAvlOutpPwr(convertIntType("dcdcAvlOutpPwr", vehicleMap));
            vehicleDataObj.setAbsActiveStatus(convertStringType("absActiveStatus", vehicleMap));
            vehicleDataObj.setAbsStatus(convertStringType("absStatus", vehicleMap));
            vehicleDataObj.setVcuBrkErr(convertStringType("VcuBrkErr", vehicleMap));
            vehicleDataObj.setEPB_AchievedClampForce(convertStringType("EPB_AchievedClampForce", vehicleMap));
            vehicleDataObj.setEpbSwitchPosition(convertStringType("epbSwitchPosition", vehicleMap));
            vehicleDataObj.setEpbStatus(convertStringType("epbStatus", vehicleMap));
            vehicleDataObj.setEspActiveStatus(convertStringType("espActiveStatus", vehicleMap));
            vehicleDataObj.setEspFunctionStatus(convertStringType("espFunctionStatus", vehicleMap));
            vehicleDataObj.setESP_TCSFailStatus(convertStringType("ESP_TCSFailStatus", vehicleMap));
            vehicleDataObj.setHhcActive(convertStringType("hhcActive", vehicleMap));
            vehicleDataObj.setTcsActive(convertStringType("tcsActive", vehicleMap));
            vehicleDataObj.setEspMasterCylinderBrakePressure(convertStringType("espMasterCylinderBrakePressure", vehicleMap));
            vehicleDataObj.setESP_MasterCylinderBrakePressureValid(convertStringType("ESP_MasterCylinderBrakePressureValid", vehicleMap));
            vehicleDataObj.setEspTorqSensorStatus(convertStringType("espTorqSensorStatus", vehicleMap));
            vehicleDataObj.setEPS_EPSFailed(convertStringType("EPS_EPSFailed", vehicleMap));
            vehicleDataObj.setSasFailure(convertStringType("sasFailure", vehicleMap));
            vehicleDataObj.setSasSteeringAngleSpeed(convertStringType("sasSteeringAngleSpeed", vehicleMap));
            vehicleDataObj.setSasSteeringAngle(convertStringType("sasSteeringAngle", vehicleMap));
            vehicleDataObj.setSasSteeringAngleValid(convertStringType("sasSteeringAngleValid", vehicleMap));
            vehicleDataObj.setEspSteeringTorque(convertStringType("espSteeringTorque", vehicleMap));
            vehicleDataObj.setAcReq(convertIntType("acReq", vehicleMap));
            vehicleDataObj.setAcSystemFailure(convertIntType("acSystemFailure", vehicleMap));
            vehicleDataObj.setPtcPwrAct(convertDoubleType("ptcPwrAct", vehicleMap));
            vehicleDataObj.setPlasmaStatus(convertIntType("plasmaStatus", vehicleMap));
            vehicleDataObj.setBattInTemperature(convertIntType("battInTemperature", vehicleMap));
            vehicleDataObj.setBattWarmLoopSts(convertStringType("battWarmLoopSts", vehicleMap));
            vehicleDataObj.setBattCoolngLoopSts(convertStringType("battCoolngLoopSts", vehicleMap));
            vehicleDataObj.setBattCoolActv(convertStringType("battCoolActv", vehicleMap));
            vehicleDataObj.setMotorOutTemperature(convertIntType("motorOutTemperature", vehicleMap));
            vehicleDataObj.setPowerStatusFeedBack(convertStringType("powerStatusFeedBack", vehicleMap));
            vehicleDataObj.setAC_RearDefrosterSwitch(convertIntType("AC_RearDefrosterSwitch", vehicleMap));
            vehicleDataObj.setRearFoglamp(convertIntType("rearFoglamp", vehicleMap));
            vehicleDataObj.setDriverDoorLock(convertIntType("driverDoorLock", vehicleMap));
            vehicleDataObj.setAcDriverReqTemp(convertDoubleType("acDriverReqTemp", vehicleMap));
            vehicleDataObj.setKeyAlarm(convertIntType("keyAlarm", vehicleMap));
            vehicleDataObj.setAirCleanStsRemind(convertIntType("airCleanStsRemind", vehicleMap));
            vehicleDataObj.setRecycleType(convertIntType("recycleType", vehicleMap));
            vehicleDataObj.setStartControlsignal(convertStringType("startControlsignal", vehicleMap));
            vehicleDataObj.setAirBagWarningLamp(convertIntType("airBagWarningLamp", vehicleMap));
            vehicleDataObj.setFrontDefrosterSwitch(convertIntType("frontDefrosterSwitch", vehicleMap));
            vehicleDataObj.setFrontBlowType(convertStringType("frontBlowType", vehicleMap));
            vehicleDataObj.setFrontReqWindLevel(convertIntType("frontReqWindLevel", vehicleMap));
            vehicleDataObj.setBcmFrontWiperStatus(convertStringType("bcmFrontWiperStatus", vehicleMap));
            vehicleDataObj.setTmsPwrAct(convertStringType("tmsPwrAct", vehicleMap));
            vehicleDataObj.setKeyUndetectedAlarmSign(convertIntType("keyUndetectedAlarmSign", vehicleMap));
            vehicleDataObj.setPositionLamp(convertStringType("positionLamp", vehicleMap));
            vehicleDataObj.setDriverReqTempModel(convertIntType("driverReqTempModel", vehicleMap));
            vehicleDataObj.setTurnLightSwitchSts(convertIntType("turnLightSwitchSts", vehicleMap));
            vehicleDataObj.setAutoHeadlightStatus(convertIntType("autoHeadlightStatus", vehicleMap));
            vehicleDataObj.setDriverDoor(convertIntType("driverDoor", vehicleMap));
            vehicleDataObj.setFrntIpuFltRnk(convertIntType("frntIpuFltRnk", vehicleMap));
            vehicleDataObj.setFrontIpuSwVers(convertStringType("frontIpuSwVers", vehicleMap));
            vehicleDataObj.setFrontIpuHwVers(convertIntType("frontIpuHwVers", vehicleMap));
            vehicleDataObj.setFrntMotTqLongTermMax(convertIntType("frntMotTqLongTermMax", vehicleMap));
            vehicleDataObj.setFrntMotTqLongTermMin(convertIntType("frntMotTqLongTermMin", vehicleMap));
            vehicleDataObj.setCpvValue(convertIntType("cpvValue", vehicleMap));
            vehicleDataObj.setObcChrgSts(convertIntType("obcChrgSts", vehicleMap));
            vehicleDataObj.setObcFltRnk(convertStringType("obcFltRnk", vehicleMap));
            vehicleDataObj.setObcChrgInpAcI(convertDoubleType("obcChrgInpAcI", vehicleMap));
            vehicleDataObj.setObcChrgInpAcU(convertIntType("obcChrgInpAcU", vehicleMap));
            vehicleDataObj.setObcChrgDcI(convertDoubleType("obcChrgDcI", vehicleMap));
            vehicleDataObj.setObcChrgDcU(convertDoubleType("obcChrgDcU", vehicleMap));
            vehicleDataObj.setObcTemperature(convertIntType("obcTemperature", vehicleMap));
            vehicleDataObj.setObcMaxChrgOutpPwrAvl(convertIntType("obcMaxChrgOutpPwrAvl", vehicleMap));
            vehicleDataObj.setPassengerBuckleSwitch(convertIntType("passengerBuckleSwitch", vehicleMap));
            vehicleDataObj.setCrashlfo(convertStringType("crashlfo", vehicleMap));
            vehicleDataObj.setDriverBuckleSwitch(convertIntType("driverBuckleSwitch", vehicleMap));
            vehicleDataObj.setEngineStartHibit(convertStringType("engineStartHibit", vehicleMap));
            vehicleDataObj.setLockCommand(convertStringType("lockCommand", vehicleMap));
            vehicleDataObj.setSearchCarReq(convertStringType("searchCarReq", vehicleMap));
            vehicleDataObj.setAcTempValueReq(convertStringType("acTempValueReq", vehicleMap));
            vehicleDataObj.setVcuErrAmnt(convertStringType("vcuErrAmnt", vehicleMap));
            vehicleDataObj.setVcuSwVers(convertIntType("vcuSwVers", vehicleMap));
            vehicleDataObj.setVcuHwVers(convertIntType("vcuHwVers", vehicleMap));
            vehicleDataObj.setLowSpdWarnStatus(convertStringType("lowSpdWarnStatus", vehicleMap));
            vehicleDataObj.setLowBattChrgRqe(convertIntType("lowBattChrgRqe", vehicleMap));
            vehicleDataObj.setLowBattChrgSts(convertStringType("lowBattChrgSts", vehicleMap));
            vehicleDataObj.setLowBattU(convertDoubleType("lowBattU", vehicleMap));
            vehicleDataObj.setHandlebrakeStatus(convertIntType("handlebrakeStatus", vehicleMap));
            vehicleDataObj.setShiftPositionValid(convertStringType("shiftPositionValid", vehicleMap));
            vehicleDataObj.setAccPedalValid(convertStringType("accPedalValid", vehicleMap));
            vehicleDataObj.setDriveMode(convertIntType("driveMode", vehicleMap));
            vehicleDataObj.setDriveModeButtonStatus(convertIntType("driveModeButtonStatus", vehicleMap));
            vehicleDataObj.setVCUSRSCrashOutpSts(convertIntType("VCUSRSCrashOutpSts", vehicleMap));
            vehicleDataObj.setTextDispEna(convertIntType("textDispEna", vehicleMap));
            vehicleDataObj.setCrsCtrlStatus(convertIntType("crsCtrlStatus", vehicleMap));
            vehicleDataObj.setCrsTarSpd(convertIntType("crsTarSpd", vehicleMap));
            vehicleDataObj.setCrsTextDisp(convertIntType("crsTextDisp",vehicleMap ));
            vehicleDataObj.setKeyOn(convertIntType("keyOn", vehicleMap));
            vehicleDataObj.setVehPwrlim(convertIntType("vehPwrlim", vehicleMap));
            vehicleDataObj.setVehCfgInfo(convertStringType("vehCfgInfo", vehicleMap));
            vehicleDataObj.setVacBrkPRmu(convertIntType("vacBrkPRmu", vehicleMap));

            /**
             * 解析复杂数据结构：
             * 1：nevChargeSystemVoltageDtoList
             * 2：nevChargeSystemTemperatureDtoList
             * 3：driveMotorData
             * 4：xcuerrinfo
             * */
            //1：nevChargeSystemVoltageDtoList:可充电储能子系统电压信息列表
            List<Map<String, Object>> nevChargeSystemVoltageDtoList = jsonToList(vehicleMap.getOrDefault("nevChargeSystemVoltageDtoList", new ArrayList<Object>()).toString());
            if(!nevChargeSystemVoltageDtoList.isEmpty()){
                //解析list中的的第一个map对象集合，集合中的第一条数据为有效数据
                Map<String, Object> nevChargeSystemVoltageDToMap = nevChargeSystemVoltageDtoList.get(0);
                vehicleDataObj.setCurrentBatteryStartNum(convertIntType("currentBatteryStartNum", nevChargeSystemVoltageDToMap));
                vehicleDataObj.setChargeSystemVoltage(convertDoubleType("chargeSystemVoltage", nevChargeSystemVoltageDToMap));
                vehicleDataObj.setCurrentBatteryCount(convertIntType("currentBatteryCount", nevChargeSystemVoltageDToMap));
                vehicleDataObj.setBatteryCount(convertIntType("batteryCount", nevChargeSystemVoltageDToMap));
                vehicleDataObj.setChildSystemNum(convertIntType("childSystemNum", nevChargeSystemVoltageDToMap));
                vehicleDataObj.setChargeSystemCurrent(convertDoubleType("chargeSystemCurrent", nevChargeSystemVoltageDToMap));
                vehicleDataObj.setBatteryVoltage(convertJoinStringType("batteryVoltage", nevChargeSystemVoltageDToMap));
            }

            //2：nevChargeSystemTemperatureDtoList
            List<Map<String, Object>> nevChargeSystemTemperatureDtoList = jsonToList(vehicleMap.getOrDefault("nevChargeSystemTemperatureDtoList", new ArrayList()).toString());
            if (!nevChargeSystemTemperatureDtoList.isEmpty()) {
                Map<String, Object> nevChargeSystemTemperatureMap = nevChargeSystemTemperatureDtoList.get(0);
                vehicleDataObj.setProbeTemperatures(convertJoinStringType("probeTemperatures", nevChargeSystemTemperatureMap));
                vehicleDataObj.setChargeTemperatureProbeNum(convertIntType("chargeTemperatureProbeNum", nevChargeSystemTemperatureMap));
            }

            //3：driveMotorData
            List<Map<String, Object>> driveMotorData = jsonToList(vehicleMap.getOrDefault("driveMotorData", new ArrayList()).toString());                                    //驱动电机数据
            if (!driveMotorData.isEmpty()) {
                Map<String, Object> driveMotorMap = driveMotorData.get(0);
                vehicleDataObj.setControllerInputVoltage(convertDoubleType("controllerInputVoltage", driveMotorMap));
                vehicleDataObj.setControllerTemperature(convertDoubleType("controllerTemperature", driveMotorMap));
                vehicleDataObj.setRevolutionSpeed(convertDoubleType("revolutionSpeed", driveMotorMap));
                vehicleDataObj.setNum(convertIntType("num", driveMotorMap));
                vehicleDataObj.setControllerDcBusCurrent(convertDoubleType("controllerDcBusCurrent", driveMotorMap));
                vehicleDataObj.setTemperature(convertDoubleType("temperature", driveMotorMap));
                vehicleDataObj.setTorque(convertDoubleType("torque", driveMotorMap));
                vehicleDataObj.setState(convertIntType("state", driveMotorMap));
            }

            //4：xcuerrinfo
            Map<String, Object> xcuerrinfoMap = jsonToMap(vehicleMap.getOrDefault("xcuerrinfo", new HashMap<String, Object>()).toString());
            if (!xcuerrinfoMap.isEmpty()) {
                List<Map<String, Object>> ecuErrCodeDataList = jsonToList(xcuerrinfoMap.getOrDefault("ecuErrCodeDataList", new ArrayList()).toString()) ;
                if (ecuErrCodeDataList.size() > 4) {
                    vehicleDataObj.setVcuFaultCode(convertJoinStringType("errCodes", ecuErrCodeDataList.get(0)));
                    vehicleDataObj.setBcuFaultCodes(convertJoinStringType("errCodes", ecuErrCodeDataList.get(1)));
                    vehicleDataObj.setDcdcFaultCode(convertJoinStringType("errCodes", ecuErrCodeDataList.get(2)));
                    vehicleDataObj.setIpuFaultCodes(convertJoinStringType("errCodes", ecuErrCodeDataList.get(3)));
                    vehicleDataObj.setObcFaultCode(convertJoinStringType("errCodes", ecuErrCodeDataList.get(4)));
                }
            }

            //TODO 区分数据是正常的数据还是异常的数据！区分的依据是vin为空或者terminalTime为空
            if(StringUtils.isEmpty(vehicleDataObj.getVin()) || StringUtils.isEmpty(vehicleDataObj.getTerminalTime())){
                //异常数据
                if(!StringUtils.isEmpty(jsonStr)){
                    vehicleDataObj.setErrorData(jsonStr);
                }

                //打印关键信息
                if(StringUtils.isEmpty(vehicleDataObj.getVin())){
                    logger.error("数据中vin为空："+jsonStr);
                }
                if(StringUtils.isEmpty(vehicleDataObj.getTerminalTime())){
                    logger.error("数据中terminalTime为空："+jsonStr);
                }
            }

            //TODO 扩展字段赋值：终端时间（long类型的）
            if(!StringUtils.isEmpty(vehicleDataObj.getTerminalTime())){
                vehicleDataObj.setTerminalTimeStamp(DateUtil.convertStringToDate(vehicleDataObj.getTerminalTime()).getTime());
            }
        } catch (Exception exception) {
            //异常数据
            if(!StringUtils.isEmpty(jsonStr)){
                vehicleDataObj.setErrorData(jsonStr);
            }
            logger.error(exception.getMessage());
            exception.printStackTrace();
        }
        //返回解析后的javaBean对象
        return vehicleDataObj;
    }

    /**
     * 将json字符串转换成集合list对象返回
     * @param jsonStr
     * @return
     */
    private static List<Map<String, Object>> jsonToList(String jsonStr){
        //定义需要返回的list对象
        List<Map<String, Object>> resultList = new ArrayList<>();
        //定义jsonArray对象
        JSONArray jsonArray = new JSONArray(jsonStr);
        for (int i = 0; i < jsonArray.length(); i++) {
            HashMap<String, Object> hashMap = jsonToMap(jsonArray.get(i).toString());
            resultList.add(hashMap);
        }

        //返回json列表集合对象
        return resultList;
    }

    /**
     * 传递json字符串转换成map对象返回
     * @param jsonStr
     * @return
     */
    private static HashMap<String, Object> jsonToMap(String jsonStr) {
        //创建jsonObject对象
        JSONObject jsonObject = new JSONObject(jsonStr);
        //定义返回的map对象
        HashMap<String, Object> hashMap = new HashMap<>();
        Iterator<String> iterator = jsonObject.keys();
        while (iterator.hasNext()){
            String key = iterator.next();
            Object value = jsonObject.get(key);
            hashMap.put(key, value);
        }

        //返回解析后的map对象集合
        return hashMap;
    }

    /**
     * 提取数据类型转换成int类型返回
     * @param filedName
     * @param map
     * @return
     */
    private static Integer convertIntType(String filedName, Map<String, Object> map){
        return Integer.parseInt(map.getOrDefault(filedName, -999999).toString());
    }

    /**
     * 提取数据类型转换成String类型返回
     * @param filedName
     * @param map
     * @return
     */
    private static String convertStringType(String filedName, Map<String, Object> map){
        return map.getOrDefault(filedName, "").toString();
    }

    /**
     * 提取数据类型转换成Double类型返回
     * @param filedName
     * @param map
     * @return
     */
    private static Double convertDoubleType(String filedName, Map<String, Object> map){
        return Double.parseDouble(map.getOrDefault(filedName, -999999D).toString());
    }

    /**
     * 提取数据类型转换成拼接后的json字符串类型返回
     * @param filedName
     * @param map
     * @return
     */
    private static String convertJoinStringType(String filedName, Map<String, Object> map){
        return String.join("-", convertStringToArray(map.getOrDefault(filedName, "").toString()));
    }

    /**
     * 将字符串拆分成列表对象返回
     * @param str
     * @return
     */
    private static List convertStringToArray(String str) {
        return Arrays.asList(str.split(","));
    }
}
