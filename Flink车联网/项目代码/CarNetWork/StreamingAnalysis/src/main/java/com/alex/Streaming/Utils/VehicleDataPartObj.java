package com.alex.Streaming.Utils;

/**
 * @Auther: alex
 * @Date: 2022/12/17 11:29
 * @Description:  定义原始数据中json对象对应的部分所需字段对象
 */
public class VehicleDataPartObj {
    //电池单体一致性差报警	0：正常 1：异常--
    private int batteryConsistencyDifferenceAlarm = -999999;
    //SOC	%--
    private int soc = -999999;
    //SOC跳变报警	0：正常 1：异常--
    private int socJumpAlarm = -999999;
    //SOC低报警	0：正常 1：异常--
    private int socLowAlarm = -999999;
    //终端时间--
    private String terminalTime = "";
    //单体电池过压报警	0：正常 1：异常--
    private int singleBatteryOverVoltageAlarm = -999999;
    //车载储能装置过压报警	0：正常 1：异常--
    private int vehicleStorageDeviceOvervoltageAlarm = -999999;
    //制动系统报警	0：正常 1：异常--
    private int brakeSystemAlarm = -999999;
    //车辆唯一编号--
    private String vin = "";
    //"0x01: 停车充电 0x02: 行车充电 0x03: 未充电  0x04:充电完成 0xFE: 异常 0xFF:无效"
    private int chargeStatus = -999999;
    //驱动电机温度报警	0：正常 1：异常--
    private int driveMotorTemperatureAlarm = -999999;
    //DC-DC状态报警	0：正常 1：异常--
    private int dcdcStatusAlarm = -999999;
    //位置时间
    private String gpsTime = "";
    //位置纬度--
    private Double lat = -999999D;
    //驱动电机控制器温度报警	0：正常 1：异常
    private int driveMotorFaultCount = -999999;
    //车速--
    private Double vehicleSpeed = -999999D;
    //位置经度--
    private Double lng = -999999D;
    //可充电储能装置电压	V--
    private Double chargeSystemVoltage = -999999D;
    //可充电储能装置电流	A--
    private Double chargeSystemCurrent = -999999D;
    //单体电池欠压报警	0：正常 1：异常--
    private int singleBatteryUnderVoltageAlarm = -999999;
    //总电压	单位：V，实际取值0.1~100V
    private Double totalVoltage = -999999D;
    //温度差异报警	0：正常 1：异常--
    private int temperatureDifferenceAlarm = -999999;
    //--电池单体电压最低值	单位：V，实际值=传输值*0.001，即实际为0~15V。0xFF,0xFE表示异常，0xFF,0xFF表示无效
    private Double minVoltageBattery = -999999D;
    //GPS是否有效（可忽略）
    private String validGps = "";
    //累计里程	单位：km--
    private Double totalOdometer = -999999D;
    //车速（可忽略）	单位：km/h
    private Double speed = -999999D;
    //SOC过高报警	0：正常 1：异常--
    private int socHighAlarm = -999999;
    //车载储能装置欠压报警	0：正常 1：异常--
    private int vehicleStorageDeviceUndervoltageAlarm = -999999;
    //总电流	单位：A，实际值 = 传输值 * 0.1-1000，即实际取值为-1000~1000A
    private Double totalCurrent = -999999D;
    //电池高温报警	0：正常 1：异常--
    private int batteryAlarm = -999999;
    //可充电储能系统不匹配报警	0：正常 1：异常--
    private int rechargeableStorageDeviceMismatchAlarm = -999999;
    //车载储能装置类型过充报警	0：正常 1：异常--
    private int vehiclePureDeviceTypeOvercharge = -999999;
    //--电池单体电压最高值	单位：V，实际值=传输值*0.001，即实际为0~15V。0xFF,0xFE表示异常，0xFF,0xFF表示无效
    private Double maxVoltageBattery = -999999D;
    //DC-DC温度报警	0：正常 1：异常--
    private int dcdcTemperatureAlarm = -999999;
    //同validGps（可忽略）
    private String isValidGps = "";
    //驱动电机控制器温度报警	0：正常 1：异常
    private int driveMotorControllerTemperatureAlarm = -999999;
    //是否补发	TRUE:补发数据 ； FALSE:实时数据
    private String repay = "";
    //车辆状态	0x01: 车辆启动状态，0x02：熄火状态 0x03：其他状态，0xFE：异常，0xFF：无效
    private int carStatus = -999999;
    //前电机故障代码 json数据中，ecuErrCodeDataList数组中ecuType=4--
    private String IpuFaultCodes = "";
    //能量回收状态	高/低
    private int energyRecoveryStatus = -999999;
    //点火状态	0：未点火 ；2：已点火
    private int fireStatus = -999999;
    //运行模式	0x01: 纯电 0x02 混动 0x03 燃油 0xFE: 异常 0xFF: 无效
    private int carMode = -999999;
    //高压互锁状态报警	0：正常 1：异常--
    private int highVoltageInterlockStateAlarm = -999999;
    //绝缘报警	0：正常 1：异常--
    private int insulationAlarm = -999999;
    //续航里程信息	单位：km--
    private int mileageInformation = -999999;
    //当前电量	%
    private Double remainPower = -999999D;
    //发动机状态	0：stop  1：crank   2：running
    private String engineStatus = "";
    //ABS故障 "0x0:No error 0x1:Error"--
    private String absStatus = "";
    //EPB故障状态 "0x0:no error 0x1:not defined 0x2:not defined 0x3:error"--
    private String VcuBrkErr = "";
    //ESP故障 "0x0:No error 0x1:Error"--
    private String ESP_TCSFailStatus = "";
    //助力转向故障 "0x0:No Failed 0x1:Failed"--
    private String EPS_EPSFailed = "";
    //电池故障代码 json数据中，ecuErrCodeDataList数组中ecuType=2--
    private String BcuFaultCodes = "";
    //VCU故障代码 json数据中，ecuErrCodeDataList数组中ecuType=1--
    private String VcuFaultCode = "";
    /**
     * --充电机故障码  "0x0:当前无故障 0x1:12V电池电压过高 0x2:12V电池电压过低 0x3:CP内部6V电压异常 0x4:CP内部9V电压异常
     * 0x5:CP内部频率异常 0x6:CP内部占空比异常 0x7:CAN收发器异常 0x8:内部SCI通信失败 0x9:内部SCICRC错误 0xA:输出过压关机
     * 0xB:输出低压关机 0xC:交流输入低压关机 0xD:输入过压关机 0xE:环境温度过低关机 0xF:环境温度过高关机
     * 0x10:充电机PFC电压欠压 0x11:输入过载 0x12:输出过载 0x13:自检故障 0x14:外部CANbusoff 0x15:内部CANbusoff
     * 0x16:外部CAN通信超时 0x17:外部CAN使能超时 0x18:外部CAN通信错误 0x19:输出短路 0x1A:充电参数错误
     * 0x1B:充电机PFC电压过压 0x1C:内部SCI通信失败 0x1D:过功率 0x1E:PFC电感过温 0x1F:LLC变压器过温 0x20:M1功率板过温
     * 0x21:PFC温度降额 0x22:LLC温度降额 0x23:M1板温度降额 0x24:Air环境温度降额"
     * json数据中，ecuErrCodeDataList数组中ecuType=5
     */
    private String ObcFaultCode  = "";
    //最高温度值	单位：℃。实际值：-40~210。0xFE表示异常，1xFF表示无效
    private Double maxTemperatureValue = -999999D;
    //最低温度值	单位：℃。实际值：-40~210。0xFE表示异常，1xFF表示无效
    private Double minTemperatureValue = -999999D;
    //DCDC故障码 json数据中，ecuErrCodeDataList数组中ecuType=3
    private String DcdcFaultCode = "";
    //电池极注高温报警 "0x0:Noerror 0x1:error"
    private String battPoleTOver = "";
    //AC系统故障 "0x0:NotFailure 0x1:Failure"
    private int acSystemFailure = -999999;
    //气囊系统报警灯状态  "0x0:Lamp off-no failure 0x1:Lamp on-no failure 0x2:Lamp flashing-no failure 0x3:Failure-failure present"
    private int airBagWarningLamp = -999999;
    //电池充电状态 "0x0:uncharged 0x1:charging 0x2:fullofcharge 0x3:chargeend"
    private int batteryChargeStatus = -999999;
    //充电枪连接状态	0:解锁1:锁定2:失败
    private int chargingGunConnectionState = -999999;
    //单体电池电压列表	Array类型的数组格式
    private String batteryVoltage = "" ;
    //电池模块温度列表
    private String probeTemperatures = "";

