-- 按天为维度计算数据正确率、错误率
-- 结果数据表
create table vehicle_data_rate_day
(
    series_no           varchar(64) null comment '记录序列号',
    src_total_num       bigint      not null comment '原数据正确数据总数',
    error_src_total_num bigint      not null comment '原数据错误数据总数',
    data_accuracy       float(7, 4) not null comment '原始数据正确率',
    data_error_rate     float(7, 4) not null comment '原始数据错误率',
    day                 varchar(11) not null comment '按天统计',
    process_date        varchar(25) null comment '记录计算时间'
);

-- 按周为维度计算数据正确率、错误率
-- 结果数据表
create table vehicle_data_rate_week
(
    series_no           varchar(64) null comment '记录序列号',
    src_total_num       bigint      not null comment '原数据正确数据总数',
    error_src_total_num bigint      not null comment '原数据错误数据总数',
    data_accuracy       float(7, 4) not null comment '原始数据正确率',
    data_error_rate     float(7, 4) not null comment '原始数据错误率',
    week                varchar(11) not null comment '按周统计',
    process_date        varchar(25) null comment '记录计算时间'
);

-- 按月为维度计算数据正确率、错误率
-- 结果数据表
create table vehicle_data_rate_month
(
    series_no           varchar(64) null comment '记录序列号',
    src_total_num       bigint      not null comment '原数据正确数据总数',
    error_src_total_num bigint      not null comment '原数据错误数据总数',
    data_accuracy       float(7, 4) not null comment '原始数据正确率',
    data_error_rate     float(7, 4) not null comment '原始数据错误率',
    month               varchar(11) not null comment '按月统计',
    process_date        varchar(25) null comment '记录计算时间'
);