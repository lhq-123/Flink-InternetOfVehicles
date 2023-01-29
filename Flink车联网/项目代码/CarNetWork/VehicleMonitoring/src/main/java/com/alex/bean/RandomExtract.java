package com.alex.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Alex_liu
 * @create 2023-01-29 14:38
 * @Description 随机抽取的车辆信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RandomExtract {
        private long taskId;
        private String car;
        private String date;
        private String dateHour;
}
