package com.alex.Streaming.Sink.SourceDataSink;

import com.alex.Streaming.Utils.ConfigLoader;
import com.alex.Streaming.Utils.DateUtil;
import com.alex.Streaming.Utils.StringUtil;
import com.alex.Streaming.Bean.VehicleDataObj;
import org.apache.commons.lang.StringUtils;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Alex_liu
 * @Create 2022-11-21
 * @Description   将车辆常用的明细数据保存到HBase
 */
public class ToHBaseDetailSinkOptimize extends RichSinkFunction<VehicleDataObj> {
    private Logger logger = LoggerFactory.getLogger("ToHBaseDetailSinkOptimize");
    //要写入数据的表名
    private String tableName;
    private Connection conn = null;
    // hbase客户端中的数据写缓存对象
    private BufferedMutator mutator = null;
    //定义列簇的名称
    private String cf = "cf";

    public ToHBaseDetailSinkOptimize(String tableName) {
        this.tableName = tableName;
    }

    /**
     * 重写方法，实现数据写入hbase逻辑
     *   重写open方法：加载资源配置
     *      创建hbase配置对象，设置hbase配置信息，添加客户端相关配置
     *      跟据hbase的连接地址，获得hbase连接
     *      根据hbase连接和表名，获得hbase客户端Table对象
     * @param parameters
     * @throws Exception
     */
    @Override
    public void open(Configuration parameters) throws Exception {
        org.apache.hadoop.conf.Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.property.clientPort", ConfigLoader.get("zookeeper.clientPort"));
        config.set("hbase.zookeeper.quorum", ConfigLoader.get("zookeeper.quorum"));
        TableName tname = TableName.valueOf(tableName);
        config.set(TableInputFormat.INPUT_TABLE, tname.getNameAsString());
        conn = ConnectionFactory.createConnection(config);
        BufferedMutatorParams params = new BufferedMutatorParams(tname);
        //设置缓存10MB，当达到10MB时数据会自动刷到HBase
        params.writeBufferSize(1024 * 1024 * 10);
        // 强制缓冲区提交数据
        mutator = conn.getBufferedMutator(params);
    }

    /**
     * 重写invoke方法：数据写入hbase逻辑
     *    设计hbase的rowkey、根据列族封装Put对象
     *    把封装好的Put对象，写入HBase表中
     * @param vehicleDataObj
     * @param context
     * @throws Exception
     */
    @Override
    public void invoke(VehicleDataObj vehicleDataObj, Context context) throws Exception {
        try {
            Put put = setDataSourcePut(vehicleDataObj);
            mutator.mutate(put);
            //指定时间内的数据强制刷写到hbase
            mutator.flush();
        } catch (IOException ex) {
            logger.error("写入到hbase失败："+ex.getMessage());
        }
    }

    /**
     *  生成put对象
     * @param vehicleDataObj
     * @return
     */
    private Put setDataSourcePut(VehicleDataObj vehicleDataObj){
        //确定rowkey
        String rowKey = vehicleDataObj.getVin() + StringUtil.reverse(vehicleDataObj.getTerminalTimeStamp().toString());
        logger.info("当前的rowkey:" + rowKey);
        //通过 rowkey实例化put
        Put put = new Put(Bytes.toBytes(rowKey));
        //设置需要写入的列有那些
        //这两个列一定不为空，如果为空就不是正常数据了
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("vin"), Bytes.toBytes(vehicleDataObj.getVin()));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("terminalTime"), Bytes.toBytes(vehicleDataObj.getTerminalTime()));

        //电量百分比(currentElectricity)、当前电量(remainPower)、百公里油耗(fuelConsumption100km)、
        // 发动机速度(engineSpeed)、车辆速度(vehicleSpeed)
        if(vehicleDataObj.getCurrentElectricity() != -999999D){
            put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("currentElectricity"), Bytes.toBytes(vehicleDataObj.getCurrentElectricity()+""));
        }
        if(vehicleDataObj.getRemainPower() != -999999D){
            put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("remainPower"), Bytes.toBytes(vehicleDataObj.getRemainPower()+""));
        }
        if(StringUtils.isNotEmpty(vehicleDataObj.getFuelConsumption100km()) ){
            put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("fuelConsumption100km"), Bytes.toBytes(vehicleDataObj.getFuelConsumption100km()));
        }
        if(StringUtils.isNotEmpty(vehicleDataObj.getEngineSpeed()) ){
            put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("engineSpeed"), Bytes.toBytes(vehicleDataObj.getEngineSpeed()));
        }
        if(vehicleDataObj.getVehicleSpeed() != -999999D){
            put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("vehicleSpeed"), Bytes.toBytes(vehicleDataObj.getVehicleSpeed()+""));
        }
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("processTime"), Bytes.toBytes(DateUtil.getCurrentDateTime()));

        //返回put对象
        return  put;
    }

    /**
     *  重写close方法：销毁对象，释放资源
     *  遵循“先创建后关闭“原则
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        if (mutator != null) {
            // 也会强制缓冲区提交数据
            mutator.close();
        }
        if (conn != null || !conn.isClosed()) {
            conn.close();
        }
    }
}