    // 扩展字段 终端时间--
    private Long terminalTimeStamp = -999999L;
    //扩展字段，用于存储异常数据
    private String errorData = "";

    /**
     * 通过参数获取相应的参数字段值，用于故障分析
     * @param filed 字段名称
     * @return 字段值
     */
    public String get(String filed) {
        switch (filed) {
            // 6类故障
            case "battPoleTOver":
                return this.battPoleTOver;
            case "absStatus" :
                return this.absStatus;
            case "VcuBrkErr":
                return this.VcuBrkErr;
            case "ESP_TCSFailStatus":
                return this.ESP_TCSFailStatus;
            case "EPS_EPSFailed":
                return this.EPS_EPSFailed;
            case "acSystemFailure":
                return String.valueOf(this.acSystemFailure);
            // 19类故障
            case "batteryAlarm":
                return String.valueOf(this.batteryAlarm);
            case "singleBatteryOverVoltageAlarm":
                return String.valueOf(this.singleBatteryOverVoltageAlarm);
            case "batteryConsistencyDifferenceAlarm":
                return String.valueOf(this.batteryConsistencyDifferenceAlarm);
            case "insulationAlarm":
                String.valueOf(this.insulationAlarm);
            case "highVoltageInterlockStateAlarm":
                return String.valueOf(this.highVoltageInterlockStateAlarm);
            case "socJumpAlarm":
                return String.valueOf(this.socJumpAlarm);
            case "driveMotorControllerTemperatureAlarm":
                return String.valueOf(this.driveMotorControllerTemperatureAlarm);
            case "dcdcTemperatureAlarm"                   :
                return String.valueOf(this.dcdcTemperatureAlarm);
            case "socHighAlarm"                           :
                return String.valueOf(this.socHighAlarm);
            case "socLowAlarm"                            :
                return String.valueOf(this.socLowAlarm);
            case "temperatureDifferenceAlarm"             :
                return String.valueOf(this.temperatureDifferenceAlarm);
            case "vehicleStorageDeviceUndervoltageAlarm"  :
                return String.valueOf(this.vehicleStorageDeviceUndervoltageAlarm);
            case "dcdcStatusAlarm"                        :
                return String.valueOf(this.dcdcStatusAlarm);
            case "singleBatteryUnderVoltageAlarm"         :
                return String.valueOf(this.singleBatteryUnderVoltageAlarm);
            case "rechargeableStorageDeviceMismatchAlarm" :
                return String.valueOf(this.rechargeableStorageDeviceMismatchAlarm);
            case "vehicleStorageDeviceOvervoltageAlarm"   :
                return String.valueOf(this.vehicleStorageDeviceOvervoltageAlarm);
            case "brakeSystemAlarm"                       :
                return String.valueOf(this.brakeSystemAlarm);
            case "driveMotorTemperatureAlarm"             :
                return String.valueOf(this.driveMotorTemperatureAlarm);
            case "vehiclePureDeviceTypeOvercharge"        :
                return String.valueOf(this.vehiclePureDeviceTypeOvercharge);
                // 5类故障
            case "VcuFaultCode"                           :
                return this.VcuFaultCode;
            case "BcuFaultCodes"                          :
                return this.BcuFaultCodes;
            case "DcdcFaultCode"                          :
                return this.DcdcFaultCode;
            case "IpuFaultCodes"                          :
                return this.IpuFaultCodes;
            case "ObcFaultCode"                           :
                return this.ObcFaultCode;
            default:
                return null;
        }
    }

