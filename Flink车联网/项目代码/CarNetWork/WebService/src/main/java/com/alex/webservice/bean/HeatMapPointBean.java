package com.alex.webservice.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: Alex_liu
 * @Date: 2022/9/16 1:46
 * @Description: 后台数据服务接口热力图对象
 */
@Getter
@Setter
public class HeatMapPointBean {
    // todo 经度
    private double longitude;
    // todo 纬度
    private double latitude;
    // todo 海拔
    private float elevation;
    // todo 城市
    private String city;
}
