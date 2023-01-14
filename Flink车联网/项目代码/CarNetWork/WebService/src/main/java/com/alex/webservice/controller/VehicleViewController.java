package com.alex.webservice.controller;

import com.alex.webservice.annotation.AutoResponse;
import com.alex.webservice.annotation.LogAudit;
import com.alex.webservice.service.VehicleViewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Alex_liu
 * @Date 2023-01-14 23:13
 * @Description 车辆网项目web页面可视化控制类
 */
@Slf4j
@RestController
@RequestMapping("/XTplatform")
@Api(value = "车辆可视化")
public class VehicleViewController {

    @Autowired
    private VehicleViewService vehicleViewService;

    @LogAudit
    @AutoResponse
    @ApiOperation(value = "平台车辆总数", response = Long.class, responseContainer = "Long")
    @RequestMapping(value = "vehicle/totalNum", produces = { "application/json" }, method = RequestMethod.GET)
    public Object totalNum() {
        try {
            Long totalNum = vehicleViewService.totalNum();
            return totalNum;
        } catch (Throwable t) {
            log.error("failed to sql databases.\n", t);
            return null;
        }
    }

    @LogAudit
    @AutoResponse
    @ApiOperation(value = "平台在线车辆总数", response = Long.class, responseContainer = "Long")
    @RequestMapping(value = "vehicle/onlineNum", produces = { "application/json" }, method = RequestMethod.GET)
    public Object onlineNum() {
        try {
            Long totalNum = vehicleViewService.onlineNum();
            return totalNum;
        } catch (Throwable t) {
            log.error("failed to sql databases.\n", t);
            return null;
        }
    }

    @LogAudit
    @AutoResponse
    @ApiOperation(value = "平台行驶中车辆总数", response = Long.class, responseContainer = "Integer")
    @RequestMapping(value = "vehicle/drivingNum", produces = { "application/json" }, method = RequestMethod.GET)
    public Object drivingNum() {
        try {
            Integer totalNum = vehicleViewService.drivingNum();
            return totalNum;
        } catch (Throwable t) {
            log.error("failed to sql databases.\n", t);
            return null;
        }
    }
}