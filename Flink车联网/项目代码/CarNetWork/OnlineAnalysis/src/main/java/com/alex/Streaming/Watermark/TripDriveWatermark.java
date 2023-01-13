package com.alex.Streaming.Watermark;

import com.alex.Streaming.Bean.VehicleDataObj;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;

import javax.annotation.Nullable;
import java.io.Serializable;

/**
 * @author Alex_liu
 * @Create 2022-11-22
 * @Description  驾驶行程自定义水位线对象：解决数据迟到5秒的问题
 *
 * 自定义水位线，通过时间戳定期分配时间戳并生成水位线（AssignerWithPeriodicWatermarks）
 */
public class TripDriveWatermark implements AssignerWithPeriodicWatermarks<VehicleDataObj>, Serializable {
    //TODO 1）允许最大乱序时间为：5秒
    long maxOutOfOrderTime= 1000*5;
    //TODO 2）初始化当前水位线时间戳
    Long currentMaxTimestamp= 0L;

    @Nullable
    @Override
    public Watermark getCurrentWatermark() {
        return new Watermark(currentMaxTimestamp-maxOutOfOrderTime);
    }

    @Override
    public long extractTimestamp(VehicleDataObj element, long previousElementTimestamp) {
        currentMaxTimestamp = Math.max(element.getTerminalTimeStamp(),currentMaxTimestamp);
        return element.getTerminalTimeStamp();

    }
}
