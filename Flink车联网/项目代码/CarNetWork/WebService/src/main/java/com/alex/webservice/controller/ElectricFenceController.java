package com.alex.webservice.controller;

import com.alex.webservice.annotation.AutoResponse;
import com.alex.webservice.annotation.LogAudit;
import com.alex.webservice.bean.ElectricFenceBean;
import com.alex.webservice.service.ElectricFenceService;
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
 * @Date 2023-01-14 2:07
 * @Description 电子围栏后台数据服务接口，控制器类
 */
@Slf4j
@RestController
@Api(value = "电子围栏数据服务接口")
public class ElectricFenceController {

    @Autowired
    private ElectricFenceService electricFenceService;

    @AutoResponse
    @LogAudit
    @ApiOperation(value = "查询电子围栏车辆记录", response = List.class, responseContainer = "List")
    @RequestMapping(value = "electricFence/queryAll", produces = { "application/json" }, method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "pageNo", value = "起始页", defaultValue = "1", dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "pageSize", value = "每页显示记录数", defaultValue = "10", dataType = "Integer")
    })
    public Object queryAll(int pageNo, int pageSize) {
        Map<String, Object> resultMap = new HashMap<>(2);
        try {
            log.info("查询数据质量，起始页{},每页显示{}条记录",pageNo,pageSize);
            List<ElectricFenceBean> electricFenceBeanList = electricFenceService.queryAll(pageNo, pageSize);
            resultMap.put("electricFenceBeanList", electricFenceBeanList);
            resultMap.put("totalNum", electricFenceService.totalNum());
        } catch (Throwable t) {
            log.error("failed to sql databases.\n", t);
        }
        return resultMap;
    }
}