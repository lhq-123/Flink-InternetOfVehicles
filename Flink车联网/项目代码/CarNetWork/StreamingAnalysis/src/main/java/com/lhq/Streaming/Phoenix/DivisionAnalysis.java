package com.lhq.Streaming.Phoenix;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author liu
 * @Create 2022-11-20
 * @Description
 *    调用flink工具类初始化task作业
 */

@Data
@AllArgsConstructor
public class DivisionAnalysis {
    // todo 车架号
    private String vin;
    // todo 分析指标名称
    private String name;
    // todo 分析指标结果1
    private String analyzeValue1;
    // todo 分析指标结果2
    private String analyzeValue2;
    // todo 分析指标结果3
    private String analyzeValue3;
    // todo 分析指标类型 1：里程-soc-行程消耗时间分析，2：高速-中速-低速里程soc消耗分析，
    //  3：高速-中速-低速里程分析，4：高速、中速、低速次数分析，5：其他业务指标分析'
    private float analyzeType;
    // todo 终端时间
    private String terminalTime;

}