    /**
     * @desc:定义根据输入字符获取字段值
     * @param str
     * @return double
     */
    public Double getValue(String str) {
        switch (str) {
            case "maxVoltageBattery":
                return this.maxVoltageBattery;
            case "minVoltageBattery":
                return this.minVoltageBattery;
            case "maxTemperatureValue":
                return this.maxTemperatureValue;
            case "minTemperatureValue":
                return this.minTemperatureValue;
            default:
                return 0.0;
        }
    }

    // 重写ItcastDataPartObj对象的toString方法
    @Override
    public String toString() {
        return "VehicleDataPartObj{" +
                "batteryConsistencyDifferenceAlarm=" + batteryConsistencyDifferenceAlarm +
                ", soc=" + soc +
                ", socJumpAlarm=" + socJumpAlarm +
                ", socLowAlarm=" + socLowAlarm +
                ", terminalTime='" + terminalTime + '\'' +
                ", singleBatteryOverVoltageAlarm=" + singleBatteryOverVoltageAlarm +
                ", vehicleStorageDeviceOvervoltageAlarm=" + vehicleStorageDeviceOvervoltageAlarm +
                ", brakeSystemAlarm=" + brakeSystemAlarm +
                ", vin='" + vin + '\'' +
                ", chargeStatus=" + chargeStatus +
                ", driveMotorTemperatureAlarm=" + driveMotorTemperatureAlarm +
                ", dcdcStatusAlarm=" + dcdcStatusAlarm +
                ", gpsTime='" + gpsTime + '\'' +
                ", lat=" + lat +
                ", driveMotorFaultCount=" + driveMotorFaultCount +
                ", vehicleSpeed=" + vehicleSpeed +
                ", lng=" + lng +
                ", chargeSystemVoltage=" + chargeSystemVoltage +
                ", chargeSystemCurrent=" + chargeSystemCurrent +
                ", singleBatteryUnderVoltageAlarm=" + singleBatteryUnderVoltageAlarm +
                ", totalVoltage=" + totalVoltage +
                ", temperatureDifferenceAlarm=" + temperatureDifferenceAlarm +
                ", minVoltageBattery=" + minVoltageBattery +
                ", validGps='" + validGps + '\'' +
                ", totalOdometer=" + totalOdometer +
                ", speed=" + speed +
                ", socHighAlarm=" + socHighAlarm +
                ", vehicleStorageDeviceUndervoltageAlarm=" + vehicleStorageDeviceUndervoltageAlarm +
                ", totalCurrent=" + totalCurrent +
                ", batteryAlarm=" + batteryAlarm +
                ", rechargeableStorageDeviceMismatchAlarm=" + rechargeableStorageDeviceMismatchAlarm +
                ", vehiclePureDeviceTypeOvercharge=" + vehiclePureDeviceTypeOvercharge +
                ", maxVoltageBattery=" + maxVoltageBattery +
                ", dcdcTemperatureAlarm=" + dcdcTemperatureAlarm +
                ", isValidGps='" + isValidGps + '\'' +
                ", driveMotorControllerTemperatureAlarm=" + driveMotorControllerTemperatureAlarm +
                ", repay='" + repay + '\'' +
                ", carStatus=" + carStatus +
                ", IpuFaultCodes='" + IpuFaultCodes + '\'' +
                ", energyRecoveryStatus=" + energyRecoveryStatus +
                ", fireStatus=" + fireStatus +
                ", carMode=" + carMode +
                ", highVoltageInterlockStateAlarm=" + highVoltageInterlockStateAlarm +
                ", insulationAlarm=" + insulationAlarm +
                ", mileageInformation=" + mileageInformation +
                ", remainPower=" + remainPower +
                ", engineStatus='" + engineStatus + '\'' +
                ", absStatus='" + absStatus + '\'' +
                ", VcuBrkErr='" + VcuBrkErr + '\'' +
                ", ESP_TCSFailStatus='" + ESP_TCSFailStatus + '\'' +
                ", EPS_EPSFailed='" + EPS_EPSFailed + '\'' +
                ", BcuFaultCodes='" + BcuFaultCodes + '\'' +
                ", VcuFaultCode='" + VcuFaultCode + '\'' +
                ", ObcFaultCode='" + ObcFaultCode + '\'' +
                ", maxTemperatureValue=" + maxTemperatureValue +
                ", minTemperatureValue=" + minTemperatureValue +
                ", DcdcFaultCode='" + DcdcFaultCode + '\'' +
                ", battPoleTOver='" + battPoleTOver + '\'' +
                ", acSystemFailure=" + acSystemFailure +
                ", airBagWarningLamp=" + airBagWarningLamp +
                ", batteryChargeStatus=" + batteryChargeStatus +
                ", chargingGunConnectionState=" + chargingGunConnectionState +
                ", batteryVoltage='" + batteryVoltage + '\'' +
                ", probeTemperatures='" + probeTemperatures + '\'' +
                ", terminalTimeStamp=" + terminalTimeStamp +
                ", errorData='" + errorData + '\'' +
                '}';
    }

