[TOC]



# 原始数据实时ETL

## 将数据文件中的JSON数据推送到 kafka 中

1.开启hdp、Zookeeper、kafka集群

2.创建topic

```shell
使用kafkatool创建vehicledata topic
```

3.开发Flink实现写入数据到Kafka中

SourceDataProcess模块下的cn.lhq.flink.source.Collection包中的FlinkWriteToKafka方法

## 原始数据实时ETL

根据Kafka中的数据进行实时ETL过滤，对数据进行划分，并将划分的数据分别落地到不同的数据存储组件中

![Snipaste_2022-11-19_18-12-23](assets\Snipaste_2022-11-19_18-12-23.png)

在对原始数据做处理的时候，需要引入一些工具类对数据进行处理，比如日期处理，配置文件加载等，最后再对JSON数据进行解析

```properties
日期处理工具:StreamingAnalysis模块com.lhq.Utils.DateUtil.java
配置文件加载工具:StreamingAnalysis模块com.lhq.Utils.ConfigLoader.java
字符串处理工具:StreamingAnalysis模块com.lhq.Utils.StringUtil.java
JSON解析工具:StreamingAnalysis模块com.lhq.Utils.JsonParseUtil.java
```

## ETL流程

![原始数据实时ETL](assets\原始数据实时ETL.png)

```properties
1.创建流式环境，设置检查点、kafka分区发现、任务重启策略，数据积压
2.根据kafka属性配置创建FlinkKafkaConsumer，消费kafka数据
3.根据kafka数据获得DataStream，进行json解析
4.消费数据中的数据解析逻辑:
  (1)得到解析成功的数据，称为正确数据
  (2)原始文本为一行可成功解析的json格式数据，且数据中存在不可或缺字段vin、terminalTime
  4.2 得到解析失败的数据，称为异常数据
      格式不正确；缺少必要字段，导致数据格式不正确的原因：
      (1)网络原因，车辆行驶在信号差的地方，数据传输不完整；
      (2)数据传输过程中，数据会传TSP造成数据丢失；
      (3)数据采集终端设备故障，导致数据部分丢失
5.数据入库，正确数据入hive与hbase库各一份，错误数据入hive
  5.1 先入hdfs，再关联hive表
```

## ETL实现步骤

**为了实现代码复用，提高开发效率，创建flink流式处理环境的工具类**

StreamingAnalysis模块com.lhq.Streaming.MainTask.BaseTask

**ETL代码实现：**

StreamingAnalysis模块com.lhq.Straming.MainTask.KafkaSourceDataTaskPlus.java

```properties
1 初始化flink流式处理的开发环境
2 设置按照事件时间处理数据（划分窗口或者添加水印都需要事件时间）
3 开启checkpoint
   3.1：设置每隔30秒钟开启checkpoint
   3.2：设置检查点的model，exactly-once，保证数据消费一次，数据不重复消费
   3.3：设置两次checkpoint时间间隔，避免两次间隔太近导致频繁checkpoint而出现业务处理能力下降
   3.4：设置checkpoint的超时时间
   3.5：设置checkpoint最大的尝试次数，同一个时间有几个checkpoint并行执行
   3.6：设置checkpoint取消的时候，是否保留checkpoint，checkpoint默认会在job取消的时候删除
   3.7：设置执行job过程中，保存检查点错误时，job不失败
   3.8：设置检查点存储的位置，使用rocksDBStateBackend，存储到本地+hdfs分布式文件，增量检查点
4 设置任务的重启策略（固定延迟重启策略、失败率重启策略、无重启策略）
5 创建flink消费kafka数据的对象，指定kafka的参数信息
   5.1：设置kafka集群地址
   5.2：设置消费者组id
   5.3：设置kafka的分区感知（动态监测）
   5.4：设置key和value的反序列化
   5.5：设置自动递交offset位置策略
   5.6：创建kafka的消费者实例
   5.7：设置自动递交offset到保存到检查点
6 将kafka消费者对象添加到环境中
7 将json字符串解析成对象
8 获取到异常的数据
9 获取到正常的数据
10 将异常的数据写入到hdfs中（StreamingFileSink、BucketingSink）
   StreamingFileSink是flink1.10的新特性
11 将正常的数据写入到hdfs中
12 将正常的数据写入到hbase中
13 启动作业，运行任务
```

