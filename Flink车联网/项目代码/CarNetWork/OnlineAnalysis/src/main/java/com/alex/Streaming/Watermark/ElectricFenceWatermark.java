package com.alex.Streaming.Watermark;

import com.alex.Streaming.Bean.ElectricFence.ElectricFenceModel;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;

import javax.annotation.Nullable;
import java.io.Serializable;

/**
 * @author Alex_Alex_liu
 * @create 2023-01-12 20:42
 * @Description 自定义水印 解决相邻两条数据乱序问题
 */
public class ElectricFenceWatermark implements AssignerWithPeriodicWatermarks<ElectricFenceModel>, Serializable {

    Long currentMaxTimestamp = 0L;

    @Nullable
    @Override
    public Watermark getCurrentWatermark() {
        return new Watermark(currentMaxTimestamp);
    }

    @Override
    public long extractTimestamp(ElectricFenceModel element, long previousElementTimestamp) {
        currentMaxTimestamp = Math.max(element.getTerminalTimestamp(),currentMaxTimestamp);

        return element.getTerminalTimestamp();
    }
}