    public int getBatteryConsistencyDifferenceAlarm() {
        return batteryConsistencyDifferenceAlarm;
    }

    public void setBatteryConsistencyDifferenceAlarm(int batteryConsistencyDifferenceAlarm) {
        this.batteryConsistencyDifferenceAlarm = batteryConsistencyDifferenceAlarm;
    }

    public int getSoc() {
        return soc;
    }

    public void setSoc(int soc) {
        this.soc = soc;
    }

    public int getSocJumpAlarm() {
        return socJumpAlarm;
    }

    public void setSocJumpAlarm(int socJumpAlarm) {
        this.socJumpAlarm = socJumpAlarm;
    }

    public int getSocLowAlarm() {
        return socLowAlarm;
    }

    public void setSocLowAlarm(int socLowAlarm) {
        this.socLowAlarm = socLowAlarm;
    }

    public String getTerminalTime() {
        return terminalTime;
    }

    public void setTerminalTime(String terminalTime) {
        this.terminalTime = terminalTime;
    }

    public int getSingleBatteryOverVoltageAlarm() {
        return singleBatteryOverVoltageAlarm;
    }

    public void setSingleBatteryOverVoltageAlarm(int singleBatteryOverVoltageAlarm) {
        this.singleBatteryOverVoltageAlarm = singleBatteryOverVoltageAlarm;
    }

