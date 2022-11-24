#!/bin/bash
#yesterday=`date --date '1 days ago' +%Y%m%d`
currentdate=$(date "+%Y-%m-%d")
db="vehicle_ods"
tableName="vehicle_rightdata"
ssh Flink01 `/opt/module/hive/bin/hive -e "use $db;alter table $tableName add partition (dt='$currentdate') location '/user/hive/apps/warehouse/flinkcar/$db/$tableName/$currentdate'"`
if [ $? -eq 0 ]; then
  echo "load $tableName partition $currentdate succesful."
else
  echo "load $tableName partition $currentdate error."
fi