package com.alex.webservice.controller;

import com.alex.webservice.annotation.AutoResponse;
import com.alex.webservice.annotation.LogAudit;
import com.alex.webservice.service.HeatMapService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: Alex_liu
 * @Date: 2023-01-14 1:57
 * @Description: 热力图后台数据服务接口，控制器类
 */
@Slf4j
@RestController
// TODO 注解实现，针对单个请求解决跨域请求问题
//@CrossOrigin(origins = "*",allowCredentials="true",allowedHeaders = "",methods = {})
@Api(value = "热力图数据源接口")
public class HeatMapController {

    @Autowired
    private HeatMapService heatMapService;

    @AutoResponse
    @LogAudit
    @ApiOperation(value = "查询某城市车辆热力图", response = List.class, responseContainer = "List")
    @RequestMapping(value = "heatmap/queryAllByCity", produces = { "application/json" }, method = RequestMethod.GET)
    @ApiImplicitParam(paramType="query", name = "city", value = "城市:例如：杭州、武汉、北京、深圳等", required = true, dataType = "String")
    public Object queryMapPoints(String city) {
        List<List<Double>> heatMapPointBeanList = null;
        try {
            log.info("查询{} 市车辆热力图!", city);
            heatMapPointBeanList = heatMapService.queryPointsByCity(city);
        } catch (Throwable t) {
            log.error("failed to sql databases.\n", t);
        }
        return heatMapPointBeanList;
    }

    @AutoResponse
    @LogAudit
    @ApiOperation(value = "查询车辆热力图", response = List.class, responseContainer = "List")
    @RequestMapping(value = "heatmap/queryPoints", produces = { "application/json" }, method = RequestMethod.GET)
    public @ResponseBody Object queryPoints() {
        List<List<Double>> heatMapPointBeanList = null;
        try {
            log.info("查询车辆热力图");
            heatMapPointBeanList = heatMapService.queryAllPoints();
        } catch (Throwable t) {
            log.error("failed to sql databases.\n", t);
        }
        return heatMapPointBeanList;
    }

}