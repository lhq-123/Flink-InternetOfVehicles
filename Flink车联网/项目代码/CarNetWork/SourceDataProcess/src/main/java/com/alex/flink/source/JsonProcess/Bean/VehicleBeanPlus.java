package com.alex.flink.source.JsonProcess.Bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleBeanPlus {

    private int batteryAlarm;
    private int carMode;
    private double minVoltageBattery89;
    private int chargeStatus;
    private String vin;
    private List<Integer> probeTempList;
    private int chargeTemperatureProbeNum;
    private int childSystemNum;
}
