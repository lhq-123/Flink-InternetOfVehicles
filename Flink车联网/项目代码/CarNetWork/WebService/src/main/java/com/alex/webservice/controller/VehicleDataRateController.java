package com.alex.webservice.controller;

import com.alex.webservice.annotation.AutoResponse;
import com.alex.webservice.annotation.LogAudit;
import com.alex.webservice.bean.VehicleDataRateBean;
import com.alex.webservice.service.VehicleDataRateService;
import com.alex.webservice.service.VehicleViewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alex_liu
 * @Date 2023-01-14 2:02
 * @Description 原始数据正确率和错误率后台数据服务接口，控制器
 */
@Slf4j
@RestController
@Api(value = "原始数据质量接口")
public class VehicleDataRateController {

    @Autowired
    private VehicleDataRateService vehicleDataRateService;


    @ApiOperation(value = "查询数据质量记录", response = List.class, responseContainer = "List")
    @RequestMapping(value = "vehicleDataRate/queryAll", produces = { "application/json" }, method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "pageNo", value = "起始页", defaultValue = "1", dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "pageSize", value = "每页显示记录数", defaultValue = "10", dataType = "Integer")
    })
    @AutoResponse
    @LogAudit
    public Object queryAll(int pageNo, int pageSize) {
        Map resultMap = null;
        try {
            log.info("查询数据质量，起始页{},每页显示{}条记录",pageNo,pageSize);
            List<VehicleDataRateBean> vehicleDataRateBeanList = vehicleDataRateService.queryAll(pageNo, pageSize);

            resultMap = new HashMap<String, Object>(2);
            resultMap.put("vehicleDataRateBeanList", vehicleDataRateBeanList);
            resultMap.put("totalNum", vehicleDataRateService.totalNum());
        } catch (Throwable t) {
            log.error("failed to sql databases.\n", t);
        }
        return resultMap;
    }
}