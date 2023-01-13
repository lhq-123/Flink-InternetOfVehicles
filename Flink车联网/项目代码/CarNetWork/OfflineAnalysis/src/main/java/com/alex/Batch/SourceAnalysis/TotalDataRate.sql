
-- 原始数据准确率计算，以原始数据解析结果表为数据源，获取原始数据中正确解析的数据以及原始数据中解析错误的数据。目前在Hive表中存在符合条件的两张表为”vehicle_right”、“vehicle_error”
-- 计算公式：
--     名词：srcTotalNum（正确数据总数）、errorTotalNum（错误数据总数）、srcDataRate（正确数据率）、errorDataRate（错误数据率）
--         srcDataRate = srcTotalNum / (srcTotalNum + errorTotalNum)
--         errorDataRate = 1 - srcDataRate
-- 什么是正确数据，什么是错误数据
--      正确数据：解析json为对象成功的数据,有必要字段
--      错误数据：Json格式错误，vin、terminalTime为空


-- sql获得正确数据总数、错误数据总数
SELECT srcTotalNum, errorTotalNum FROM (SELECT count(1) srcTotalNum FROM itcast_src) src, (SELECT count(1) errorTotalNum FROM itcast_error) error

-- 结果数据存储mysql结果表设计
create table vehicle_data_rate
(
    series_no           varchar(64) null comment '记录序列号',
    src_total_num       bigint      not null comment '原数据正确数据总数',
    error_total_num bigint      not null comment '原数据错误数据总数',
    data_accuracy       float(7, 4) not null comment '原始数据正确率',
    data_error_rate     float(7, 4) not null comment '原始数据错误率',
    process_date        varchar(25) null comment '记录计算时间'
);