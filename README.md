# Flink_Internet-of-Vehicles
基于Flink的车联网实时数据平台

```sql
StreamingAnalysis
    │  pom.xml
    │  
    ├─src
    │  ├─main
    │  │  ├─java
    │  │  │  └─com
    │  │  │      └─lhq
    │  │  │          └─Streaming
    │  │  │              ├─Bean
    │  │  │              │      DateFormatDefine.java
    │  │  │              │      TripModel.java
    │  │  │              │      
    │  │  │              ├─Function
    │  │  │              │      TripDriveSampleWindowFunction.java
    │  │  │              │      TripDriveWindowFunction.java
    │  │  │              │      
    │  │  │              ├─MainTask
    │  │  │              │      BaseTask.java
    │  │  │              │      KafkaSourceDataTask.java
    │  │  │              │      KafkaSourceDataTaskOptimize.java
    │  │  │              │      TripDriveTask.java
    │  │  │              │      
    │  │  │              ├─Phoenix
    │  │  │              │      DivisionAnalysis.java
    │  │  │              │      JDBCUtil.java
    │  │  │              │      PhoenixJDBCUtil.java
    │  │  │              │      TripDivisionPhoenixAnalysis.java
    │  │  │              │      TripDriveSamplePhoenixAnalysis.java
    │  │  │              │      
    │  │  │              ├─Sink
    │  │  │              │      ToHBaseDetailSinkOptimize.java
    │  │  │              │      ToHBaseSink.java
    │  │  │              │      ToHBaseSinkOptimize.java
    │  │  │              │      ToHiveSink.java
    │  │  │              │      TripDriveSampleToHBaseSink.java
    │  │  │              │      TripDriveToHBaseSink.java
    │  │  │              │      
    │  │  │              ├─Source
    │  │  │              │      FlinkReadFromKafka.java  #flink模拟kafka消费者
    │  │  │              │      FlinkWriteToKafka.java   #flink读取日志文件里的数据到kafka里(相当于生产者)
    │  │  │              │      KafkaMsgProducer.java    
    │  │  │              │      
    │  │  │              ├─Utils
    │  │  │              │      ConfigLoader.java
    │  │  │              │      DateUtil.java
    │  │  │              │      JsonParseUtil.java
    │  │  │              │      StringUtil.java
    │  │  │              │      VehicleDataObj.java
    │  │  │              │      
    │  │  │              └─Watermark
    │  │  │                      TripDriveWatermark.java
    │  │  │                      
    │  │  └─resources
    │  │          conf.properties
    │  │          core-site.xml
    │  │          hbase-site.xml
    │  │          hdfs-site.xml
    │  │          logback.xml
```