    public int getVehicleStorageDeviceOvervoltageAlarm() {
        return vehicleStorageDeviceOvervoltageAlarm;
    }

    public void setVehicleStorageDeviceOvervoltageAlarm(int vehicleStorageDeviceOvervoltageAlarm) {
        this.vehicleStorageDeviceOvervoltageAlarm = vehicleStorageDeviceOvervoltageAlarm;
    }

    public int getBrakeSystemAlarm() {
        return brakeSystemAlarm;
    }

    public void setBrakeSystemAlarm(int brakeSystemAlarm) {
        this.brakeSystemAlarm = brakeSystemAlarm;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public int getChargeStatus() {
        return chargeStatus;
    }

    public void setChargeStatus(int chargeStatus) {
        this.chargeStatus = chargeStatus;
    }

    public int getDriveMotorTemperatureAlarm() {
        return driveMotorTemperatureAlarm;
    }

    public void setDriveMotorTemperatureAlarm(int driveMotorTemperatureAlarm) {
        this.driveMotorTemperatureAlarm = driveMotorTemperatureAlarm;
    }

    public int getDcdcStatusAlarm() {
        return dcdcStatusAlarm;
    }

    public void setDcdcStatusAlarm(int dcdcStatusAlarm) {
        this.dcdcStatusAlarm = dcdcStatusAlarm;
    }

    public String getGpsTime() {
        return gpsTime;
    }

    public void setGpsTime(String gpsTime) {
        this.gpsTime = gpsTime;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public int getDriveMotorFaultCount() {
        return driveMotorFaultCount;
    }

    public void setDriveMotorFaultCount(int driveMotorFaultCount) {
        this.driveMotorFaultCount = driveMotorFaultCount;
    }

    public Double getVehicleSpeed() {
        return vehicleSpeed;
    }

    public void setVehicleSpeed(Double vehicleSpeed) {
        this.vehicleSpeed = vehicleSpeed;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getChargeSystemVoltage() {
        return chargeSystemVoltage;
    }

    public void setChargeSystemVoltage(Double chargeSystemVoltage) {
        this.chargeSystemVoltage = chargeSystemVoltage;
    }

    public Double getChargeSystemCurrent() {
        return chargeSystemCurrent;
    }

    public void setChargeSystemCurrent(Double chargeSystemCurrent) {
        this.chargeSystemCurrent = chargeSystemCurrent;
    }

    public int getSingleBatteryUnderVoltageAlarm() {
        return singleBatteryUnderVoltageAlarm;
    }

    public void setSingleBatteryUnderVoltageAlarm(int singleBatteryUnderVoltageAlarm) {
        this.singleBatteryUnderVoltageAlarm = singleBatteryUnderVoltageAlarm;
    }

    public Double getTotalVoltage() {
        return totalVoltage;
    }

    public void setTotalVoltage(Double totalVoltage) {
        this.totalVoltage = totalVoltage;
    }

    public int getTemperatureDifferenceAlarm() {
        return temperatureDifferenceAlarm;
    }

    public void setTemperatureDifferenceAlarm(int temperatureDifferenceAlarm) {
        this.temperatureDifferenceAlarm = temperatureDifferenceAlarm;
    }

    public Double getMinVoltageBattery() {
        return minVoltageBattery;
    }

    public void setMinVoltageBattery(Double minVoltageBattery) {
        this.minVoltageBattery = minVoltageBattery;
    }

    public String getValidGps() {
        return validGps;
    }

    public void setValidGps(String validGps) {
        this.validGps = validGps;
    }

    public Double getTotalOdometer() {
        return totalOdometer;
    }

    public void setTotalOdometer(Double totalOdometer) {
        this.totalOdometer = totalOdometer;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public int getSocHighAlarm() {
        return socHighAlarm;
    }

    public void setSocHighAlarm(int socHighAlarm) {
        this.socHighAlarm = socHighAlarm;
    }

    public int getVehicleStorageDeviceUndervoltageAlarm() {
        return vehicleStorageDeviceUndervoltageAlarm;
    }

    public void setVehicleStorageDeviceUndervoltageAlarm(int vehicleStorageDeviceUndervoltageAlarm) {
        this.vehicleStorageDeviceUndervoltageAlarm = vehicleStorageDeviceUndervoltageAlarm;
    }

    public Double getTotalCurrent() {
        return totalCurrent;
    }

    public void setTotalCurrent(Double totalCurrent) {
        this.totalCurrent = totalCurrent;
    }

    public int getBatteryAlarm() {
        return batteryAlarm;
    }

    public void setBatteryAlarm(int batteryAlarm) {
        this.batteryAlarm = batteryAlarm;
    }

    public int getRechargeableStorageDeviceMismatchAlarm() {
        return rechargeableStorageDeviceMismatchAlarm;
    }

    public void setRechargeableStorageDeviceMismatchAlarm(int rechargeableStorageDeviceMismatchAlarm) {
        this.rechargeableStorageDeviceMismatchAlarm = rechargeableStorageDeviceMismatchAlarm;
    }

    public int getVehiclePureDeviceTypeOvercharge() {
        return vehiclePureDeviceTypeOvercharge;
    }

    public void setVehiclePureDeviceTypeOvercharge(int vehiclePureDeviceTypeOvercharge) {
        this.vehiclePureDeviceTypeOvercharge = vehiclePureDeviceTypeOvercharge;
    }

    public Double getMaxVoltageBattery() {
        return maxVoltageBattery;
    }

    public void setMaxVoltageBattery(Double maxVoltageBattery) {
        this.maxVoltageBattery = maxVoltageBattery;
    }

    public int getDcdcTemperatureAlarm() {
        return dcdcTemperatureAlarm;
    }

    public void setDcdcTemperatureAlarm(int dcdcTemperatureAlarm) {
        this.dcdcTemperatureAlarm = dcdcTemperatureAlarm;
    }

    public String getIsValidGps() {
        return isValidGps;
    }

    public void setIsValidGps(String isValidGps) {
        this.isValidGps = isValidGps;
    }

    public int getDriveMotorControllerTemperatureAlarm() {
        return driveMotorControllerTemperatureAlarm;
    }

    public void setDriveMotorControllerTemperatureAlarm(int driveMotorControllerTemperatureAlarm) {
        this.driveMotorControllerTemperatureAlarm = driveMotorControllerTemperatureAlarm;
    }

    public String getRepay() {
        return repay;
    }

    public void setRepay(String repay) {
        this.repay = repay;
    }

    public int getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(int carStatus) {
        this.carStatus = carStatus;
    }

    public String getIpuFaultCodes() {
        return IpuFaultCodes;
    }

    public void setIpuFaultCodes(String ipuFaultCodes) {
        IpuFaultCodes = ipuFaultCodes;
    }

    public int getEnergyRecoveryStatus() {
        return energyRecoveryStatus;
    }

    public void setEnergyRecoveryStatus(int energyRecoveryStatus) {
        this.energyRecoveryStatus = energyRecoveryStatus;
    }

    public int getFireStatus() {
        return fireStatus;
    }

    public void setFireStatus(int fireStatus) {
        this.fireStatus = fireStatus;
    }

    public int getCarMode() {
        return carMode;
    }

    public void setCarMode(int carMode) {
        this.carMode = carMode;
    }

    public int getHighVoltageInterlockStateAlarm() {
        return highVoltageInterlockStateAlarm;
    }

    public void setHighVoltageInterlockStateAlarm(int highVoltageInterlockStateAlarm) {
        this.highVoltageInterlockStateAlarm = highVoltageInterlockStateAlarm;
    }

    public int getInsulationAlarm() {
        return insulationAlarm;
    }

    public void setInsulationAlarm(int insulationAlarm) {
        this.insulationAlarm = insulationAlarm;
    }

    public int getMileageInformation() {
        return mileageInformation;
    }

    public void setMileageInformation(int mileageInformation) {
        this.mileageInformation = mileageInformation;
    }

    public Double getRemainPower() {
        return remainPower;
    }

    public void setRemainPower(Double remainPower) {
        this.remainPower = remainPower;
    }

    public String getEngineStatus() {
        return engineStatus;
    }

    public void setEngineStatus(String engineStatus) {
        this.engineStatus = engineStatus;
    }

    public String getAbsStatus() {
        return absStatus;
    }

    public void setAbsStatus(String absStatus) {
        this.absStatus = absStatus;
    }

    public String getVcuBrkErr() {
        return VcuBrkErr;
    }

    public void setVcuBrkErr(String vcuBrkErr) {
        VcuBrkErr = vcuBrkErr;
    }

    public String getESP_TCSFailStatus() {
        return ESP_TCSFailStatus;
    }

    public void setESP_TCSFailStatus(String ESP_TCSFailStatus) {
        this.ESP_TCSFailStatus = ESP_TCSFailStatus;
    }

    public String getEPS_EPSFailed() {
        return EPS_EPSFailed;
    }

    public void setEPS_EPSFailed(String EPS_EPSFailed) {
        this.EPS_EPSFailed = EPS_EPSFailed;
    }

    public String getBcuFaultCodes() {
        return BcuFaultCodes;
    }

    public void setBcuFaultCodes(String bcuFaultCodes) {
        BcuFaultCodes = bcuFaultCodes;
    }

    public String getVcuFaultCode() {
        return VcuFaultCode;
    }

    public void setVcuFaultCode(String vcuFaultCode) {
        VcuFaultCode = vcuFaultCode;
    }

    public String getObcFaultCode() {
        return ObcFaultCode;
    }

    public void setObcFaultCode(String obcFaultCode) {
        ObcFaultCode = obcFaultCode;
    }

    public Double getMaxTemperatureValue() {
        return maxTemperatureValue;
    }

    public void setMaxTemperatureValue(Double maxTemperatureValue) {
        this.maxTemperatureValue = maxTemperatureValue;
    }

    public Double getMinTemperatureValue() {
        return minTemperatureValue;
    }

    public void setMinTemperatureValue(Double minTemperatureValue) {
        this.minTemperatureValue = minTemperatureValue;
    }

    public String getDcdcFaultCode() {
        return DcdcFaultCode;
    }

    public void setDcdcFaultCode(String dcdcFaultCode) {
        DcdcFaultCode = dcdcFaultCode;
    }

    public String getBattPoleTOver() {
        return battPoleTOver;
    }

    public void setBattPoleTOver(String battPoleTOver) {
        this.battPoleTOver = battPoleTOver;
    }

    public int getAcSystemFailure() {
        return acSystemFailure;
    }

    public void setAcSystemFailure(int acSystemFailure) {
        this.acSystemFailure = acSystemFailure;
    }

    public int getAirBagWarningLamp() {
        return airBagWarningLamp;
    }

    public void setAirBagWarningLamp(int airBagWarningLamp) {
        this.airBagWarningLamp = airBagWarningLamp;
    }

    public int getBatteryChargeStatus() {
        return batteryChargeStatus;
    }

    public void setBatteryChargeStatus(int batteryChargeStatus) {
        this.batteryChargeStatus = batteryChargeStatus;
    }

    public int getChargingGunConnectionState() {
        return chargingGunConnectionState;
    }

    public void setChargingGunConnectionState(int chargingGunConnectionState) {
        this.chargingGunConnectionState = chargingGunConnectionState;
    }

    public String getBatteryVoltage() {
        return batteryVoltage;
    }

    public void setBatteryVoltage(String batteryVoltage) {
        this.batteryVoltage = batteryVoltage;
    }

    public String getProbeTemperatures() {
        return probeTemperatures;
    }

    public void setProbeTemperatures(String probeTemperatures) {
        this.probeTemperatures = probeTemperatures;
    }

    public Long getTerminalTimeStamp() {
        return terminalTimeStamp;
    }

    public void setTerminalTimeStamp(Long terminalTimeStamp) {
        this.terminalTimeStamp = terminalTimeStamp;
    }

    public String getErrorData() {
        return errorData;
    }

    public void setErrorData(String errorData) {
        this.errorData = errorData;
    }
}
