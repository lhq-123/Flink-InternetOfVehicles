package com.alex.function;

import com.alex.utils.StringUtil;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.expressions.MutableAggregationBuffer;
import org.apache.spark.sql.expressions.UserDefinedAggregateFunction;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Alex_liu
 * @create 2023-01-29 15:58
 * @Description 自定义UDAF聚合函数
 *              组内拼接去重函数（group_concat_distinct()）
 */
public class GroupConcatDistinctUDAF extends UserDefinedAggregateFunction {
    private static final long serialVersionUID = -2510776241322950505L;
    // 指定输入数据的字段与类型
    private StructType inputSchema = DataTypes.createStructType(Arrays.asList(
            DataTypes.createStructField("carInfo", DataTypes.StringType, true)));
    // 指定缓冲数据的字段与类型
    private StructType bufferSchema = DataTypes.createStructType(Arrays.asList(
            DataTypes.createStructField("bufferInfo", DataTypes.StringType, true)));
    // 指定返回类型
    private DataType dataType = DataTypes.StringType;
    // 指定是否是确定性的
    private boolean deterministic = true;

    /**
     * 输入数据的类型
     * @return
     */
    @Override
    public StructType inputSchema() {
        return inputSchema;
    }

    /**
     * 聚合操作的数据类型
     */
    @Override
    public StructType bufferSchema() {
        return bufferSchema;
    }

    @Override
    public DataType dataType() {
        return dataType;
    }

    @Override
    public boolean deterministic() {
        return deterministic;
    }

    /**
     * 初始化 ，在内部指定一个初始的值
     * @param buffer
     */
    @Override
    public void initialize(MutableAggregationBuffer buffer) {
        buffer.update(0, "");
    }

    /**
     * 更新，一个一个地将组内的字段值传递进来，实现拼接的逻辑
     * @param buffer
     * @param input
     */
    @Override
    public void update(MutableAggregationBuffer buffer, Row input) {
        //缓冲中的已经拼接过的monitoring信息小字符串
        String bufferMonitoringInfo = buffer.getString(0);
        //刚刚传递进来的某个monitoring信息
        String inputMonitoringInfo = input.getString(0);
        String[] split = inputMonitoringInfo.split("\\|");
        String monitorId = "";
        int addNum = 1;
        for (String currMonitorId: split) {
            if (currMonitorId.indexOf("=") != -1){
                monitorId = currMonitorId.split("=")[0];
                addNum = Integer.parseInt(currMonitorId.split("=")[1]);
            }else {
                monitorId = currMonitorId;
            }
            String oldVS = StringUtil.getFieldFromConcatString(bufferMonitoringInfo,"\\|",monitorId);
            if (oldVS == null){
                bufferMonitoringInfo += "|" + monitorId + "=" + addNum;
            }else {
                bufferMonitoringInfo = StringUtil.setFieldInConcatString(bufferMonitoringInfo,"\\|",monitorId, Integer.parseInt(oldVS)+addNum+"");
            }
            buffer.update(0, bufferMonitoringInfo);
        }
    }

    /**
     * 合并
     * update操作，可能是针对一个分组内的部分数据，在某个节点上发生的
     * 但是可能一个分组内的数据，会分布在多个节点上处理
     * 此时就要用merge操作，将各个节点上分布式拼接好的串，合并起来
     *
     * 海淀区 建材城西路
     * merge1:|0001=100|0002=20|0003=4
     * merge2:|0001=200|0002=30|0003=3|0004=100
     *
     * bufferMonitorInfo1 = 0001=300|0002=50|0003=7|0004=100
     * @param buffer1
     * @param buffer2
     */
    @Override
    public void merge(MutableAggregationBuffer buffer1, Row buffer2) {
        //缓存中的monitor信息这个大字符串
        String bufferMonitorInfo1 = buffer1.getString(0);
        //传进来
        String bufferMonitorInfo2 = buffer2.getString(0);

        // 等于是把buffer2里面的数据都拆开来更新
        for(String monitorInfo : bufferMonitorInfo2.split("\\|")) {
            Map<String, String> map = StringUtil.getKeyValuesFromConcatString(monitorInfo, "\\|");
            /**
             * bufferMonitorInfo1 = 0001=300|0002=50|0003=7|0009=1000
             */
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String monitor = entry.getKey();
                int carCount = Integer.parseInt(entry.getValue());
                String oldVS = StringUtil.getFieldFromConcatString(bufferMonitorInfo1, "\\|", monitor);
                //当没有获取到本次monitor对应的值时
                if(oldVS == null) {
                    if("".equals(bufferMonitorInfo1)) {
                        //当第一次聚合的时候，没有初始的传进来的bufferMonitorInfo1，默认为""
                        bufferMonitorInfo1 += monitor + "=" + carCount;
                    } else {
                        //当上一次传进来的字符串不包含本次的monitor时，就拼上
                        bufferMonitorInfo1 += "|" + monitor + "=" + carCount;
                    }
                }else{
                    int oldVal = Integer.valueOf(oldVS);
                    oldVal += carCount;
                    bufferMonitorInfo1 = StringUtil.setFieldInConcatString(bufferMonitorInfo1, "\\|", monitor, oldVal+"");
                }
                buffer1.update(0, bufferMonitorInfo1);
            }
        }
    }

    /**
     * evaluate方法返回数据的类型要和dateType的类型一致，不一致就会报错
     * @param row
     * @return
     */
    @Override
    public Object evaluate(Row row) {
        return row.getString(0);
    }
}
