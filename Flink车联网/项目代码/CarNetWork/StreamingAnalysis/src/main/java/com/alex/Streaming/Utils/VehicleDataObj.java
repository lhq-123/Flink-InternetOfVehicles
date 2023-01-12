package com.alex.Streaming.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Alex_liu
 * @Create 2022-11-19
 * @Description 实现所有上报数据的对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class VehicleDataObj {
    // 档位驱动力状态	0：无驱动力 1：有驱动力
    private int gearDriveForce = -999999;
    // 电池单体一致性差报警	0：正常 1：异常
    private int batteryConsistencyDifferenceAlarm = -999999;
    // SOC,单位：%
    private int soc = -999999;
    // SOC跳变报警	0：正常 1：异常
    private int socJumpAlarm = -999999;
    // 蠕行功能状态	启动/关闭
    private int caterpillaringFunction= -999999;
    // 有效卫星数
    private int satNum = -999999;
    // SOC低报警	0：正常 1：异常
    private int socLowAlarm = -999999;
    // 充电枪连接状态	0:解锁1:锁定2:失败
    private int chargingGunConnectionState = -999999;
    //最低温度子系统号	有效值范围：1~250，0xFE表示异常，0xFF表示无效
    private int minTemperatureSubSystemNum = -999999;
    // 充电电子锁状态	0：解锁 1：锁止
    private int chargedElectronicLockStatus = -999999;
    //最高电压电池单体代号	有效值范围：1~250，0xFE表示异常，1xFF表示无效
    private int maxVoltageBatteryNum = -999999;
    // 终端时间
    private String terminalTime = "";
    //单体电池过压报警	0：正常 1：异常
    private int singleBatteryOverVoltageAlarm = -999999;
    // 其他故障总数 N4	有效值范围：0~252，0xFE表示异常，0xFF表示无效
    private int otherFaultCount = -999999;
    // 车载储能装置过压报警	0：正常 1：异常
    private int vehicleStorageDeviceOvervoltageAlarm = -999999;
    // 制动系统报警	0：正常 1：异常
    private int brakeSystemAlarm = -999999;
    //服务器时间
    private String serverTime = "";
    //车辆唯一编号
    private String vin = "";
    //可充电储能装置故障总数 N1	有效值范围：0~252，0xFE表示异常，0xFF表示无效
    private int rechargeableStorageDevicesFaultCount = -999999;
    // 驱动电机温度报警	0：正常 1：异常
    private int driveMotorTemperatureAlarm = -999999;
    // 档位制动力状态	0：无制动力 1：有制动力
    private int gearBrakeForce = -999999;
    //DC-DC状态报警	0：正常 1：异常
    private int dcdcStatusAlarm = -999999;
    //位置纬度
    private Double lat = -999999D;
    //驱动电机故障代码列表	每4个字节代表一个故障码，由厂家进行自定义
    private String driveMotorFaultCodes = "";
    // 驱动电机控制器温度报警	0：正常 1：异常
    private int driveMotorFaultCount = -999999;
    // 终端类型
    private String deviceType = "";
    //车速
    private Double vehicleSpeed = -999999D;
    //位置经度
    private Double lng = -999999D;
    //充电时间延长原因
    private int chargingTimeExtensionReason= -999999;
    //可充电储能子系统电压信息列表
    private String nevChargeSystemVoltageDtoList = "";
    //nevChargeSystemVoltageDtoList里的信息 本帧电池起始序号
    private int currentBatteryStartNum = -999999;
    // 单体电池电压列表	Array格式
    private String batteryVoltage = "";
    //可充电储能装置电压	V
    private Double chargeSystemVoltage = -999999D;
    //本帧电池单体总数
    private int currentBatteryCount = -999999;
    //电池单体总数
    private int batteryCount = -999999;
    //电池子系统号
    private int childSystemNum = -999999;
    //可充电储能装置电流	A
    private Double chargeSystemCurrent = -999999D;

    //位置时间
    private String gpsTime = "";
    //发动机故障总数 N3	有效值范围：0~252，0xFE表示异常，0xFF表示无效
    private int engineFaultCount = -999999;
    //车辆ID（可忽略）
    private String carId = "";
    //当前电量	单位：%，如传输值为900 为90%
    private Double currentElectricity = -999999D;
    //单体电池欠压报警	0：正常 1：异常
    private int singleBatteryUnderVoltageAlarm = -999999;
    //最高电压电池子系统号	有效值范围：1~250，0xFE表示异常，0xFF表示无效
    private int maxVoltageBatterySubSystemNum = -999999;
    //最低温度探针序号	有效值范围：1~250，0xFE表示异常，0xFF表示无效
    private int minTemperatureProbe = -999999;
    //驱动电机数量
    private int driveMotorNum = -999999;
    //总电压	单位：V，实际取值0.1~100V
    private Double totalVoltage = -999999D;
    //温度差异报警	0：正常 1：异常
    private int temperatureDifferenceAlarm = -999999;
    //最高报警等级	"有效值：0~3。0：无故障，1：1级故障 2：2级故障，3：3级故障。0xFE表示异常，0xFF表示无效"
    private int maxAlarmLevel = -999999;
    //车辆状态（可忽略）
    private int status = 0;
    //挡位位置
    private int geerPosition = -999999;
    //平均能耗（历史累积平均能耗）	单位：kWh/百公里
    private Double averageEnergyConsumption = -999999D;
    //电池单体电压最低值	单位：V，实际值=传输值*0.001，即实际为0~15V。0xFF,0xFE表示异常，0xFF,0xFF表示无效
    private Double minVoltageBattery = -999999D;
    //挡位状态
    private int geerStatus = -999999;
    //驱动电机数据
    private String driveMotorData = "";
    //driveMotorData里的信息 电机控制器输入电压	V
    private Double controllerInputVoltage = -999999D;
    //电机控制器温度	℃
    private Double controllerTemperature = -999999D;
    //电机转速
    private Double revolutionSpeed = -999999D;
    //电机数量
    private int num = -999999;
    //电机控制器直流母线电流	A
    private Double controllerDcBusCurrent = -999999D;
    //电机温度	℃
    private Double temperature = -999999D;
    //电机扭矩	Nm
    private Double torque = -999999D;
    //电机状态
    private int state = -999999;
    //最低电压电池单体代号	有效值范围：1~250，0xFE表示异常，0xFF表示无效
    private int minVoltageBatteryNum = -999999;
    //GPS是否有效（可忽略）
    private String validGps = "";
    //发动机故障列表	每4个字节代表一个故障码，由厂家进行自定义
    private String engineFaultCodes = "";
    //最低温度值	单位：℃。实际值：-40~210。0xFE表示异常，1xFF表示无效
    private Double minTemperatureValue = -999999D;
    //"0x01: 停车充电 0x02: 行车充电 0x03: 未充电  0x04:充电完成 0xFE: 异常 0xFF:无效"
    private int chargeStatus = -999999;
    //行程开始时间
    private String ignitionTime = "";
    //累计里程	单位：km
    private Double totalOdometer = -999999D;
    //位置海拔
    private Double alti = -999999D;
    //车速（可忽略）	单位：km/h
    private Double speed = -999999D;
    //SOC过高报警	0：正常 1：异常
    private int socHighAlarm = -999999;
    //车载储能装置欠压报警	0：正常 1：异常
    private int vehicleStorageDeviceUndervoltageAlarm = -999999;
    //总电流	单位：A，实际值 = 传输值 * 0.1-1000，即实际取值为-1000~1000A,
    private Double totalCurrent = -999999D;
    //电池高温报警	0：正常 1：异常
    private int batteryAlarm = -999999;
    //可充电储能系统不匹配报警	0：正常 1：异常
    private int rechargeableStorageDeviceMismatchAlarm = -999999;
    //是否历史轨迹点（可忽略）
    private int isHistoryPoi = -999999;
    //车载储能装置类型过充	0：正常 1：异常
    private int vehiclePureDeviceTypeOvercharge = -999999;
    //电池单体电压最高值	单位：V，实际值=传输值*0.001，即实际为0~15V。0xFF,0xFE表示异常，0xFF,0xFF表示无效
    private Double maxVoltageBattery = -999999D;
    //DC-DC温度报警	0：正常 1：异常
    private int dcdcTemperatureAlarm = -999999;
    //同validGps（可忽略）
    private String isValidGps = "";
    //最后回传时间
    private String lastUpdatedTime = "";
    //驱动电机控制器温度报警	0：正常 1：异常
    private int driveMotorControllerTemperatureAlarm = -999999;
    //可充电储能子系统温度信息列表	列表中包含序号85-86字段内容
    private String nevChargeSystemTemperatureDtoList = "" ;
    //nevChargeSystemTemperatureDtoList里的信息 电池模块温度列表
    private String probeTemperatures = "";
    //温度探针个数
    private int chargeTemperatureProbeNum = -999999;
    //行程开始的累计里程	单位：km
    private Double igniteCumulativeMileage = -999999D;
    //DCDC状态	0x01 工作 0x02断开 0xFE: 异常 0xFF:无效
    private int dcStatus = -999999;
    //是否补发	TRUE:补发数据 ； FALSE:实时数据
    private String repay = "";
    //最高温度子系统号	有效值范围：1~250，0xFE表示异常，0xFF表示无效
    private int maxTemperatureSubSystemNum = -999999;
    //车辆状态	0x01: 车辆启动状态，0x02：熄火状态 0x03：其他状态，0xFE：异常，0xFF：无效
    private int carStatus = -999999;
    //最低电压电池子系统代号	有效值范围：1~250，0xFE表示异常，0xFF表示无效
    private int minVoltageBatterySubSystemNum = -999999;
    //方位角度值
    private Double heading = -999999D;
    //前电机故障代码 json数据中，ecuErrCodeDataList数组中ecuType=4
    private String IpuFaultCodes = "";
    //TUID
    private String tuid = "";
    //能量回收状态	高/低
    private int energyRecoveryStatus = -999999;
    //点火状态	0：未点火 ；2：已点火
    private int fireStatus = -999999;
    //可忽略
    private String targetType = "";
    //最高温度探针序号	有效值范围：1~250，0xFE表示异常，0xFF表示无效
    private int maxTemperatureProbe = -999999;
    //可充电储能装置故障代码列表	每4个字节代表一个故障码，由厂家进行自定义
    private String rechargeableStorageDevicesFaultCodes = "";
    //运行模式	0x01: 纯电 0x02 混动 0x03 燃油 0xFE: 异常 0xFF: 无效
    private int carMode = -999999;
    //高压互锁状态报警	0：正常 1：异常
    private int highVoltageInterlockStateAlarm = -999999;
    //绝缘报警	0：正常 1：异常
    private int insulationAlarm = -999999;
    //续航里程信息	单位：km
    private int mileageInformation = -999999;
    //最高温度值	单位：℃。实际值：-40~210。0xFE表示异常，1xFF表示无效
    private Double maxTemperatureValue = -999999D;
    //其他故障代码列表	每4个字节代表一个故障码，由厂家进行自定义
    private String otherFaultCodes = "";
    //当前电量	%
    private Double remainPower = -999999D;
    //绝缘电阻	kΩ
    private int insulateResistance = -999999;
    //电池低温加热器状态	"0：停止加热 1：正在加热"
    private int batteryLowTemperatureHeater = -999999;
    //百公里油耗
    private String fuelConsumption100km = "";
    //百毫秒油耗
    private String fuelConsumption = "";
    //发动机速度
    private String engineSpeed = "";
    //发动机状态	0：stop  1：crank   2：running
    private String engineStatus = "";
    //行李箱门状态	"0x0:Close 0x1:Open"
    private int trunk = -999999;
    //近光灯工作状态 "0x0:OFF 0x1:ON 0x2:Not used 0x3:Reserved"
    private int lowBeam = -999999;
    //锁电机过热保护 "0x0:OFF 0x1:ON"
    private String triggerLatchOverheatProtect = "";
    //右转向灯信号 "0x0:OFF 0x1:ON 0x2:Not used 0x3:Error"
    private int turnLndicatorRight = -999999;
    //远光灯工作状态	"0x0:OFF 0x1:ON 0x2:Not used 0x3:Reserved"
    private int highBeam = -999999;
    //左转向灯信号	"0x0:OFF 0x1:ON 0x2:Not used 0x3:Error"
    private int turnLndicatorLeft = -999999;
    //BCU软件版本
    private int bcuSwVers = -999999;
    //BCU硬件版本
    private int bcuHwVers = -999999;
    //"0x0:Initializing 0x1:Standby 0x2:PreCharge 0x3:HVActive 0x4:Powerdown 0x5:Failure 0x7:ReadytoSleep"
    private int bcuOperMod = -999999;
    /**
     * 0x1:拔枪退出充电;0x2:交流充电CP导致充电结束;0x3:交直流充电开始VCU充电允许信号致充电结束;0x4:充电机交流断电压小于90V导致充电结束;
     * 0x5:CC信号导致充电结束;0x6:OBC充电状态小于1导致充电结束;0x7:交直流充电过程中模式异常导致充电结束;0x8:充电过程中VCU充电允许信号导致充电结束;
     * 0x9:VCU要求高压下电导致充电结束;0xA:充电结束阶段OBC状态不为1导致充电结束;0xB:充电模式7导致充电结束;0xC:进行直流充电流程前报文超时导致充电结束;
     * 0xD:充电开启前VCU充电允许信号导致充电结束;0xE:加热故障导致充电结束;0xF:OBCCAN报文超时导致充电结束;0x10:BCU内部故障导致充电结束;
     * 0x11:CC信号1min内累积3次错误导致充电结束;0x12:直流报文超时导致充电结束;0x13:CC2信号导致充电结束;0x14:CCS充电暂停导致充电结束;
     * 0x15:温度不在范围导致充电结束;0x16:充电桩CST导致充电结束;0x17:充电桩参数不合适导致充电结束"
     */
    private int chrgEndReason = -999999;
    //单次能量回收能量存储值	Kwh
    private String BCURegenEngDisp = "";
    //单次能量回收容量存储值	Ah
    private int BCURegenCpDisp = -999999;
    /**
     * 电池充电模式 0x0:nochargemode 0x1:10AhomeACcharge 0x2:10Acablecontrolcharge 0x3:16AACchargestake/cablecontrolcharge
     * 0x4:32AACchargestake 0x5:63AACchargestake 0x6:DCcharge 0x7:AC/DCmodevalid 0x8:ACsemi-connection
     * 0x9:ACchargeunconventionality 0xA:DCchargeunconventionality
     */
    private int bcuChrgMod = -999999;
    //电池充电状态 "0x0:uncharged 0x1:charging 0x2:fullofcharge 0x3:chargeend"
    private int batteryChargeStatus = -999999;
    //电池故障代码
    private int bcuFltRnk = -999999;
    //电池极注高温报警 "0x0:Noerror 0x1:error"
    private String battPoleTOver = "";
    //电池健康状态 %
    private Double bcuSOH = -999999D;
    //电池内部加热激活 0x0:no active 0x1:heat active 0x2:cool active 0x3:加热中止
    private int battIntrHeatActive = -999999;
    //电池热管理模式请求 "0x0:not request 0x1: heat without HVactive 0x2: heat with HVactive 0x3:cool"
    private int battIntrHeatReq = -999999;
    //电池热管理目标温度 ℃
    private String BCUBattTarT = "";
    //电池外部加热请求"0x0:notrequest 0x1:request"
    private int battExtHeatReq = -999999;
    //电池最大充电功率(长时)  kW
    private String BCUMaxChrgPwrLongT = "";
    //电池最大放电功率(长时)  kW
    private String BCUMaxDchaPwrLongT = "";
    //累计能量回收能量存储值  Kwh
    private String BCUTotalRegenEngDisp = "";
    //累计能量回收容量存储值  Ah
    private String BCUTotalRegenCpDisp = "";
    //DCDC故障等级 "0x0:无故障 0x1:性能限制 0x2:暂时停止工作 0x3:立即停止工作"
    private int dcdcFltRnk = -999999;
    //DCDC故障码 json数据中，ecuErrCodeDataList数组中ecuType=3
    private String DcdcFaultCode = "";
    //DCDC输出电流 A
    private Double dcdcOutpCrrt = -999999D;
    //DCDC输出电压 V
    private Double dcdcOutpU = -999999D;
    //当前可输出的功率 W
    private int dcdcAvlOutpPwr = -999999;
    //ABS工作状态 "0x0:NotActive 0x1:Active"
    private String absActiveStatus = "";
    //ABS故障 "0x0:No error 0x1:Error"
    private String absStatus = "";
    //EPB故障状态 "0x0:no error 0x1:not defined 0x2:not defined 0x3:error"
    private String VcuBrkErr = "";
    //EPB夹紧力 kN
    private String EPB_AchievedClampForce = "";
    //EPB开关位置 "0x0:no request 0x1:Release request 0x2:Apply request 0x3:Error"
    private String epbSwitchPosition = "";
    //EPB状态 "0x0:both brakes released 0x1:both brakes applied 0x2:both brakes in operation 0x3:unknown"
    private String epbStatus = "";
    //ESP工作状态 "0x0:NotActive 0x1:Active"
    private String espActiveStatus = "";
    //ESP功能开启状态 "0x0:OFF 0x1:ON"
    private String espFunctionStatus = "";
    //ESP故障 "0x0:No error 0x1:Error"
    private String ESP_TCSFailStatus = "";
    //HHC功能激活 "0x0:function is not in active 0x1:function is in active"
    private String hhcActive = "";
    //TCS激活 "0x0:Not Active 0x1:Active"
    private String tcsActive = "";
    //制动主缸压力信号 Bar
    private String espMasterCylinderBrakePressure = "";
    //制动主缸压力信号有效 "0x0:Valid 0x1:Invalid"
    private String ESP_MasterCylinderBrakePressureValid = "";
    //力矩传感器状态 "0x0:Normal 0x1:Abnormal"
    private String espTorqSensorStatus = "";
    //助力转向故障 "0x0:No Failed 0x1:Failed"
    private String EPS_EPSFailed = "";
    //转角传感器失效 "0x0:Valid 0x1:Invalid"
    private String sasFailure = "";
    //转角速度 deg/s
    private String sasSteeringAngleSpeed = "";
    //转向角度 degree
    private String sasSteeringAngle = "";
    //转向角度失效信号 "0x0:Valid 0x1:Invalid"
    private String sasSteeringAngleValid = "";
    //转向力矩 Nm
    private String espSteeringTorque = "";
    //AC请求信号 "0x0:OFF 0x1:ON"
    private int acReq = -999999;
    //AC系统故障 "0x0:NotFailure 0x1:Failure"
    private int acSystemFailure = -999999;
    //PTC实际消耗功率 kW
    private Double ptcPwrAct = -999999D;
    //等离子发生器状态 "0x0:inactive 0x1:active"
    private int plasmaStatus = -999999;
    //电池包进水口温度 ℃
    private int battInTemperature = -999999;
    //电池加热回路状态 "0x0:off 0x1:Normal 0x2:warning 0x3:Failure"
    private String battWarmLoopSts = "";
    //电池冷却回路状态 "0x0:off 0x1:Normal 0x2:warning 0x3:Failure"
    private String battCoolngLoopSts = "";
    //电池冷却器工作标志位 "0x0:notactive 0x1:active"
    private String battCoolActv = "";
    //电机出水口温度 ℃
    private int motorOutTemperature = -999999;
    //电源状态反馈 "0x0:OFF 0x1:ACC 0x2:ON 0x3:Start"
    private String powerStatusFeedBack = "";
    //后除霜开关  "0x0:OFF 0x1:ON"
    private int AC_RearDefrosterSwitch = -999999;
    //后雾灯工作状态  "0x0:OFF 0x1:ON 0x2:Not used 0x3:Error"
    private int rearFoglamp = -999999;
    //驾驶侧门锁状态信号  "0x0:Lock 0x1:Unlock 0x2:Not used 0x3:Error"
    private int driverDoorLock = -999999;
    //驾驶员温度调节自动	℃
    private Double acDriverReqTemp = -999999D;
    //警戒状态信息  "0x0:Disarmed 0x1:Prearmed 0x2:Armed 0x3:Activated"
    private int keyAlarm = -999999;
    //空气净化状态提醒  "0x0:inactive 0x1:active"
    private int airCleanStsRemind = -999999;
    //内外循环模式  "0x0:recycle 0x1:fresh 0x2:auto recycle 0x3:error"
    private int recycleType = -999999;
    //启动控制信号  "0x0:NoRequest 0x1:StartupStart 0x2:StartupStop 0x3:Invalid"
    private String startControlsignal = "";
    //气囊系统报警灯状态  "0x0:Lamp off-no failure 0x1:Lamp on-no failure 0x2:Lamp flashing-no failure 0x3:Failure-failure present"
    private int airBagWarningLamp = -999999;
    //前除霜信号  "0x0:inactive 0x1:active"
    private int frontDefrosterSwitch = -999999;
    //前吹风模式  "0x0:blow face 0x1:blow face/blow feet 0x2:blow feet 0x3:blow fee/defroster 0x4:defroster 0x7:error"
    private String frontBlowType = "";
    //前排风量调节  "0x0:OFF 0x1:1档 0x2:2档 0x3:3档 0x4:4档 0x5:5档 0x6:6档 0x7:7档 0x8:8档"
    private int frontReqWindLevel = -999999;
    //前雨刮工作状态  "0x0:OFF 0x1:Low 0x2:High 0x3:Error"
    private String bcmFrontWiperStatus = "";
    //热管理系统实际消耗功率 kW
    private String tmsPwrAct = "";
    //未检测到钥匙报警信号 "0x0:nactive 0x1:ON"
    private int keyUndetectedAlarmSign = -999999;
    //位置灯工作状态 "0x0:OFF 0x1:ON 0x2:Not used 0x3:Reserved"
    private String positionLamp = "";
    /**
     * 温度调节电动 "0x0:Level 1 0x1:Level 2 0x2:Level 3 0x3:Level 4 0x4:Level 5 0x5:Level 6 0x6:Level 7 0x7:Level 8 0x8:Level 9
     * 0x9:Level 10 0xA:Level 11 0xB:Level 12 0xC:Level 13 0xD:Level 14 0xE:Level 15 0xF:Level 16"
     */
    private int driverReqTempModel = -999999;
    //转向灯开关状态信号 "0x0:Not Actived 0x1:Left Actived 0x2:Right Actived 0x3:Invalid"
    private int turnLightSwitchSts = -999999;
    //自动大灯状态 "0x0:Not Actived 0x1:Actived"
    private int autoHeadlightStatus = -999999;
    //左前门状态 "0x0:Close 0x1:Open"
    private int driverDoor = -999999;
    //前电机控制器故障等级 "0x0:Noerrors 0x1:reserved 0x2:alarmintosafemode 0x3:stop. 0x4:emergecestop."
    private int frntIpuFltRnk = -999999;
    //前电机控制器软件版本号
    private String frontIpuSwVers = "";
    //前电机控制器硬件版本号
    private int frontIpuHwVers = -999999;
    //前电机长时最大扭矩 Nm
    private int frntMotTqLongTermMax = -999999;
    //前电机长时最小扭矩 Nm
    private int frntMotTqLongTermMin = -999999;
    //CP信号占空比 %
    private int cpvValue = -999999;
    //充电机工作状态 "0x0:Init 0x1:Standby 0x2:working 0x3:reserved 0x4:Failure 0x5:reserved 0x6:Sleep"
    private int obcChrgSts = -999999;
    //充电机故障等级 "0x0:无故障 0x1:性能限制 0x2:暂时停止工作 0x3:立即停止工作"
    private String obcFltRnk = "";
    //电池故障代码 json数据中，ecuErrCodeDataList数组中ecuType=2
    private String BcuFaultCodes = "";
    //充电机交流端实时输入电流	A
    private Double obcChrgInpAcI = -999999D;
    //充电机交流端实时输入电压	V
    private int obcChrgInpAcU = -999999;
    //充电机实时充电电流	A
    private Double obcChrgDcI = -999999D;
    //充电机实时充电电压	V
    private Double obcChrgDcU = -999999D;
    //充电机温度	℃
    private int obcTemperature = -999999;
    //充电机最大允许输出功率	w
    private int obcMaxChrgOutpPwrAvl = -999999;
    //副驶安全带扣状态 "0x0:Buckled 0x1:Unbuckle 0x2:Not Used 0x3:Not Used"
    private int passengerBuckleSwitch = -999999;
    //碰撞输出信号 "0x0:No Crash 0x1:crash"
    private String crashlfo = "";
    //主驶安全带扣状态 "0x0:Buckled 0x1:Unbuckle 0x2:Not Used 0x3:Not Used"
    private int driverBuckleSwitch = -999999;
    //禁止发动机启动 "0x0:No Inhibit 0x1:Inhibit Engine Start 0x2:Reserved 0x3:Invalid"
    private String engineStartHibit = "";
    //远程解闭锁请求 "0x0 : No Request 0x1 : Remote Lock 0x2 : Remote Unlock 0x3 : Invalid."
    private String lockCommand = "";
    //远程寻车 "0x0 : No Request 0x1:Light 0x2 :Horn 0x3 :Light And Horn"
    private String searchCarReq = "";
    //主驾温度请求信号
    private String acTempValueReq = "";
    //VCU故障代码 json数据中，ecuErrCodeDataList数组中ecuType=1
    private String VcuFaultCode = "";
    //VCU故障数量
    private String vcuErrAmnt = "";
    //VCU软件版本号
    private int vcuSwVers = -999999;
    //VCU硬件版本号
    private int vcuHwVers = -999999;
    //低速报警状态 "0x0:opened 0x1:closed 0x2:Reserved"
    private String lowSpdWarnStatus = "";
    //低压电瓶充电请求 "0x0:无请求 0x1:低压电瓶充电"
    private int lowBattChrgRqe = -999999;
    //低压电瓶充电状态 "0x0:补电成功 0x1:补电失败"
    private String lowBattChrgSts = "";
    //低压电瓶电压 V
    private Double lowBattU = -999999D;
    //电子手刹状态 "0x0:放下 0x1:拉上"
    private int handlebrakeStatus = -999999;
    //换挡器位置有效 "0x0:Valid 0x1:Invalid"
    private String shiftPositionValid = "";
    //加速踏板位置有效 "0x0:valid 0x1:invalid"
    private String accPedalValid = "";
    //驾驶模式 "0x0:Reserved 0x1:Normal 0x2:Sport 0x3:ECO"
    private int driveMode = -999999;
    //驾驶模式按键状态 "0x0:未按下 0x1:按下"
    private int driveModeButtonStatus = -999999;
    //碰撞信号状态 "0x0:NoCrash 0x1:DeploymentCrash"
    private int VCUSRSCrashOutpSts = -999999;
    /**
     * 文字提醒 "0x0：初始值或无效值 0x1：无法启动，请拔充电枪 0x2：换挡时请按解锁按钮 0x3：P挡未锁止，请维修（预留）
     * 0x4：P挡未解锁，请维修（预留） 0x5：高压系统过温 0x6：电网波动，请更换充电地点 0x7：制动助力不足，请谨慎驾驶
     * 0x8：请在P档下进行充电（预留） 0x9：请刷卡或连接电源 0xA：请选择一种充电方式 0xB：无法充电，请拉起手刹
     * 0xC：动力系统故障，请立即停车 0xD：电网波动，充电时间延长 0xE：换挡器故障，请维修 0xF：请手动解锁充电电子锁
     * 0x10：电子锁锁止失败，充电时间延长 0x11：电池温度低 0x12：请踩刹车退出P档（预留） 0x13：电子锁锁止失败，充电停止
     * 0x14：12V蓄电池电压过低，请靠边停车 0x15：动力电池电量低，请及时充电 0x16：功率限制，请减速慢行
     * 0x17：换挡时请踩下制动踏板 0x18：请将钥匙拧到Start档后再换挡（低配仪表）/请踩制动踏板并按Start按钮后再换挡（高配仪表）"
     */
    private int textDispEna = -999999;
    //巡航控制状态 "0x0:Off 0x1:Active 0x2:Standby 0x3:Error"
    private int crsCtrlStatus = -999999;
    //巡航目标车速 Km/h
    private int crsTarSpd = -999999;
    /**
     * 巡航信息提示 "0x0:无提示 0x1:系统故障，巡航禁止 0x2:当前车速过低不满足巡航条件 0x3:当前车速过高不满足巡航条件
     * 0x4:非前进挡，巡航禁止 0x5:车身稳定系统激活，巡航禁止。 0x6:动力系统超速，巡航禁止。 0x7:制动踏板踩下，巡航禁止
     * 0x8:无有效目标车速，请按set键重新设定 0x9:请按启动键，进入可行驶模式 0xA:巡航已关闭 0xB:巡航已退出"
     */
    private int crsTextDisp = -999999;
    //钥匙ON档信号 "0x0:非ON 0x1:ON"
    private int keyOn = -999999;
    //整车功率限制 "0x0:NoError 0x1:Error"
    private int vehPwrlim = -999999;
    //整车配置信息 "0x0:Level1 0x1:Level2 0x2:Level3 0x3:Invalid"
    private String vehCfgInfo = "";
    //制动真空压力信号 kPa
    private int vacBrkPRmu = -999999;
    /**
     *充电机故障码  "0x0:当前无故障 0x1:12V电池电压过高 0x2:12V电池电压过低 0x3:CP内部6V电压异常 0x4:CP内部9V电压异常
     * 0x5:CP内部频率异常 0x6:CP内部占空比异常 0x7:CAN收发器异常 0x8:内部SCI通信失败 0x9:内部SCICRC错误 0xA:输出过压关机
     * 0xB:输出低压关机 0xC:交流输入低压关机 0xD:输入过压关机 0xE:环境温度过低关机 0xF:环境温度过高关机 0x10:充电机PFC电压欠压
     * 0x11:输入过载 0x12:输出过载 0x13:自检故障 0x14:外部CANbusoff 0x15:内部CANbusoff 0x16:外部CAN通信超时
     * 0x17:外部CAN使能超时 0x18:外部CAN通信错误 0x19:输出短路 0x1A:充电参数错误 0x1B:充电机PFC电压过压
     * 0x1C:内部SCI通信失败 0x1D:过功率 0x1E:PFC电感过温 0x1F:LLC变压器过温 0x20:M1功率板过温 0x21:PFC温度降额
     * 0x22:LLC温度降额 0x23:M1板温度降额 0x24:Air环境温度降额" json数据中，ecuErrCodeDataList数组中ecuType=5
     */
    private String ObcFaultCode = "";

    // 扩展字段 终端时间
    private Long terminalTimeStamp = -999999L;
    // 扩展字段，用于存储异常数据
    private String errorData = "";


    /**
     * @desc:转换成hive表的数据结果,数据字段之间以'\t'分割
     * @return string
     */
    public String toHiveString() {
        StringBuilder resultString = new StringBuilder();
        resultString.append(vin).append("\t");
        resultString.append(terminalTime).append("\t");
        // 先判断是否为空，不为空再进行拼接
        if (soc != -999999) resultString.append(soc).append("\t"); else resultString.append("NULL").append("\t");
        if (lat != -999999) resultString.append(lat).append("\t"); else resultString.append("NULL").append("\t");
        if (lng != -999999) resultString.append(lng).append("\t"); else resultString.append("NULL").append("\t");
        if (gearDriveForce != -999999) resultString.append(gearDriveForce).append("\t"); else resultString.append("NULL").append("\t");
        if (batteryConsistencyDifferenceAlarm != -999999) resultString.append(batteryConsistencyDifferenceAlarm).append("\t"); else resultString.append("NULL").append("\t");
        if (socJumpAlarm != -999999) resultString.append(socJumpAlarm).append("\t"); else resultString.append("NULL").append("\t");
        if (caterpillaringFunction != -999999) resultString.append(caterpillaringFunction).append("\t"); else resultString.append("NULL").append("\t");
        if (satNum != -999999) resultString.append(satNum).append("\t"); else resultString.append("NULL").append("\t");
        if (socLowAlarm != -999999) resultString.append(socLowAlarm).append("\t"); else resultString.append("NULL").append("\t");
        if (chargingGunConnectionState != -999999) resultString.append(chargingGunConnectionState).append("\t"); else resultString.append("NULL").append("\t");
        if (minTemperatureSubSystemNum != -999999) resultString.append(minTemperatureSubSystemNum).append("\t"); else resultString.append("NULL").append("\t");
        if (chargedElectronicLockStatus != -999999) resultString.append(chargedElectronicLockStatus).append("\t"); else resultString.append("NULL").append("\t");
        if (maxVoltageBatteryNum != -999999) resultString.append(maxVoltageBatteryNum).append("\t"); else resultString.append("NULL").append("\t");
        if (singleBatteryOverVoltageAlarm != -999999) resultString.append(singleBatteryOverVoltageAlarm).append("\t"); else resultString.append("NULL").append("\t");
        if (otherFaultCount != -999999) resultString.append(otherFaultCount).append("\t"); else resultString.append("NULL").append("\t");
        if (vehicleStorageDeviceOvervoltageAlarm != -999999) resultString.append(vehicleStorageDeviceOvervoltageAlarm).append("\t"); else resultString.append("NULL").append("\t");
        if (brakeSystemAlarm != -999999) resultString.append(brakeSystemAlarm).append("\t"); else resultString.append("NULL").append("\t");
        if (serverTime != "") resultString.append(serverTime).append("\t"); else resultString.append("NULL").append("\t");
        if (rechargeableStorageDevicesFaultCount != -999999) resultString.append(rechargeableStorageDevicesFaultCount).append("\t"); else resultString.append("NULL").append("\t");
        if (driveMotorTemperatureAlarm != -999999) resultString.append(driveMotorTemperatureAlarm).append("\t"); else resultString.append("NULL").append("\t");
        if (gearBrakeForce != -999999) resultString.append(gearBrakeForce).append("\t"); else resultString.append("NULL").append("\t");
        if (dcdcStatusAlarm != -999999) resultString.append(dcdcStatusAlarm).append("\t"); else resultString.append("NULL").append("\t");
        if (driveMotorFaultCodes != "") resultString.append(driveMotorFaultCodes).append("\t"); else resultString.append("NULL").append("\t");
        if (deviceType != "") resultString.append(deviceType).append("\t"); else resultString.append("NULL").append("\t");
        if (vehicleSpeed != -999999) resultString.append(vehicleSpeed).append("\t"); else resultString.append("NULL").append("\t");
        if (chargingTimeExtensionReason != -999999) resultString.append(chargingTimeExtensionReason).append("\t"); else resultString.append("NULL").append("\t");
        //         if (nevChargeSystemVoltageDtoList != -999999) resultString.append(nevChargeSystemVoltageDtoList).append("\t"); else resultString.append("NULL").append("\t");
        resultString.append("NULL").append("\t");
        if (currentBatteryStartNum != -999999) resultString.append(currentBatteryStartNum).append("\t"); else resultString.append("NULL").append("\t");
        if (batteryVoltage != "") resultString.append(batteryVoltage).append("\t"); else resultString.append("NULL").append("\t");
        if (chargeSystemVoltage != -999999) resultString.append(chargeSystemVoltage).append("\t"); else resultString.append("NULL").append("\t");
        if (currentBatteryCount != -999999) resultString.append(currentBatteryCount).append("\t"); else resultString.append("NULL").append("\t");
        if (batteryCount != -999999) resultString.append(batteryCount).append("\t"); else resultString.append("NULL").append("\t");
        if (childSystemNum != -999999) resultString.append(childSystemNum).append("\t"); else resultString.append("NULL").append("\t");
        if (chargeSystemCurrent != -999999) resultString.append(chargeSystemCurrent).append("\t"); else resultString.append("NULL").append("\t");
        if (gpsTime != "") resultString.append(gpsTime).append("\t"); else resultString.append("NULL").append("\t");
        if (engineFaultCount != -999999) resultString.append(engineFaultCount).append("\t"); else resultString.append("NULL").append("\t");
        if (carId != "") resultString.append(carId).append("\t"); else resultString.append("NULL").append("\t");
        if (currentElectricity != -999999) resultString.append(currentElectricity).append("\t"); else resultString.append("NULL").append("\t");
        if (singleBatteryUnderVoltageAlarm != -999999) resultString.append(singleBatteryUnderVoltageAlarm).append("\t"); else resultString.append("NULL").append("\t");
        if (maxVoltageBatterySubSystemNum != -999999) resultString.append(maxVoltageBatterySubSystemNum).append("\t"); else resultString.append("NULL").append("\t");
        if (minTemperatureProbe != -999999) resultString.append(minTemperatureProbe).append("\t"); else resultString.append("NULL").append("\t");
        if (driveMotorNum != -999999) resultString.append(driveMotorNum).append("\t"); else resultString.append("NULL").append("\t");
        if (totalVoltage != -999999) resultString.append(totalVoltage).append("\t"); else resultString.append("NULL").append("\t");
        if (temperatureDifferenceAlarm != -999999) resultString.append(temperatureDifferenceAlarm).append("\t"); else resultString.append("NULL").append("\t");
        if (maxAlarmLevel != -999999) resultString.append(maxAlarmLevel).append("\t"); else resultString.append("NULL").append("\t");
        if (status != -999999) resultString.append(status).append("\t"); else resultString.append("NULL").append("\t");
        if (geerPosition != -999999) resultString.append(geerPosition).append("\t"); else resultString.append("NULL").append("\t");
        if (averageEnergyConsumption != -999999) resultString.append(averageEnergyConsumption).append("\t"); else resultString.append("NULL").append("\t");
        if (minVoltageBattery != -999999) resultString.append(minVoltageBattery).append("\t"); else resultString.append("NULL").append("\t");
        if (geerStatus != -999999) resultString.append(geerStatus).append("\t"); else resultString.append("NULL").append("\t");
        //         if (driveMotorData != -999999) resultString.append(driveMotorData).append("\t"); else resultString.append("NULL").append("\t");
        resultString.append("NULL").append("\t");
        if (controllerInputVoltage != -999999) resultString.append(controllerInputVoltage).append("\t"); else resultString.append("NULL").append("\t");
        if (controllerTemperature != -999999) resultString.append(controllerTemperature).append("\t"); else resultString.append("NULL").append("\t");
        if (revolutionSpeed != -999999) resultString.append(revolutionSpeed).append("\t"); else resultString.append("NULL").append("\t");
        if (num != -999999) resultString.append(num).append("\t"); else resultString.append("NULL").append("\t");
        if (controllerDcBusCurrent != -999999) resultString.append(controllerDcBusCurrent).append("\t"); else resultString.append("NULL").append("\t");
        if (temperature != -999999) resultString.append(temperature).append("\t"); else resultString.append("NULL").append("\t");
        if (torque != -999999) resultString.append(torque).append("\t"); else resultString.append("NULL").append("\t");
        if (state != -999999) resultString.append(state).append("\t"); else resultString.append("NULL").append("\t");
        if (minVoltageBatteryNum != -999999) resultString.append(minVoltageBatteryNum).append("\t"); else resultString.append("NULL").append("\t");
        if (validGps != "") resultString.append(validGps).append("\t"); else resultString.append("NULL").append("\t");
        if (engineFaultCodes != "") resultString.append(engineFaultCodes).append("\t"); else resultString.append("NULL").append("\t");
        if (minTemperatureValue != -999999) resultString.append(minTemperatureValue).append("\t"); else resultString.append("NULL").append("\t");
        if (chargeStatus != -999999) resultString.append(chargeStatus).append("\t"); else resultString.append("NULL").append("\t");
        if (ignitionTime != "") resultString.append(ignitionTime).append("\t"); else resultString.append("NULL").append("\t");
        if (totalOdometer != -999999) resultString.append(totalOdometer).append("\t"); else resultString.append("NULL").append("\t");
        if (alti != -999999) resultString.append(alti).append("\t"); else resultString.append("NULL").append("\t");
        if (speed != -999999) resultString.append(speed).append("\t"); else resultString.append("NULL").append("\t");
        if (socHighAlarm != -999999) resultString.append(socHighAlarm).append("\t"); else resultString.append("NULL").append("\t");
        if (vehicleStorageDeviceUndervoltageAlarm != -999999) resultString.append(vehicleStorageDeviceUndervoltageAlarm).append("\t"); else resultString.append("NULL").append("\t");
        if (totalCurrent != -999999) resultString.append(totalCurrent).append("\t"); else resultString.append("NULL").append("\t");
        if (batteryAlarm != -999999) resultString.append(batteryAlarm).append("\t"); else resultString.append("NULL").append("\t");
        if (rechargeableStorageDeviceMismatchAlarm != -999999) resultString.append(rechargeableStorageDeviceMismatchAlarm).append("\t"); else resultString.append("NULL").append("\t");
        if (isHistoryPoi != -999999) resultString.append(isHistoryPoi).append("\t"); else resultString.append("NULL").append("\t");
        if (vehiclePureDeviceTypeOvercharge != -999999) resultString.append(vehiclePureDeviceTypeOvercharge).append("\t"); else resultString.append("NULL").append("\t");
        if (maxVoltageBattery != -999999) resultString.append(maxVoltageBattery).append("\t"); else resultString.append("NULL").append("\t");
        if (dcdcTemperatureAlarm != -999999) resultString.append(dcdcTemperatureAlarm).append("\t"); else resultString.append("NULL").append("\t");
        if (isValidGps != "") resultString.append(isValidGps).append("\t"); else resultString.append("NULL").append("\t");
        if (lastUpdatedTime != "") resultString.append(lastUpdatedTime).append("\t"); else resultString.append("NULL").append("\t");
        if (driveMotorControllerTemperatureAlarm != -999999) resultString.append(driveMotorControllerTemperatureAlarm).append("\t"); else resultString.append("NULL").append("\t");
        //         if (nevChargeSystemTemperatureDtoList != -999999) resultString.append(nevChargeSystemTemperatureDtoList).append("\t"); else resultString.append("NULL").append("\t");
        resultString.append("NULL").append("\t");
        if (probeTemperatures != "") resultString.append(probeTemperatures).append("\t"); else resultString.append("NULL").append("\t");
        if (chargeTemperatureProbeNum != -999999) resultString.append(chargeTemperatureProbeNum).append("\t"); else resultString.append("NULL").append("\t");
        if (igniteCumulativeMileage != -999999) resultString.append(igniteCumulativeMileage).append("\t"); else resultString.append("NULL").append("\t");
        if (dcStatus != -999999) resultString.append(dcStatus).append("\t"); else resultString.append("NULL").append("\t");
        if (repay != "") resultString.append(repay).append("\t"); else resultString.append("NULL").append("\t");
        if (maxTemperatureSubSystemNum != -999999) resultString.append(maxTemperatureSubSystemNum).append("\t"); else resultString.append("NULL").append("\t");
        if (carStatus != -999999) resultString.append(carStatus).append("\t"); else resultString.append("NULL").append("\t");
        if (minVoltageBatterySubSystemNum != -999999) resultString.append(minVoltageBatterySubSystemNum).append("\t"); else resultString.append("NULL").append("\t");
        if (heading != -999999) resultString.append(heading).append("\t"); else resultString.append("NULL").append("\t");
        if (driveMotorFaultCount != -999999) resultString.append(driveMotorFaultCount).append("\t"); else resultString.append("NULL").append("\t");
        if (tuid != "") resultString.append(tuid).append("\t"); else resultString.append("NULL").append("\t");
        if (energyRecoveryStatus != -999999) resultString.append(energyRecoveryStatus).append("\t"); else resultString.append("NULL").append("\t");
        if (fireStatus != -999999) resultString.append(fireStatus).append("\t"); else resultString.append("NULL").append("\t");
        if (targetType != "") resultString.append(targetType).append("\t"); else resultString.append("NULL").append("\t");
        if (maxTemperatureProbe != -999999) resultString.append(maxTemperatureProbe).append("\t"); else resultString.append("NULL").append("\t");
        if (rechargeableStorageDevicesFaultCodes != "") resultString.append(rechargeableStorageDevicesFaultCodes).append("\t"); else resultString.append("NULL").append("\t");
        if (carMode != -999999) resultString.append(carMode).append("\t"); else resultString.append("NULL").append("\t");
        if (highVoltageInterlockStateAlarm != -999999) resultString.append(highVoltageInterlockStateAlarm).append("\t"); else resultString.append("NULL").append("\t");
        if (insulationAlarm != -999999) resultString.append(insulationAlarm).append("\t"); else resultString.append("NULL").append("\t");
        if (mileageInformation != -999999) resultString.append(mileageInformation).append("\t"); else resultString.append("NULL").append("\t");
        if (maxTemperatureValue != -999999) resultString.append(maxTemperatureValue).append("\t"); else resultString.append("NULL").append("\t");
        if (otherFaultCodes != "") resultString.append(otherFaultCodes).append("\t"); else resultString.append("NULL").append("\t");
        if (remainPower != -999999) resultString.append(remainPower).append("\t"); else resultString.append("NULL").append("\t");
        if (insulateResistance != -999999) resultString.append(insulateResistance).append("\t"); else resultString.append("NULL").append("\t");
        if (batteryLowTemperatureHeater != -999999) resultString.append(batteryLowTemperatureHeater).append("\t"); else resultString.append("NULL").append("\t");
        if (fuelConsumption != "") resultString.append(fuelConsumption).append("\t"); else resultString.append("NULL").append("\t");
        if (fuelConsumption != "") resultString.append(fuelConsumption).append("\t"); else resultString.append("NULL").append("\t");
        if (engineSpeed != "") resultString.append(engineSpeed).append("\t"); else resultString.append("NULL").append("\t");
        if (engineStatus != "") resultString.append(engineStatus).append("\t"); else resultString.append("NULL").append("\t");
        if (trunk != -999999) resultString.append(trunk).append("\t"); else resultString.append("NULL").append("\t");
        if (lowBeam != -999999) resultString.append(lowBeam).append("\t"); else resultString.append("NULL").append("\t");
        if (triggerLatchOverheatProtect != "") resultString.append(triggerLatchOverheatProtect).append("\t"); else resultString.append("NULL").append("\t");
        if (turnLndicatorRight != -999999) resultString.append(turnLndicatorRight).append("\t"); else resultString.append("NULL").append("\t");
        if (highBeam != -999999) resultString.append(highBeam).append("\t"); else resultString.append("NULL").append("\t");
        if (turnLndicatorLeft != -999999) resultString.append(turnLndicatorLeft).append("\t"); else resultString.append("NULL").append("\t");
        if (bcuSwVers != -999999) resultString.append(bcuSwVers).append("\t"); else resultString.append("NULL").append("\t");
        if (bcuHwVers != -999999) resultString.append(bcuHwVers).append("\t"); else resultString.append("NULL").append("\t");
        if (bcuOperMod != -999999) resultString.append(bcuOperMod).append("\t"); else resultString.append("NULL").append("\t");
        if (chrgEndReason != -999999) resultString.append(chrgEndReason).append("\t"); else resultString.append("NULL").append("\t");
        if (BCURegenEngDisp != "") resultString.append(BCURegenEngDisp).append("\t"); else resultString.append("NULL").append("\t");
        if (BCURegenCpDisp != -999999) resultString.append(BCURegenCpDisp).append("\t"); else resultString.append("NULL").append("\t");
        if (bcuChrgMod != -999999) resultString.append(bcuChrgMod).append("\t"); else resultString.append("NULL").append("\t");
        if (batteryChargeStatus != -999999) resultString.append(batteryChargeStatus).append("\t"); else resultString.append("NULL").append("\t");
        if (BcuFaultCodes != "") resultString.append(BcuFaultCodes).append("\t"); else resultString.append("NULL").append("\t");
        if (bcuFltRnk != -999999) resultString.append(bcuFltRnk).append("\t"); else resultString.append("NULL").append("\t");
        if (battPoleTOver != "") resultString.append(battPoleTOver).append("\t"); else resultString.append("NULL").append("\t");
        if (bcuSOH != -999999) resultString.append(bcuSOH).append("\t"); else resultString.append("NULL").append("\t");
        if (battIntrHeatActive != -999999) resultString.append(battIntrHeatActive).append("\t"); else resultString.append("NULL").append("\t");
        if (battIntrHeatReq != -999999) resultString.append(battIntrHeatReq).append("\t"); else resultString.append("NULL").append("\t");
        if (BCUBattTarT != "") resultString.append(BCUBattTarT).append("\t"); else resultString.append("NULL").append("\t");
        if (battExtHeatReq != -999999) resultString.append(battExtHeatReq).append("\t"); else resultString.append("NULL").append("\t");
        if (BCUMaxChrgPwrLongT != "") resultString.append(BCUMaxChrgPwrLongT).append("\t"); else resultString.append("NULL").append("\t");
        if (BCUMaxDchaPwrLongT != "") resultString.append(BCUMaxDchaPwrLongT).append("\t"); else resultString.append("NULL").append("\t");
        if (BCUTotalRegenEngDisp != "") resultString.append(BCUTotalRegenEngDisp).append("\t"); else resultString.append("NULL").append("\t");
        if (BCUTotalRegenCpDisp != "") resultString.append(BCUTotalRegenCpDisp).append("\t"); else resultString.append("NULL").append("\t");
        if (dcdcFltRnk != -999999) resultString.append(dcdcFltRnk).append("\t"); else resultString.append("NULL").append("\t");
        if (DcdcFaultCode != "") resultString.append(DcdcFaultCode).append("\t"); else resultString.append("NULL").append("\t");
        if (dcdcOutpCrrt != -999999) resultString.append(dcdcOutpCrrt).append("\t"); else resultString.append("NULL").append("\t");
        if (dcdcOutpU != -999999) resultString.append(dcdcOutpU).append("\t"); else resultString.append("NULL").append("\t");
        if (dcdcAvlOutpPwr != -999999) resultString.append(dcdcAvlOutpPwr).append("\t"); else resultString.append("NULL").append("\t");
        if (absActiveStatus != "") resultString.append(absActiveStatus).append("\t"); else resultString.append("NULL").append("\t");
        if (absStatus != "") resultString.append(absStatus).append("\t"); else resultString.append("NULL").append("\t");
        if (VcuBrkErr != "") resultString.append(VcuBrkErr).append("\t"); else resultString.append("NULL").append("\t");
        if (EPB_AchievedClampForce != "") resultString.append(EPB_AchievedClampForce).append("\t"); else resultString.append("NULL").append("\t");
        if (epbSwitchPosition != "") resultString.append(epbSwitchPosition).append("\t"); else resultString.append("NULL").append("\t");
        if (epbStatus != "") resultString.append(epbStatus).append("\t"); else resultString.append("NULL").append("\t");
        if (espActiveStatus != "") resultString.append(espActiveStatus).append("\t"); else resultString.append("NULL").append("\t");
        if (espFunctionStatus != "") resultString.append(espFunctionStatus).append("\t"); else resultString.append("NULL").append("\t");
        if (ESP_TCSFailStatus != "") resultString.append(ESP_TCSFailStatus).append("\t"); else resultString.append("NULL").append("\t");
        if (hhcActive != "") resultString.append(hhcActive).append("\t"); else resultString.append("NULL").append("\t");
        if (tcsActive != "") resultString.append(tcsActive).append("\t"); else resultString.append("NULL").append("\t");
        if (espMasterCylinderBrakePressure != "") resultString.append(espMasterCylinderBrakePressure).append("\t"); else resultString.append("NULL").append("\t");
        if (ESP_MasterCylinderBrakePressureValid != "") resultString.append(ESP_MasterCylinderBrakePressureValid).append("\t"); else resultString.append("NULL").append("\t");
        if (espTorqSensorStatus != "") resultString.append(espTorqSensorStatus).append("\t"); else resultString.append("NULL").append("\t");
        if (EPS_EPSFailed != "") resultString.append(EPS_EPSFailed).append("\t"); else resultString.append("NULL").append("\t");
        if (sasFailure != "") resultString.append(sasFailure).append("\t"); else resultString.append("NULL").append("\t");
        if (sasSteeringAngleSpeed != "") resultString.append(sasSteeringAngleSpeed).append("\t"); else resultString.append("NULL").append("\t");
        if (sasSteeringAngle != "") resultString.append(sasSteeringAngle).append("\t"); else resultString.append("NULL").append("\t");
        if (sasSteeringAngleValid != "") resultString.append(sasSteeringAngleValid).append("\t"); else resultString.append("NULL").append("\t");
        if (espSteeringTorque != "") resultString.append(espSteeringTorque).append("\t"); else resultString.append("NULL").append("\t");
        if (acReq != -999999) resultString.append(acReq).append("\t"); else resultString.append("NULL").append("\t");
        if (acSystemFailure != -999999) resultString.append(acSystemFailure).append("\t"); else resultString.append("NULL").append("\t");
        if (ptcPwrAct != -999999) resultString.append(ptcPwrAct).append("\t"); else resultString.append("NULL").append("\t");
        if (plasmaStatus != -999999) resultString.append(plasmaStatus).append("\t"); else resultString.append("NULL").append("\t");
        if (battInTemperature != -999999) resultString.append(battInTemperature).append("\t"); else resultString.append("NULL").append("\t");
        if (battWarmLoopSts != "") resultString.append(battWarmLoopSts).append("\t"); else resultString.append("NULL").append("\t");
        if (battCoolngLoopSts != "") resultString.append(battCoolngLoopSts).append("\t"); else resultString.append("NULL").append("\t");
        if (battCoolActv != "") resultString.append(battCoolActv).append("\t"); else resultString.append("NULL").append("\t");
        if (motorOutTemperature != -999999) resultString.append(motorOutTemperature).append("\t"); else resultString.append("NULL").append("\t");
        if (powerStatusFeedBack != "") resultString.append(powerStatusFeedBack).append("\t"); else resultString.append("NULL").append("\t");
        if (AC_RearDefrosterSwitch != -999999) resultString.append(AC_RearDefrosterSwitch).append("\t"); else resultString.append("NULL").append("\t");
        if (rearFoglamp != -999999) resultString.append(rearFoglamp).append("\t"); else resultString.append("NULL").append("\t");
        if (driverDoorLock != -999999) resultString.append(driverDoorLock).append("\t"); else resultString.append("NULL").append("\t");
        if (acDriverReqTemp != -999999) resultString.append(acDriverReqTemp).append("\t"); else resultString.append("NULL").append("\t");
        if (keyAlarm != -999999) resultString.append(keyAlarm).append("\t"); else resultString.append("NULL").append("\t");
        if (airCleanStsRemind != -999999) resultString.append(airCleanStsRemind).append("\t"); else resultString.append("NULL").append("\t");
        if (recycleType != -999999) resultString.append(recycleType).append("\t"); else resultString.append("NULL").append("\t");
        if (startControlsignal != "") resultString.append(startControlsignal).append("\t"); else resultString.append("NULL").append("\t");
        if (airBagWarningLamp != -999999) resultString.append(airBagWarningLamp).append("\t"); else resultString.append("NULL").append("\t");
        if (frontDefrosterSwitch != -999999) resultString.append(frontDefrosterSwitch).append("\t"); else resultString.append("NULL").append("\t");
        if (frontBlowType != "") resultString.append(frontBlowType).append("\t"); else resultString.append("NULL").append("\t");
        if (frontReqWindLevel != -999999) resultString.append(frontReqWindLevel).append("\t"); else resultString.append("NULL").append("\t");
        if (bcmFrontWiperStatus != "") resultString.append(bcmFrontWiperStatus).append("\t"); else resultString.append("NULL").append("\t");
        if (tmsPwrAct != "") resultString.append(tmsPwrAct).append("\t"); else resultString.append("NULL").append("\t");
        if (keyUndetectedAlarmSign != -999999) resultString.append(keyUndetectedAlarmSign).append("\t"); else resultString.append("NULL").append("\t");
        if (positionLamp != "") resultString.append(positionLamp).append("\t"); else resultString.append("NULL").append("\t");
        if (driverReqTempModel != -999999) resultString.append(driverReqTempModel).append("\t"); else resultString.append("NULL").append("\t");
        if (turnLightSwitchSts != -999999) resultString.append(turnLightSwitchSts).append("\t"); else resultString.append("NULL").append("\t");
        if (autoHeadlightStatus != -999999) resultString.append(autoHeadlightStatus).append("\t"); else resultString.append("NULL").append("\t");
        if (driverDoor != -999999) resultString.append(driverDoor).append("\t"); else resultString.append("NULL").append("\t");
        if (IpuFaultCodes != "") resultString.append(IpuFaultCodes).append("\t"); else resultString.append("NULL").append("\t");
        if (frntIpuFltRnk != -999999) resultString.append(frntIpuFltRnk).append("\t"); else resultString.append("NULL").append("\t");
        if (frontIpuSwVers != "") resultString.append(frontIpuSwVers).append("\t"); else resultString.append("NULL").append("\t");
        if (frontIpuHwVers != -999999) resultString.append(frontIpuHwVers).append("\t"); else resultString.append("NULL").append("\t");
        if (frntMotTqLongTermMax != -999999) resultString.append(frntMotTqLongTermMax).append("\t"); else resultString.append("NULL").append("\t");
        if (frntMotTqLongTermMin != -999999) resultString.append(frntMotTqLongTermMin).append("\t"); else resultString.append("NULL").append("\t");
        if (cpvValue != -999999) resultString.append(cpvValue).append("\t"); else resultString.append("NULL").append("\t");
        if (obcChrgSts != -999999) resultString.append(obcChrgSts).append("\t"); else resultString.append("NULL").append("\t");
        if (obcFltRnk != "") resultString.append(obcFltRnk).append("\t"); else resultString.append("NULL").append("\t");
        if (ObcFaultCode != "") resultString.append(ObcFaultCode).append("\t"); else resultString.append("NULL").append("\t");
        if (obcChrgInpAcI != -999999) resultString.append(obcChrgInpAcI).append("\t"); else resultString.append("NULL").append("\t");
        if (obcChrgInpAcU != -999999) resultString.append(obcChrgInpAcU).append("\t"); else resultString.append("NULL").append("\t");
        if (obcChrgDcI != -999999) resultString.append(obcChrgDcI).append("\t"); else resultString.append("NULL").append("\t");
        if (obcChrgDcU != -999999) resultString.append(obcChrgDcU).append("\t"); else resultString.append("NULL").append("\t");
        if (obcTemperature != -999999) resultString.append(obcTemperature).append("\t"); else resultString.append("NULL").append("\t");
        if (obcMaxChrgOutpPwrAvl != -999999) resultString.append(obcMaxChrgOutpPwrAvl).append("\t"); else resultString.append("NULL").append("\t");
        if (passengerBuckleSwitch != -999999) resultString.append(passengerBuckleSwitch).append("\t"); else resultString.append("NULL").append("\t");
        if (crashlfo != "") resultString.append(crashlfo).append("\t"); else resultString.append("NULL").append("\t");
        if (driverBuckleSwitch != -999999) resultString.append(driverBuckleSwitch).append("\t"); else resultString.append("NULL").append("\t");
        if (engineStartHibit != "") resultString.append(engineStartHibit).append("\t"); else resultString.append("NULL").append("\t");
        if (lockCommand != "") resultString.append(lockCommand).append("\t"); else resultString.append("NULL").append("\t");
        if (searchCarReq != "") resultString.append(searchCarReq).append("\t"); else resultString.append("NULL").append("\t");
        if (acTempValueReq != "") resultString.append(acTempValueReq).append("\t"); else resultString.append("NULL").append("\t");
        if (VcuFaultCode != "") resultString.append(VcuFaultCode).append("\t"); else resultString.append("NULL").append("\t");
        if (vcuErrAmnt != "") resultString.append(vcuErrAmnt).append("\t"); else resultString.append("NULL").append("\t");
        if (vcuSwVers != -999999) resultString.append(vcuSwVers).append("\t"); else resultString.append("NULL").append("\t");
        if (vcuHwVers != -999999) resultString.append(vcuHwVers).append("\t"); else resultString.append("NULL").append("\t");
        if (lowSpdWarnStatus != "") resultString.append(lowSpdWarnStatus).append("\t"); else resultString.append("NULL").append("\t");
        if (lowBattChrgRqe != -999999) resultString.append(lowBattChrgRqe).append("\t"); else resultString.append("NULL").append("\t");
        if (lowBattChrgSts != "") resultString.append(lowBattChrgSts).append("\t"); else resultString.append("NULL").append("\t");
        if (lowBattU != -999999) resultString.append(lowBattU).append("\t"); else resultString.append("NULL").append("\t");
        if (handlebrakeStatus != -999999) resultString.append(handlebrakeStatus).append("\t"); else resultString.append("NULL").append("\t");
        if (shiftPositionValid != "") resultString.append(shiftPositionValid).append("\t"); else resultString.append("NULL").append("\t");
        if (accPedalValid != "") resultString.append(accPedalValid).append("\t"); else resultString.append("NULL").append("\t");
        if (driveMode != -999999) resultString.append(driveMode).append("\t"); else resultString.append("NULL").append("\t");
        if (driveModeButtonStatus != -999999) resultString.append(driveModeButtonStatus).append("\t"); else resultString.append("NULL").append("\t");
        if (VCUSRSCrashOutpSts != -999999) resultString.append(VCUSRSCrashOutpSts).append("\t"); else resultString.append("NULL").append("\t");
        if (textDispEna != -999999) resultString.append(textDispEna).append("\t"); else resultString.append("NULL").append("\t");
        if (crsCtrlStatus != -999999) resultString.append(crsCtrlStatus).append("\t"); else resultString.append("NULL").append("\t");
        if (crsTarSpd != -999999) resultString.append(crsTarSpd).append("\t"); else resultString.append("NULL").append("\t");
        if (crsTextDisp != -999999) resultString.append(crsTextDisp).append("\t"); else resultString.append("NULL").append("\t");
        if (keyOn != -999999) resultString.append(keyOn).append("\t"); else resultString.append("NULL").append("\t");
        if (vehPwrlim != -999999) resultString.append(vehPwrlim).append("\t"); else resultString.append("NULL").append("\t");
        if (vehCfgInfo != "") resultString.append(vehCfgInfo).append("\t"); else resultString.append("NULL").append("\t");
        if (vacBrkPRmu != -999999) resultString.append(vacBrkPRmu).append("\t"); else resultString.append("NULL").append("\t");
        resultString.append(DateUtil.getCurrentDateTime());
        return resultString.toString();
    }
}


