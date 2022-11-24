package com.lhq.Streaming.Bean;

import com.lhq.Streaming.Utils.DateUtil;
import lombok.Data;

/**
 * @author liu
 * @Create 2022-11-22
 * @Description   定义驾驶行程计算结果对应的JavaBean对象
 */

@Data
public class TripModel {
    //车架号
    private String vin = "";
    //上次报文soc
    private Double lastSoc =  -999999D;
    //上次报文里程数
    private Double lastMileage = -999999D;
    //行程开始时间
    private String tripStartTime = "";
    //行程开始soc
    private int start_BMS_SOC =  -999999;
    //行程开始经度
    private Double start_longitude = -999999D;
    //行程开始纬度
    private Double start_latitude =  -999999D;
    //行程开始里程
    private Double start_mileage = -999999D;
    //结束soc
    private int end_BMS_SOC =  -999999;
    //结束经度
    private Double end_longitude =  -999999D;
    //结束纬度
    private Double end_latitude =  -999999D;
    //结束里程
    private Double end_mileage =  -999999D;
    //行程结束时间
    private String tripEndTime = "" ;
    //行程里程消耗
    private Double mileage =  -999999D;
    //最高行驶车速
    private Double max_speed =  0D;
    //soc消耗
    private Double soc_comsuption =  0D;
    //行程消耗时间(分钟)
    private Double time_comsuption =  -999999D;
    //总低速的个数
    private Long total_low_speed_nums = 0L;
    //总中速的个数
    private Long total_medium_speed_nums =  0L;
    //总高速个数
    private Long total_high_speed_nums =  0L;
    //低速soc消耗
    private Double Low_BMS_SOC =  0D;
    //中速soc消耗
    private Double Medium_BMS_SOC =  0D;
    //高速soc消耗
    private Double High_BMS_SOC =  0D;
    //低速里程
    private Double Low_BMS_Mileage =  0D;
    //中速里程
    private Double Medium_BMS_Mileage =  0D;
    //高速里程
    private Double High_BMS_Mileage =  0D;
    //是否为异常行程 0：正常行程 1：异常行程（只有一个采样点）
    private int tripStatus = -999999;

    /**
     * 将驾驶行程计算结果数据保存到hdfs时候需要转换成可以被hive所识别的字符串格式
     * @return
     */
    public String toHiveString() {
        StringBuilder resultString = new StringBuilder();
        if (this.vin != "") resultString.append(this.vin).append("\t"); else resultString.append("NULL").append("\t");
        if (this.tripStartTime != "") resultString.append(this.tripStartTime).append("\t"); else resultString.append("NULL").append("\t");
        if (this.tripEndTime != "") resultString.append(this.tripEndTime).append("\t"); else resultString.append("NULL").append("\t");
        if (this.lastSoc !=  -999999 ) resultString.append(this.lastSoc).append("\t"); else resultString.append("NULL").append("\t");
        if (this.lastMileage !=  -999999 ) resultString.append(this.lastMileage).append("\t"); else resultString.append("NULL").append("\t");
        if (this.start_BMS_SOC !=  -999999 ) resultString.append(this.start_BMS_SOC).append("\t"); else resultString.append("NULL").append("\t");
        if (this.start_longitude !=  -999999 ) resultString.append(this.start_longitude).append("\t"); else resultString.append("NULL").append("\t");
        if (this.start_latitude !=  -999999 ) resultString.append(this.start_latitude).append("\t"); else resultString.append("NULL").append("\t");
        if (this.start_mileage !=  -999999 ) resultString.append(this.start_mileage).append("\t"); else resultString.append("NULL").append("\t");
        if (this.end_BMS_SOC !=  -999999 ) resultString.append(this.end_BMS_SOC).append("\t"); else resultString.append("NULL").append("\t");
        if (this.end_longitude !=  -999999 ) resultString.append(this.end_longitude).append("\t"); else resultString.append("NULL").append("\t");
        if (this.end_latitude !=  -999999 ) resultString.append(this.end_latitude).append("\t"); else resultString.append("NULL").append("\t");
        if (this.end_mileage !=  -999999 ) resultString.append(this.end_mileage).append("\t"); else resultString.append("NULL").append("\t");
        if (this.mileage !=  -999999 ) resultString.append(this.mileage).append("\t"); else resultString.append("NULL").append("\t");
        if (this.max_speed !=  -999999 ) resultString.append(this.max_speed).append("\t"); else resultString.append("NULL").append("\t");
        if (this.soc_comsuption !=  -999999 ) resultString.append(this.soc_comsuption).append("\t"); else resultString.append("NULL").append("\t");
        if (this.time_comsuption !=  -999999 ) resultString.append(this.time_comsuption).append("\t"); else resultString.append("NULL").append("\t");
        if (this.total_low_speed_nums !=  -999999 ) resultString.append(this.total_low_speed_nums).append("\t"); else resultString.append("NULL").append("\t");
        if (this.total_medium_speed_nums !=  -999999 ) resultString.append(this.total_medium_speed_nums).append("\t"); else resultString.append("NULL").append("\t");
        if (this.total_high_speed_nums !=  -999999 ) resultString.append(this.total_high_speed_nums).append("\t"); else resultString.append("NULL").append("\t");
        if (this.Low_BMS_SOC !=  -999999 ) resultString.append(this.Low_BMS_SOC).append("\t"); else resultString.append("NULL").append("\t");
        if (this.Medium_BMS_SOC !=  -999999 ) resultString.append(this.Medium_BMS_SOC).append("\t"); else resultString.append("NULL").append("\t");
        if (this.High_BMS_SOC !=  -999999 ) resultString.append(this.High_BMS_SOC).append("\t"); else resultString.append("NULL").append("\t");
        if (this.Low_BMS_Mileage !=  -999999 ) resultString.append(this.Low_BMS_Mileage).append("\t"); else resultString.append("NULL").append("\t");
        if (this.Medium_BMS_Mileage !=  -999999 ) resultString.append(this.Medium_BMS_Mileage).append("\t"); else resultString.append("NULL").append("\t");
        if (this.High_BMS_Mileage !=  -999999 ) resultString.append(this.High_BMS_Mileage).append("\t"); else resultString.append("NULL").append("\t");
        if (this.tripStatus !=  -999999 ) resultString.append(this.tripStatus).append("\t"); else resultString.append("NULL").append("\t");
        resultString.append(DateUtil.getCurrentDateTime());
        return resultString.toString();
    }
}
