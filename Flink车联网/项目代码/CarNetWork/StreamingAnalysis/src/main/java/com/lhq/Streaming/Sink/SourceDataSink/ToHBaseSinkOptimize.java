package com.lhq.Streaming.Sink.SourceDataSink;

import com.lhq.Streaming.Utils.ConfigLoader;
import com.lhq.Streaming.Utils.DateUtil;
import com.lhq.Streaming.Utils.VehicleDataObj;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author liu
 * @Create 2022-11-21
 * @Description   正确数据写入hbase优化
 */
public class ToHBaseSinkOptimize extends RichSinkFunction<VehicleDataObj> {
    private Logger logger = LoggerFactory.getLogger("ToHBaseSinkOptimize");
    //要写入数据的表名
    private String tableName;
    private Connection conn = null;
    // hbase客户端中的数据写缓存对象
    private BufferedMutator mutator = null;
    //定义列簇
    private String cf = "cf";

    public ToHBaseSinkOptimize(String tableName) {
        this.tableName = tableName;
    }

    /**
     * 重写方法，实现数据写入hbase逻辑
     *   重写open方法：加载资源配置
     *      创建hbase配置对象，设置hbase配置信息，添加客户端相关配置
     *      跟据hbase的连接地址，获得hbase连接
     *      根据hbase连接和表名，获得hbase客户端Table对象
     * @param parameters
     * @throws Exception
     */
    @Override
    public void open(Configuration parameters) throws Exception {
        org.apache.hadoop.conf.Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.property.clientPort", ConfigLoader.get("zookeeper.clientPort"));
        config.set("hbase.zookeeper.quorum", ConfigLoader.get("zookeeper.quorum"));
        TableName tname = TableName.valueOf(tableName);
        config.set(TableInputFormat.INPUT_TABLE, tname.getNameAsString());
        conn = ConnectionFactory.createConnection(config);
        BufferedMutatorParams params = new BufferedMutatorParams(tname);
        //设置缓存10MB，当达到10MB时数据会自动刷到HBase
        params.writeBufferSize(1024 * 1024 * 10);
        // 强制缓冲区提交数据
        mutator = conn.getBufferedMutator(params);
    }

    /**
     * 重写invoke方法：数据写入hbase逻辑
     *    设计hbase的rowkey、根据列族封装Put对象
     *    把封装好的Put对象，写入HBase表中
     * @param vehicleDataObj
     * @param context
     * @throws Exception
     */
    @Override
    public void invoke(VehicleDataObj vehicleDataObj, Context context) throws Exception {
        try {
            Put put = setDataSourcePut(vehicleDataObj);
            mutator.mutate(put);
            //指定时间内的数据强制刷写到hbase
            mutator.flush();
        } catch (IOException ex) {
            logger.error("写入到hbase失败："+ex.getMessage());
        }
    }

