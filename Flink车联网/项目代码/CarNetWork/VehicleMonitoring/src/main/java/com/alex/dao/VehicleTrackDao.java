package com.alex.dao;

import com.alex.bean.VehicleTrack;

import java.util.List;

/**
 * @author Alex_liu
 * @create 2023-01-29 14:30
 * @Description
 */
public interface VehicleTrackDao {
    void insertBatchVehicleTrack(List<VehicleTrack> vehicleTracks);

}
