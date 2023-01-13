package com.alex.Streaming.Bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Alex_liu
 * @create 2023-01-13 14:08
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FailureAnalysisLocationInfo implements Serializable {
    //省份
    private String province;
    //城市
    private String city;
    //国家
    private String country;
    //区县
    private String district;
    //详细地址
    private String address;
    //纬度
    private Double latitude = -999999D;
    //经度
    private Double longitude = -999999D;
}
