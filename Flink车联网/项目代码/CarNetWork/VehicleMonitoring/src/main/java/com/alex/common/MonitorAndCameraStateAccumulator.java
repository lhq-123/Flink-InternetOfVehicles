package com.alex.common;
import com.alex.constants.Constants;
import com.alex.utils.StringUtil;
import org.apache.spark.metrics.source.AccumulatorSource;
import org.apache.spark.util.AccumulatorMetadata;
import org.apache.spark.util.AccumulatorV2;

/**
 * @author Alex_liu
 * @create 2023-01-29 16:56
 * @Description 自定义累加器要实现AccumulatorParam接口
 */
public class MonitorAndCameraStateAccumulator extends AccumulatorV2 {
    private static final long serialVersionUID = 1L;

    @Override
    public boolean isZero() {
        return false;
    }

    @Override
    public AccumulatorV2 copy() {
        return null;
    }

    @Override
    public void reset() {

    }

    @Override
    public void add(Object v) {

    }

    @Override
    public void merge(AccumulatorV2 other) {

    }

    @Override
    public Object value() {
        return null;
    }
}