    /**
     *  生成put对象
     * @param vehicleDataObj
     * @return
     */
    private Put setDataSourcePut(VehicleDataObj vehicleDataObj){
        // rowkey设计:保证rowKey不会有序
        Put put = new Put(Bytes.toBytes(vehicleDataObj.getVin() + "_" + (Long.MAX_VALUE - vehicleDataObj.getTerminalTimeStamp())));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("vin"), Bytes.toBytes(vehicleDataObj.getVin()));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("terminalTime"), Bytes.toBytes(vehicleDataObj.getTerminalTime()));
        if (vehicleDataObj.getSoc() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("soc"), Bytes.toBytes(String.valueOf(vehicleDataObj.getSoc())));
        if (vehicleDataObj.getLat() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("lat"), Bytes.toBytes(String.valueOf(vehicleDataObj.getLat())));
        if (vehicleDataObj.getLng() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("lng"), Bytes.toBytes(String.valueOf(vehicleDataObj.getLng())));
        if (vehicleDataObj.getGearDriveForce() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("gearDriveForce"), Bytes.toBytes(String.valueOf(vehicleDataObj.getGearDriveForce())));
        if (vehicleDataObj.getBatteryConsistencyDifferenceAlarm() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("batteryConsistencyDifferenceAlarm"), Bytes.toBytes(String.valueOf(vehicleDataObj.getBatteryConsistencyDifferenceAlarm())));
        if (vehicleDataObj.getSocJumpAlarm() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("socJumpAlarm"), Bytes.toBytes(String.valueOf(vehicleDataObj.getSocJumpAlarm())));
        if (vehicleDataObj.getCaterpillaringFunction() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("caterpillaringFunction"), Bytes.toBytes(String.valueOf(vehicleDataObj.getCaterpillaringFunction())));
        if (vehicleDataObj.getSatNum() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("satNum"), Bytes.toBytes(String.valueOf(vehicleDataObj.getSatNum())));
        if (vehicleDataObj.getSocLowAlarm() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("socLowAlarm"), Bytes.toBytes(String.valueOf(vehicleDataObj.getSocLowAlarm())));
        if (vehicleDataObj.getChargingGunConnectionState() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("chargingGunConnectionState"), Bytes.toBytes(String.valueOf(vehicleDataObj.getChargingGunConnectionState())));
        if (vehicleDataObj.getMinTemperatureSubSystemNum() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("minTemperatureSubSystemNum"), Bytes.toBytes(String.valueOf(vehicleDataObj.getMinTemperatureSubSystemNum())));
        if (vehicleDataObj.getChargedElectronicLockStatus() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("chargedElectronicLockStatus"), Bytes.toBytes(String.valueOf(vehicleDataObj.getChargedElectronicLockStatus())));
        if (vehicleDataObj.getMaxVoltageBatteryNum() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("maxVoltageBatteryNum"), Bytes.toBytes(String.valueOf(vehicleDataObj.getMaxVoltageBatteryNum())));
        if (vehicleDataObj.getSingleBatteryOverVoltageAlarm() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("singleBatteryOverVoltageAlarm"), Bytes.toBytes(String.valueOf(vehicleDataObj.getSingleBatteryOverVoltageAlarm())));
        if (vehicleDataObj.getOtherFaultCount() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("otherFaultCount"), Bytes.toBytes(String.valueOf(vehicleDataObj.getOtherFaultCount())));
        if (vehicleDataObj.getVehicleStorageDeviceOvervoltageAlarm() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("vehicleStorageDeviceOvervoltageAlarm"), Bytes.toBytes(String.valueOf(vehicleDataObj.getVehicleStorageDeviceOvervoltageAlarm())));
        if (vehicleDataObj.getBrakeSystemAlarm() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("brakeSystemAlarm"), Bytes.toBytes(String.valueOf(vehicleDataObj.getBrakeSystemAlarm())));
        if (!vehicleDataObj.getServerTime().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("serverTime"), Bytes.toBytes(vehicleDataObj.getServerTime()));
        if (vehicleDataObj.getRechargeableStorageDevicesFaultCount() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("rechargeableStorageDevicesFaultCount"), Bytes.toBytes(String.valueOf(vehicleDataObj.getRechargeableStorageDevicesFaultCount())));
        if (vehicleDataObj.getDriveMotorTemperatureAlarm() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("driveMotorTemperatureAlarm"), Bytes.toBytes(String.valueOf(vehicleDataObj.getDriveMotorTemperatureAlarm())));
        if (vehicleDataObj.getGearBrakeForce() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("gearBrakeForce"), Bytes.toBytes(String.valueOf(vehicleDataObj.getGearBrakeForce())));
        if (vehicleDataObj.getDcdcStatusAlarm() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("dcdcStatusAlarm"), Bytes.toBytes(String.valueOf(vehicleDataObj.getDcdcStatusAlarm())));
        if (!vehicleDataObj.getDriveMotorFaultCodes().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("driveMotorFaultCodes"), Bytes.toBytes(vehicleDataObj.getDriveMotorFaultCodes()));
        if (!vehicleDataObj.getDeviceType().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("deviceType"), Bytes.toBytes(vehicleDataObj.getDeviceType()));
        if (vehicleDataObj.getVehicleSpeed() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("vehicleSpeed"), Bytes.toBytes(String.valueOf(vehicleDataObj.getVehicleSpeed())));
        if (vehicleDataObj.getChargingTimeExtensionReason() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("chargingTimeExtensionReason"), Bytes.toBytes(String.valueOf(vehicleDataObj.getChargingTimeExtensionReason())));
        if (vehicleDataObj.getCurrentBatteryStartNum() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("currentBatteryStartNum"), Bytes.toBytes(String.valueOf(vehicleDataObj.getCurrentBatteryStartNum())));
        if (!vehicleDataObj.getBatteryVoltage().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("batteryVoltage"), Bytes.toBytes(vehicleDataObj.getBatteryVoltage()));
        if (vehicleDataObj.getChargeSystemVoltage() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("chargeSystemVoltage"), Bytes.toBytes(String.valueOf(vehicleDataObj.getChargeSystemVoltage())));
        if (vehicleDataObj.getCurrentBatteryCount() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("currentBatteryCount"), Bytes.toBytes(String.valueOf(vehicleDataObj.getCurrentBatteryCount())));
        if (vehicleDataObj.getBatteryCount() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("batteryCount"), Bytes.toBytes(String.valueOf(vehicleDataObj.getBatteryCount())));
        if (vehicleDataObj.getChildSystemNum() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("childSystemNum"), Bytes.toBytes(String.valueOf(vehicleDataObj.getChildSystemNum())));
        if (vehicleDataObj.getChargeSystemCurrent() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("chargeSystemCurrent"), Bytes.toBytes(String.valueOf(vehicleDataObj.getChargeSystemCurrent())));
        if (!vehicleDataObj.getGpsTime().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("gpsTime"), Bytes.toBytes(vehicleDataObj.getGpsTime()));
        if (vehicleDataObj.getEngineFaultCount() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("engineFaultCount"), Bytes.toBytes(String.valueOf(vehicleDataObj.getEngineFaultCount())));
        if (!vehicleDataObj.getCarId().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("carId"), Bytes.toBytes(vehicleDataObj.getCarId()));
        if (vehicleDataObj.getCurrentElectricity() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("currentElectricity"), Bytes.toBytes(String.valueOf(vehicleDataObj.getCurrentElectricity())));
        if (vehicleDataObj.getSingleBatteryUnderVoltageAlarm() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("singleBatteryUnderVoltageAlarm"), Bytes.toBytes(String.valueOf(vehicleDataObj.getSingleBatteryUnderVoltageAlarm())));
        if (vehicleDataObj.getMaxVoltageBatterySubSystemNum() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("maxVoltageBatterySubSystemNum"), Bytes.toBytes(String.valueOf(vehicleDataObj.getMaxVoltageBatterySubSystemNum())));
        if (vehicleDataObj.getMinTemperatureProbe() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("minTemperatureProbe"), Bytes.toBytes(String.valueOf(vehicleDataObj.getMinTemperatureProbe())));
        if (vehicleDataObj.getDriveMotorNum() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("driveMotorNum"), Bytes.toBytes(String.valueOf(vehicleDataObj.getDriveMotorNum())));
        if (vehicleDataObj.getTotalVoltage() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("totalVoltage"), Bytes.toBytes(String.valueOf(vehicleDataObj.getTotalVoltage())));
        if (vehicleDataObj.getTemperatureDifferenceAlarm() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("temperatureDifferenceAlarm"), Bytes.toBytes(String.valueOf(vehicleDataObj.getTemperatureDifferenceAlarm())));
        if (vehicleDataObj.getMaxAlarmLevel() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("maxAlarmLevel"), Bytes.toBytes(String.valueOf(vehicleDataObj.getMaxAlarmLevel())));
        if (vehicleDataObj.getStatus() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("status"), Bytes.toBytes(String.valueOf(vehicleDataObj.getStatus())));
        if (vehicleDataObj.getGeerPosition() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("geerPosition"), Bytes.toBytes(String.valueOf(vehicleDataObj.getGeerPosition())));
        if (vehicleDataObj.getAverageEnergyConsumption() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("averageEnergyConsumption"), Bytes.toBytes(String.valueOf(vehicleDataObj.getAverageEnergyConsumption())));
        if (vehicleDataObj.getMinVoltageBattery() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("minVoltageBattery"), Bytes.toBytes(String.valueOf(vehicleDataObj.getMinVoltageBattery())));
        if (vehicleDataObj.getGeerStatus() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("geerStatus"), Bytes.toBytes(String.valueOf(vehicleDataObj.getGeerStatus())));
        if (vehicleDataObj.getControllerInputVoltage() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("controllerInputVoltage"), Bytes.toBytes(String.valueOf(vehicleDataObj.getControllerInputVoltage())));
        if (vehicleDataObj.getControllerTemperature() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("controllerTemperature"), Bytes.toBytes(String.valueOf(vehicleDataObj.getControllerTemperature())));
        if (vehicleDataObj.getRevolutionSpeed() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("revolutionSpeed"), Bytes.toBytes(String.valueOf(vehicleDataObj.getRevolutionSpeed())));
        if (vehicleDataObj.getNum() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("num"), Bytes.toBytes(String.valueOf(vehicleDataObj.getNum())));
        if (vehicleDataObj.getControllerDcBusCurrent() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("controllerDcBusCurrent"), Bytes.toBytes(String.valueOf(vehicleDataObj.getControllerDcBusCurrent())));
        if (vehicleDataObj.getTemperature() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("temperature"), Bytes.toBytes(String.valueOf(vehicleDataObj.getTemperature())));
        if (vehicleDataObj.getTorque() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("torque"), Bytes.toBytes(String.valueOf(vehicleDataObj.getTorque())));
        if (vehicleDataObj.getState() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("state"), Bytes.toBytes(String.valueOf(vehicleDataObj.getState())));
        if (vehicleDataObj.getMinVoltageBatteryNum() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("minVoltageBatteryNum"), Bytes.toBytes(String.valueOf(vehicleDataObj.getMinVoltageBatteryNum())));
        if (!vehicleDataObj.getValidGps().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("validGps"), Bytes.toBytes(vehicleDataObj.getValidGps()));
        if (!vehicleDataObj.getEngineFaultCodes().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("engineFaultCodes"), Bytes.toBytes(vehicleDataObj.getEngineFaultCodes()));
        if (vehicleDataObj.getMinTemperatureValue() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("minTemperatureValue"), Bytes.toBytes(String.valueOf(vehicleDataObj.getMinTemperatureValue())));
        if (vehicleDataObj.getChargeStatus() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("chargeStatus"), Bytes.toBytes(String.valueOf(vehicleDataObj.getChargeStatus())));
        if (!vehicleDataObj.getIgnitionTime().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("ignitionTime"), Bytes.toBytes(vehicleDataObj.getIgnitionTime()));
        if (vehicleDataObj.getTotalOdometer() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("totalOdometer"), Bytes.toBytes(String.valueOf(vehicleDataObj.getTotalOdometer())));
        if (vehicleDataObj.getAlti() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("alti"), Bytes.toBytes(String.valueOf(vehicleDataObj.getAlti())));
        if (vehicleDataObj.getSpeed() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("speed"), Bytes.toBytes(String.valueOf(vehicleDataObj.getSpeed())));
        if (vehicleDataObj.getSocHighAlarm() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("socHighAlarm"), Bytes.toBytes(String.valueOf(vehicleDataObj.getSocHighAlarm())));
        if (vehicleDataObj.getVehicleStorageDeviceUndervoltageAlarm() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("vehicleStorageDeviceUndervoltageAlarm"), Bytes.toBytes(String.valueOf(vehicleDataObj.getVehicleStorageDeviceUndervoltageAlarm())));
        if (vehicleDataObj.getTotalCurrent() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("totalCurrent"), Bytes.toBytes(String.valueOf(vehicleDataObj.getTotalCurrent())));
        if (vehicleDataObj.getBatteryAlarm() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("batteryAlarm"), Bytes.toBytes(String.valueOf(vehicleDataObj.getBatteryAlarm())));
        if (vehicleDataObj.getRechargeableStorageDeviceMismatchAlarm() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("rechargeableStorageDeviceMismatchAlarm"), Bytes.toBytes(String.valueOf(vehicleDataObj.getRechargeableStorageDeviceMismatchAlarm())));
        if (vehicleDataObj.getIsHistoryPoi() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("isHistoryPoi"), Bytes.toBytes(String.valueOf(vehicleDataObj.getIsHistoryPoi())));
        if (vehicleDataObj.getVehiclePureDeviceTypeOvercharge() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("vehiclePureDeviceTypeOvercharge"), Bytes.toBytes(String.valueOf(vehicleDataObj.getVehiclePureDeviceTypeOvercharge())));
        if (vehicleDataObj.getMaxVoltageBattery() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("maxVoltageBattery"), Bytes.toBytes(String.valueOf(vehicleDataObj.getMaxVoltageBattery())));
        if (vehicleDataObj.getDcdcTemperatureAlarm() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("dcdcTemperatureAlarm"), Bytes.toBytes(String.valueOf(vehicleDataObj.getDcdcTemperatureAlarm())));
        if (!vehicleDataObj.getIsValidGps().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("isValidGps"), Bytes.toBytes(vehicleDataObj.getIsValidGps()));
        if (!vehicleDataObj.getLastUpdatedTime().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("lastUpdatedTime"), Bytes.toBytes(vehicleDataObj.getLastUpdatedTime()));
        if (vehicleDataObj.getDriveMotorControllerTemperatureAlarm() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("driveMotorControllerTemperatureAlarm"), Bytes.toBytes(String.valueOf(vehicleDataObj.getDriveMotorControllerTemperatureAlarm())));
        if (!vehicleDataObj.getProbeTemperatures().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("probeTemperatures"), Bytes.toBytes(vehicleDataObj.getProbeTemperatures()));
        if (vehicleDataObj.getChargeTemperatureProbeNum() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("chargeTemperatureProbeNum"), Bytes.toBytes(String.valueOf(vehicleDataObj.getChargeTemperatureProbeNum())));
        if (vehicleDataObj.getIgniteCumulativeMileage() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("igniteCumulativeMileage"), Bytes.toBytes(String.valueOf(vehicleDataObj.getIgniteCumulativeMileage())));
        if (vehicleDataObj.getDcStatus() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("dcStatus"), Bytes.toBytes(String.valueOf(vehicleDataObj.getDcStatus())));
        if (!vehicleDataObj.getRepay().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("repay"), Bytes.toBytes(vehicleDataObj.getRepay()));
        if (vehicleDataObj.getMaxTemperatureSubSystemNum() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("maxTemperatureSubSystemNum"), Bytes.toBytes(String.valueOf(vehicleDataObj.getMaxTemperatureSubSystemNum())));
        if (vehicleDataObj.getCarStatus() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("carStatus"), Bytes.toBytes(String.valueOf(vehicleDataObj.getCarStatus())));
        if (vehicleDataObj.getMinVoltageBatterySubSystemNum() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("minVoltageBatterySubSystemNum"), Bytes.toBytes(String.valueOf(vehicleDataObj.getMinVoltageBatterySubSystemNum())));
        if (vehicleDataObj.getHeading() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("heading"), Bytes.toBytes(String.valueOf(vehicleDataObj.getHeading())));
        if (vehicleDataObj.getDriveMotorFaultCount() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("driveMotorFaultCount"), Bytes.toBytes(String.valueOf(vehicleDataObj.getDriveMotorFaultCount())));
        if (!vehicleDataObj.getTuid().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("tuid"), Bytes.toBytes(vehicleDataObj.getTuid()));
        if (vehicleDataObj.getEnergyRecoveryStatus() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("energyRecoveryStatus"), Bytes.toBytes(String.valueOf(vehicleDataObj.getEnergyRecoveryStatus())));
        if (vehicleDataObj.getFireStatus() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("fireStatus"), Bytes.toBytes(String.valueOf(vehicleDataObj.getFireStatus())));
        if (!vehicleDataObj.getTargetType().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("targetType"), Bytes.toBytes(vehicleDataObj.getTargetType()));
        if (vehicleDataObj.getMaxTemperatureProbe() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("maxTemperatureProbe"), Bytes.toBytes(String.valueOf(vehicleDataObj.getMaxTemperatureProbe())));
        if (!vehicleDataObj.getRechargeableStorageDevicesFaultCodes().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("rechargeableStorageDevicesFaultCodes"), Bytes.toBytes(vehicleDataObj.getRechargeableStorageDevicesFaultCodes()));
        if (vehicleDataObj.getCarMode() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("carMode"), Bytes.toBytes(String.valueOf(vehicleDataObj.getCarMode())));
        if (vehicleDataObj.getHighVoltageInterlockStateAlarm() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("highVoltageInterlockStateAlarm"), Bytes.toBytes(String.valueOf(vehicleDataObj.getHighVoltageInterlockStateAlarm())));
        if (vehicleDataObj.getInsulationAlarm() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("insulationAlarm"), Bytes.toBytes(String.valueOf(vehicleDataObj.getInsulationAlarm())));
        if (vehicleDataObj.getMileageInformation() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("mileageInformation"), Bytes.toBytes(String.valueOf(vehicleDataObj.getMileageInformation())));
        if (vehicleDataObj.getMaxTemperatureValue() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("maxTemperatureValue"), Bytes.toBytes(String.valueOf(vehicleDataObj.getMaxTemperatureValue())));
        if (vehicleDataObj.getOtherFaultCodes().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("otherFaultCodes"), Bytes.toBytes(vehicleDataObj.getOtherFaultCodes()));
        if (vehicleDataObj.getRemainPower() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("remainPower"), Bytes.toBytes(String.valueOf(vehicleDataObj.getRemainPower())));
        if (vehicleDataObj.getInsulateResistance() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("insulateResistance"), Bytes.toBytes(String.valueOf(vehicleDataObj.getInsulateResistance())));
        if (vehicleDataObj.getBatteryLowTemperatureHeater() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("batteryLowTemperatureHeater"), Bytes.toBytes(String.valueOf(vehicleDataObj.getBatteryLowTemperatureHeater())));
        if (!vehicleDataObj.getFuelConsumption100km().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("fuelConsumption100km"), Bytes.toBytes(vehicleDataObj.getFuelConsumption100km()));
        if (!vehicleDataObj.getFuelConsumption().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("fuelConsumption"), Bytes.toBytes(vehicleDataObj.getFuelConsumption()));
        if (!vehicleDataObj.getEngineSpeed().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("engineSpeed"), Bytes.toBytes(vehicleDataObj.getEngineSpeed()));
        if (!vehicleDataObj.getEngineStatus().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("engineStatus"), Bytes.toBytes(vehicleDataObj.getEngineStatus()));
        if (vehicleDataObj.getTrunk() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("trunk"), Bytes.toBytes(String.valueOf(vehicleDataObj.getTrunk())));
        if (vehicleDataObj.getLowBeam() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("lowBeam"), Bytes.toBytes(String.valueOf(vehicleDataObj.getLowBeam())));
        if (!vehicleDataObj.getTriggerLatchOverheatProtect().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("triggerLatchOverheatProtect"), Bytes.toBytes(vehicleDataObj.getTriggerLatchOverheatProtect()));
        if (vehicleDataObj.getTurnLndicatorRight() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("turnLndicatorRight"), Bytes.toBytes(String.valueOf(vehicleDataObj.getTurnLndicatorRight())));
        if (vehicleDataObj.getHighBeam() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("highBeam"), Bytes.toBytes(String.valueOf(vehicleDataObj.getHighBeam())));
        if (vehicleDataObj.getTurnLndicatorLeft() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("turnLndicatorLeft"), Bytes.toBytes(String.valueOf(vehicleDataObj.getTurnLndicatorLeft())));
        if (vehicleDataObj.getBcuSwVers() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("bcuSwVers"), Bytes.toBytes(String.valueOf(vehicleDataObj.getBcuSwVers())));
        if (vehicleDataObj.getBcuHwVers() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("bcuHwVers"), Bytes.toBytes(String.valueOf(vehicleDataObj.getBcuHwVers())));
        if (vehicleDataObj.getBcuOperMod() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("bcuOperMod"), Bytes.toBytes(String.valueOf(vehicleDataObj.getBcuOperMod())));
        if (vehicleDataObj.getChrgEndReason() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("chrgEndReason"), Bytes.toBytes(String.valueOf(vehicleDataObj.getChrgEndReason())));
        if (!vehicleDataObj.getBCURegenEngDisp().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("BCURegenEngDisp"), Bytes.toBytes(vehicleDataObj.getBCURegenEngDisp()));
        if (vehicleDataObj.getBCURegenCpDisp() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("BCURegenCpDisp"), Bytes.toBytes(String.valueOf(vehicleDataObj.getBCURegenCpDisp())));
        if (vehicleDataObj.getBcuChrgMod() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("bcuChrgMod"), Bytes.toBytes(String.valueOf(vehicleDataObj.getBcuChrgMod())));
        if (vehicleDataObj.getBatteryChargeStatus() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("batteryChargeStatus"), Bytes.toBytes(String.valueOf(vehicleDataObj.getBatteryChargeStatus())));
        if (!vehicleDataObj.getBcuFaultCodes().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("BcuFaultCodes"), Bytes.toBytes(vehicleDataObj.getBcuFaultCodes()));
        if (vehicleDataObj.getBcuFltRnk() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("bcuFltRnk"), Bytes.toBytes(String.valueOf(vehicleDataObj.getBcuFltRnk())));
        if (!vehicleDataObj.getBattPoleTOver().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("battPoleTOver"), Bytes.toBytes(vehicleDataObj.getBattPoleTOver()));
        if (vehicleDataObj.getBcuSOH() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("bcuSOH"), Bytes.toBytes(String.valueOf(vehicleDataObj.getBcuSOH())));
        if (vehicleDataObj.getBattIntrHeatActive() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("battIntrHeatActive"), Bytes.toBytes(String.valueOf(vehicleDataObj.getBattIntrHeatActive())));
        if (vehicleDataObj.getBattIntrHeatReq() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("battIntrHeatReq"), Bytes.toBytes(String.valueOf(vehicleDataObj.getBattIntrHeatReq())));
        if (!vehicleDataObj.getBCUBattTarT().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("BCUBattTarT"), Bytes.toBytes(vehicleDataObj.getBCUBattTarT()));
        if (vehicleDataObj.getBattExtHeatReq() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("battExtHeatReq"), Bytes.toBytes(String.valueOf(vehicleDataObj.getBattExtHeatReq())));
        if (!vehicleDataObj.getBCUMaxChrgPwrLongT().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("BCUMaxChrgPwrLongT"), Bytes.toBytes(vehicleDataObj.getBCUMaxChrgPwrLongT()));
        if (!vehicleDataObj.getBCUMaxDchaPwrLongT().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("BCUMaxDchaPwrLongT"), Bytes.toBytes(vehicleDataObj.getBCUMaxDchaPwrLongT()));
        if (!vehicleDataObj.getBCUTotalRegenEngDisp().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("BCUTotalRegenEngDisp"), Bytes.toBytes(vehicleDataObj.getBCUTotalRegenEngDisp()));
        if (!vehicleDataObj.getBCUTotalRegenCpDisp().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("BCUTotalRegenCpDisp"), Bytes.toBytes(vehicleDataObj.getBCUTotalRegenCpDisp()));
        if (vehicleDataObj.getDcdcFltRnk() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("dcdcFltRnk"), Bytes.toBytes(String.valueOf(vehicleDataObj.getDcdcFltRnk())));
        if (!vehicleDataObj.getDcdcFaultCode().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("DcdcFaultCode"), Bytes.toBytes(vehicleDataObj.getDcdcFaultCode()));
        if (vehicleDataObj.getDcdcOutpCrrt() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("dcdcOutpCrrt"), Bytes.toBytes(String.valueOf(vehicleDataObj.getDcdcOutpCrrt())));
        if (vehicleDataObj.getDcdcOutpU() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("dcdcOutpU"), Bytes.toBytes(String.valueOf(vehicleDataObj.getDcdcOutpU())));
        if (vehicleDataObj.getDcdcAvlOutpPwr() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("dcdcAvlOutpPwr"), Bytes.toBytes(String.valueOf(vehicleDataObj.getDcdcAvlOutpPwr())));
        if (!vehicleDataObj.getAbsActiveStatus().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("absActiveStatus"), Bytes.toBytes(vehicleDataObj.getAbsActiveStatus()));
        if (!vehicleDataObj.getAbsStatus().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("absStatus"), Bytes.toBytes(vehicleDataObj.getAbsStatus()));
        if (!vehicleDataObj.getVcuBrkErr().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("VcuBrkErr"), Bytes.toBytes(vehicleDataObj.getVcuBrkErr()));
        if (!vehicleDataObj.getEPB_AchievedClampForce().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("EPB_AchievedClampForce"), Bytes.toBytes(vehicleDataObj.getEPB_AchievedClampForce()));
        if (!vehicleDataObj.getEpbSwitchPosition().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("epbSwitchPosition"), Bytes.toBytes(vehicleDataObj.getEpbSwitchPosition()));
        if (!vehicleDataObj.getEpbStatus().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("epbStatus"), Bytes.toBytes(vehicleDataObj.getEpbStatus()));
        if (!vehicleDataObj.getEspActiveStatus().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("espActiveStatus"), Bytes.toBytes(vehicleDataObj.getEspActiveStatus()));
        if (!vehicleDataObj.getEspFunctionStatus().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("espFunctionStatus"), Bytes.toBytes(vehicleDataObj.getEspFunctionStatus()));
        if (!vehicleDataObj.getESP_TCSFailStatus().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("ESP_TCSFailStatus"), Bytes.toBytes(vehicleDataObj.getESP_TCSFailStatus()));
        if (!vehicleDataObj.getHhcActive().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("hhcActive"), Bytes.toBytes(vehicleDataObj.getHhcActive()));
        if (!vehicleDataObj.getTcsActive().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("tcsActive"), Bytes.toBytes(vehicleDataObj.getTcsActive()));
        if (!vehicleDataObj.getEspMasterCylinderBrakePressure().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("espMasterCylinderBrakePressure"), Bytes.toBytes(vehicleDataObj.getEspMasterCylinderBrakePressure()));
        if (!vehicleDataObj.getESP_MasterCylinderBrakePressureValid().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("ESP_MasterCylinderBrakePressureValid"), Bytes.toBytes(vehicleDataObj.getESP_MasterCylinderBrakePressureValid()));
        if (!vehicleDataObj.getEspTorqSensorStatus().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("espTorqSensorStatus"), Bytes.toBytes(vehicleDataObj.getEspTorqSensorStatus()));
        if (!vehicleDataObj.getEPS_EPSFailed().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("EPS_EPSFailed"), Bytes.toBytes(vehicleDataObj.getEPS_EPSFailed()));
        if (!vehicleDataObj.getSasFailure().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("sasFailure"), Bytes.toBytes(vehicleDataObj.getSasFailure()));
        if (!vehicleDataObj.getSasSteeringAngleSpeed().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("sasSteeringAngleSpeed"), Bytes.toBytes(vehicleDataObj.getSasSteeringAngleSpeed()));
        if (!vehicleDataObj.getSasSteeringAngle().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("sasSteeringAngle"), Bytes.toBytes(vehicleDataObj.getSasSteeringAngle()));
        if (!vehicleDataObj.getSasSteeringAngleValid().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("sasSteeringAngleValid"), Bytes.toBytes(vehicleDataObj.getSasSteeringAngleValid()));
        if (!vehicleDataObj.getEspSteeringTorque().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("espSteeringTorque"), Bytes.toBytes(vehicleDataObj.getEspSteeringTorque()));
        if (vehicleDataObj.getAcReq() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("acReq"), Bytes.toBytes(String.valueOf(vehicleDataObj.getAcReq())));
        if (vehicleDataObj.getAcSystemFailure() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("acSystemFailure"), Bytes.toBytes(String.valueOf(vehicleDataObj.getAcSystemFailure())));
        if (vehicleDataObj.getPtcPwrAct() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("ptcPwrAct"), Bytes.toBytes(String.valueOf(vehicleDataObj.getPtcPwrAct())));
        if (vehicleDataObj.getPlasmaStatus() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("plasmaStatus"), Bytes.toBytes(String.valueOf(vehicleDataObj.getPlasmaStatus())));
        if (vehicleDataObj.getBattInTemperature() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("battInTemperature"), Bytes.toBytes(String.valueOf(vehicleDataObj.getBattInTemperature())));
        if (!vehicleDataObj.getBattWarmLoopSts().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("battWarmLoopSts"), Bytes.toBytes(vehicleDataObj.getBattWarmLoopSts()));
        if (!vehicleDataObj.getBattCoolngLoopSts().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("battCoolngLoopSts"), Bytes.toBytes(vehicleDataObj.getBattCoolngLoopSts()));
        if (!vehicleDataObj.getBattCoolActv().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("battCoolActv"), Bytes.toBytes(vehicleDataObj.getBattCoolActv()));
        if (vehicleDataObj.getMotorOutTemperature() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("motorOutTemperature"), Bytes.toBytes(String.valueOf(vehicleDataObj.getMotorOutTemperature())));
        if (!vehicleDataObj.getPowerStatusFeedBack().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("powerStatusFeedBack"), Bytes.toBytes(String.valueOf(vehicleDataObj.getPowerStatusFeedBack())));
        if (vehicleDataObj.getAC_RearDefrosterSwitch() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("AC_RearDefrosterSwitch"), Bytes.toBytes(String.valueOf(vehicleDataObj.getAC_RearDefrosterSwitch())));
        if (vehicleDataObj.getRearFoglamp() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("rearFoglamp"), Bytes.toBytes(String.valueOf(vehicleDataObj.getRearFoglamp())));
        if (vehicleDataObj.getDriverDoorLock() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("driverDoorLock"), Bytes.toBytes(String.valueOf(vehicleDataObj.getDriverDoorLock())));
        if (vehicleDataObj.getAcDriverReqTemp() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("acDriverReqTemp"), Bytes.toBytes(String.valueOf(vehicleDataObj.getAcDriverReqTemp())));
        if (vehicleDataObj.getKeyAlarm() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("keyAlarm"), Bytes.toBytes(String.valueOf(vehicleDataObj.getKeyAlarm())));
        if (vehicleDataObj.getAirCleanStsRemind() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("airCleanStsRemind"), Bytes.toBytes(String.valueOf(vehicleDataObj.getAirCleanStsRemind())));
        if (vehicleDataObj.getRecycleType() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("recycleType"), Bytes.toBytes(String.valueOf(vehicleDataObj.getRecycleType())));
        if (!vehicleDataObj.getStartControlsignal().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("startControlsignal"), Bytes.toBytes(vehicleDataObj.getStartControlsignal()));
        if (vehicleDataObj.getAirBagWarningLamp() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("airBagWarningLamp"), Bytes.toBytes(String.valueOf(vehicleDataObj.getAirBagWarningLamp())));
        if (vehicleDataObj.getFrontDefrosterSwitch() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("frontDefrosterSwitch"), Bytes.toBytes(String.valueOf(vehicleDataObj.getFrontDefrosterSwitch())));
        if (!vehicleDataObj.getFrontBlowType().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("frontBlowType"), Bytes.toBytes(vehicleDataObj.getFrontBlowType()));
        if (vehicleDataObj.getFrontReqWindLevel() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("frontReqWindLevel"), Bytes.toBytes(String.valueOf(vehicleDataObj.getFrontReqWindLevel())));
        if (!vehicleDataObj.getBcmFrontWiperStatus().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("bcmFrontWiperStatus"), Bytes.toBytes(vehicleDataObj.getBcmFrontWiperStatus()));
        if (!vehicleDataObj.getTmsPwrAct().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("tmsPwrAct"), Bytes.toBytes(vehicleDataObj.getTmsPwrAct()));
        if (vehicleDataObj.getKeyUndetectedAlarmSign() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("keyUndetectedAlarmSign"), Bytes.toBytes(String.valueOf(vehicleDataObj.getKeyUndetectedAlarmSign())));
        if (!vehicleDataObj.getPositionLamp().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("positionLamp"), Bytes.toBytes(vehicleDataObj.getPositionLamp()));
        if (vehicleDataObj.getDriverReqTempModel() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("driverReqTempModel"), Bytes.toBytes(String.valueOf(vehicleDataObj.getDriverReqTempModel())));
        if (vehicleDataObj.getTurnLightSwitchSts() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("turnLightSwitchSts"), Bytes.toBytes(String.valueOf(vehicleDataObj.getTurnLightSwitchSts())));
        if (vehicleDataObj.getAutoHeadlightStatus() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("autoHeadlightStatus"), Bytes.toBytes(String.valueOf(vehicleDataObj.getAutoHeadlightStatus())));
        if (vehicleDataObj.getDriverDoor() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("driverDoor"), Bytes.toBytes(String.valueOf(vehicleDataObj.getDriverDoor())));
        if (!vehicleDataObj.getIpuFaultCodes().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("IpuFaultCodes"), Bytes.toBytes(vehicleDataObj.getIpuFaultCodes()));
        if (vehicleDataObj.getFrntIpuFltRnk() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("frntIpuFltRnk"), Bytes.toBytes(String.valueOf(vehicleDataObj.getFrntIpuFltRnk())));
        if (!vehicleDataObj.getFrontIpuSwVers().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("frontIpuSwVers"), Bytes.toBytes(vehicleDataObj.getFrontIpuSwVers()));
        if (vehicleDataObj.getFrontIpuHwVers() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("frontIpuHwVers"), Bytes.toBytes(String.valueOf(vehicleDataObj.getFrontIpuHwVers())));
        if (vehicleDataObj.getFrntMotTqLongTermMax() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("frntMotTqLongTermMax"), Bytes.toBytes(String.valueOf(vehicleDataObj.getFrntMotTqLongTermMax())));
        if (vehicleDataObj.getFrntMotTqLongTermMin() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("frntMotTqLongTermMin"), Bytes.toBytes(String.valueOf(vehicleDataObj.getFrntMotTqLongTermMin())));
        if (vehicleDataObj.getCpvValue() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("cpvValue"), Bytes.toBytes(String.valueOf(vehicleDataObj.getCpvValue())));
        if (vehicleDataObj.getObcChrgSts() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("obcChrgSts"), Bytes.toBytes(String.valueOf(vehicleDataObj.getObcChrgSts())));
        if (!vehicleDataObj.getObcFltRnk().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("obcFltRnk"), Bytes.toBytes(vehicleDataObj.getObcFltRnk()));
        if (vehicleDataObj.getObcChrgInpAcI() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("obcChrgInpAcI"), Bytes.toBytes(String.valueOf(vehicleDataObj.getObcChrgInpAcI())));
        if (vehicleDataObj.getObcChrgInpAcU() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("obcChrgInpAcU"), Bytes.toBytes(String.valueOf(vehicleDataObj.getObcChrgInpAcU())));
        if (vehicleDataObj.getObcChrgDcI() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("obcChrgDcI"), Bytes.toBytes(String.valueOf(vehicleDataObj.getObcChrgDcI())));
        if (vehicleDataObj.getObcChrgDcU() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("obcChrgDcU"), Bytes.toBytes(String.valueOf(vehicleDataObj.getObcChrgDcU())));
        if (vehicleDataObj.getObcTemperature() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("obcTemperature"), Bytes.toBytes(String.valueOf(vehicleDataObj.getObcTemperature())));
        if (vehicleDataObj.getObcMaxChrgOutpPwrAvl() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("obcMaxChrgOutpPwrAvl"), Bytes.toBytes(String.valueOf(vehicleDataObj.getObcMaxChrgOutpPwrAvl())));
        if (vehicleDataObj.getPassengerBuckleSwitch() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("passengerBuckleSwitch"), Bytes.toBytes(String.valueOf(vehicleDataObj.getPassengerBuckleSwitch())));
        if (!vehicleDataObj.getCrashlfo().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("crashlfo"), Bytes.toBytes(vehicleDataObj.getCrashlfo()));
        if (vehicleDataObj.getDriverBuckleSwitch() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("driverBuckleSwitch"), Bytes.toBytes(String.valueOf(vehicleDataObj.getDriverBuckleSwitch())));
        if (!vehicleDataObj.getEngineStartHibit().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("engineStartHibit"), Bytes.toBytes(vehicleDataObj.getEngineStartHibit()));
        if (!vehicleDataObj.getLockCommand().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("lockCommand"), Bytes.toBytes(vehicleDataObj.getLockCommand()));
        if (!vehicleDataObj.getSearchCarReq().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("searchCarReq"), Bytes.toBytes(vehicleDataObj.getSearchCarReq()));
        if (!vehicleDataObj.getAcTempValueReq().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("acTempValueReq"), Bytes.toBytes(vehicleDataObj.getAcTempValueReq()));
        if (!vehicleDataObj.getVcuFaultCode().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("VcuFaultCode"), Bytes.toBytes(vehicleDataObj.getVcuFaultCode()));
        if (!vehicleDataObj.getVcuErrAmnt().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("vcuErrAmnt"), Bytes.toBytes(vehicleDataObj.getVcuErrAmnt()));
        if (vehicleDataObj.getVcuSwVers() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("vcuSwVers"), Bytes.toBytes(String.valueOf(vehicleDataObj.getVcuSwVers())));
        if (vehicleDataObj.getVcuHwVers() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("vcuHwVers"), Bytes.toBytes(String.valueOf(vehicleDataObj.getVcuHwVers())));
        if (!vehicleDataObj.getLowSpdWarnStatus().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("lowSpdWarnStatus"), Bytes.toBytes(vehicleDataObj.getLowSpdWarnStatus()));
        if (vehicleDataObj.getLowBattChrgRqe() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("lowBattChrgRqe"), Bytes.toBytes(String.valueOf(vehicleDataObj.getLowBattChrgRqe())));
        if (!vehicleDataObj.getLowBattChrgSts().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("lowBattChrgSts"), Bytes.toBytes(vehicleDataObj.getLowBattChrgSts()));
        if (vehicleDataObj.getLowBattU() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("lowBattU"), Bytes.toBytes(String.valueOf(vehicleDataObj.getLowBattU())));
        if (vehicleDataObj.getHandlebrakeStatus() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("handlebrakeStatus"), Bytes.toBytes(String.valueOf(vehicleDataObj.getHandlebrakeStatus())));
        if (!vehicleDataObj.getShiftPositionValid().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("shiftPositionValid"), Bytes.toBytes(vehicleDataObj.getShiftPositionValid()));
        if (!vehicleDataObj.getAccPedalValid().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("accPedalValid"), Bytes.toBytes(vehicleDataObj.getAccPedalValid()));
        if (vehicleDataObj.getDriveMode() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("driveMode"), Bytes.toBytes(String.valueOf(vehicleDataObj.getDriveMode())));
        if (vehicleDataObj.getDriveModeButtonStatus() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("driveModeButtonStatus"), Bytes.toBytes(String.valueOf(vehicleDataObj.getDriveModeButtonStatus())));
        if (vehicleDataObj.getVCUSRSCrashOutpSts() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("VCUSRSCrashOutpSts"), Bytes.toBytes(String.valueOf(vehicleDataObj.getVCUSRSCrashOutpSts())));
        if (vehicleDataObj.getTextDispEna() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("textDispEna"), Bytes.toBytes(String.valueOf(vehicleDataObj.getTextDispEna())));
        if (vehicleDataObj.getCrsCtrlStatus() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("crsCtrlStatus"), Bytes.toBytes(String.valueOf(vehicleDataObj.getCrsCtrlStatus())));
        if (vehicleDataObj.getCrsTarSpd() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("crsTarSpd"), Bytes.toBytes(String.valueOf(vehicleDataObj.getCrsTarSpd())));
        if (vehicleDataObj.getCrsTextDisp() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("crsTextDisp"), Bytes.toBytes(String.valueOf(vehicleDataObj.getCrsTextDisp())));
        if (vehicleDataObj.getKeyOn() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("keyOn"), Bytes.toBytes(String.valueOf(vehicleDataObj.getKeyOn())));
        if (vehicleDataObj.getVehPwrlim() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("vehPwrlim"), Bytes.toBytes(String.valueOf(vehicleDataObj.getVehPwrlim())));
        if (!vehicleDataObj.getVehCfgInfo().isEmpty()) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("vehCfgInfo"), Bytes.toBytes(vehicleDataObj.getVehCfgInfo()));
        if (vehicleDataObj.getVacBrkPRmu() != -999999) put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("vacBrkPRmu"), Bytes.toBytes(vehicleDataObj.getVacBrkPRmu()));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("processTime"), Bytes.toBytes(DateUtil.getCurrentDateTime()));
        return put;
    }

    /**
     *  重写close方法：销毁对象，释放资源
     *  遵循“先创建后关闭“原则
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        if (mutator != null) {
            // 也会强制缓冲区提交数据
            mutator.close();
        }
        if (conn != null || !conn.isClosed()) {
            conn.close();
        }
    }
}
