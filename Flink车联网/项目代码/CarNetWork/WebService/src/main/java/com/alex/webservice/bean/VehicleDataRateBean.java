package com.alex.webservice.bean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.Date;

/**
 * @Auther: Alex_liu
 * @Date: 2023-01-14 1:04
 * @Description: 数据准确率和错误率计算后台数据服务接口对象
 */
@Getter
@Setter
public class VehicleDataRateBean {
    /** todo 记录序列号 */
    private String seriesNo;
    /** todo 原数据正确数据总数 */
    private BigInteger srcTotalNum;
    /** todo 原数据错误数据总数 */
    private BigInteger errorSrcTotalNum;
    /** todo 原始数据正确率 */
    private Float dataAccuracy;
    /** todo 原始数据错误率 */
    private Float dataErrorRate;
    /** todo 记录计算时间 */
    private String processDate;
}