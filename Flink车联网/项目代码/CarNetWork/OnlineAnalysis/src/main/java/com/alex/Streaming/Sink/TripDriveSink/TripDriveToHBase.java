package com.alex.Streaming.Sink.TripDriveSink;

import com.alex.Streaming.Bean.TripModel;
import com.alex.Streaming.Utils.ConfigLoader;
import com.alex.Streaming.Utils.DateUtil;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.phoenix.parse.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alex_liu
 * @Create 2022-11-22
 * @Description
 */
public class TripDriveToHBase extends RichSinkFunction<TripModel> {
    //定义日志操作对象
    private final static Logger logger = LoggerFactory.getLogger(TripDriveToHBase.class);
    //定义操作的hbase的表名
    private String tableName;
    //定义connection连接对象
    private Connection connection;
    //定义BufferedMutator对象
    private BufferedMutator bufferedMutator;
    //定义列族的名称
    private String cf = "cf";

    public TripDriveToHBase(String tableName){
        this.tableName = tableName;
    }

    /**
     * 初始化资源
     * @param parameters
     * @throws Exception
     */
    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        //TODO 1）定义hbase的连接配置对象
        org.apache.hadoop.conf.Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", ConfigLoader.get("zookeeper.quorum"));
        configuration.set("hbase.zookeeper.property.clientPort", ConfigLoader.get("zookeeper.clientPort"));
        configuration.set(TableInputFormat.INPUT_TABLE, tableName);
        //TODO 2）创建hbase的连接对象
        connection = ConnectionFactory.createConnection();
        //实例化BufferedMutator所需要的参数
        BufferedMutatorParams params = new BufferedMutatorParams(TableName.valueOf(tableName));
        //写缓存的大小
        params.writeBufferSize(128*1024*1024);
        bufferedMutator = connection.getBufferedMutator(params);
        logger.warn("获得hbase的连接对象，{}表对象初始化成功！", tableName);
    }

    /**
     * 释放资源
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        super.close();
        //释放资源的时候需要将缓冲区的数据递交一次hbase，否则数据可能会被丢失
        if(bufferedMutator!=null) bufferedMutator.close();
        if(connection!=null) connection.close();
    }

    /**
     * 每条数据调用一次该方法
     * @param tripModel
     * @param context
     * @throws Exception
     */
    @Override
    public void invoke(TripModel tripModel, Context context) throws Exception {
        Put put = markPut(tripModel);
        //将put对象追加到缓冲区
        bufferedMutator.mutate(put);
        //缓冲区写满后，刷到hbase中
        bufferedMutator.flush();
    }

    /**
     * @desc:封装hbase Put
     * @param tripModel
     * @return
     * @throws ParseException
     */
    private Put markPut(TripModel tripModel) throws ParseException {
        String rowKey = tripModel.getVin() + "_" + DateUtil.convertStringToDate(tripModel.getTripStartTime()).getTime();
        Put put = new Put(Bytes.toBytes(rowKey));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("vin"), Bytes.toBytes(tripModel.getVin()));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("lastSoc"), Bytes.toBytes(String.valueOf(tripModel.getLastSoc())));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("lastMileage"), Bytes.toBytes(String.valueOf(tripModel.getLastMileage())));

        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("tripStartTime"), Bytes.toBytes(tripModel.getTripStartTime()));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("start_BMS_SOC"), Bytes.toBytes(String.valueOf(tripModel.getStart_BMS_SOC())));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("start_longitude"), Bytes.toBytes(String.valueOf(tripModel.getStart_longitude())));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("start_latitude"), Bytes.toBytes(String.valueOf(tripModel.getStart_latitude())));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("start_mileage"), Bytes.toBytes(String.valueOf(tripModel.getStart_mileage())));

        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("end_BMS_SOC"), Bytes.toBytes(String.valueOf(tripModel.getEnd_BMS_SOC())));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("end_longitude"), Bytes.toBytes(String.valueOf(tripModel.getEnd_longitude())));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("end_latitude"), Bytes.toBytes(String.valueOf(tripModel.getEnd_latitude())));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("end_mileage"), Bytes.toBytes(String.valueOf(tripModel.getEnd_mileage())));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("tripEndTime"), Bytes.toBytes(tripModel.getTripEndTime()));

        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("mileage"), Bytes.toBytes(String.valueOf(tripModel.getMileage())));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("max_speed"), Bytes.toBytes(String.valueOf(tripModel.getMax_speed())));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("soc_comsuption"), Bytes.toBytes(String.valueOf(tripModel.getSoc_comsuption())));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("time_comsuption"), Bytes.toBytes(String.valueOf(tripModel.getTime_comsuption())));

        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("total_low_speed_nums"), Bytes.toBytes(String.valueOf(tripModel.getTotal_low_speed_nums())));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("total_medium_speed_nums"), Bytes.toBytes(String.valueOf(tripModel.getTotal_medium_speed_nums())));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("total_high_speed_nums"), Bytes.toBytes(String.valueOf(tripModel.getTotal_high_speed_nums())));

        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("Low_BMS_SOC"), Bytes.toBytes(String.valueOf(tripModel.getLow_BMS_SOC())));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("Medium_BMS_SOC"), Bytes.toBytes(String.valueOf(tripModel.getMedium_BMS_SOC())));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("High_BMS_SOC"), Bytes.toBytes(String.valueOf(tripModel.getHigh_BMS_SOC())));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("Low_BMS_Mileage"), Bytes.toBytes(String.valueOf(tripModel.getLow_BMS_Mileage())));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("Medium_BMS_Mileage"), Bytes.toBytes(String.valueOf(tripModel.getMedium_BMS_Mileage())));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("High_BMS_Mileage"), Bytes.toBytes(String.valueOf(tripModel.getHigh_BMS_Mileage())));

        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("tripStatus"), Bytes.toBytes(String.valueOf(tripModel.getTripStatus())));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("processTime"), Bytes.toBytes(DateUtil.getCurrentDateTime()));
        return put;
    }
}
