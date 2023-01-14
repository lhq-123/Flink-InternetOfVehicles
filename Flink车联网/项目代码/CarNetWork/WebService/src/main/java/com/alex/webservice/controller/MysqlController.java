package com.alex.webservice.controller;

import com.alex.webservice.annotation.AutoResponse;
import com.alex.webservice.annotation.LogAudit;
import com.alex.webservice.bean.ElectronicFenceVinsBean;
import com.alex.webservice.service.MySqlService;
import com.alex.webservice.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Alex_liu
 * @Date 2023-01-14 1:35
 * @Description Mysql数据源测试类，查询车辆信息数据后台数据服务接口
 */
@Slf4j
@RestController
@Api(value = "Mysql数据源测试")
public class MysqlController {

    @Autowired
    private MySqlService mySqlService;

    /**
     * @desc 调用自定义日志AOP注解
     * @param pageNo
     * @param pageSize
     * @return
     */
    @LogAudit
    @ApiOperation(value = "查询电子围栏已存在的车辆", response = List.class, responseContainer = "List")
    @RequestMapping(value = "mysql/queryAll", produces = { "application/json" }, method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "pageNo", value = "起始页", defaultValue = "1", dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "pageSize", value = "每页显示记录数", defaultValue = "10", dataType = "Integer")
    })
    public Object queryAll(int pageNo, int pageSize) {
        try {
            log.info("查询电子围栏存在车辆，起始页{},每页显示{}条记录",pageNo,pageSize);
            List<ElectronicFenceVinsBean> vinsBeanList = mySqlService.queryAll(pageNo, pageSize);
            return ResponseUtil.buildSuccessResult(mySqlService.totalNum(),"successfully.", vinsBeanList);
        } catch (Throwable t) {
            log.error("failed to sql databases.\n", t);
            return ResponseUtil.buildResult("failed.", t.getMessage());
        }
    }

    @LogAudit
    @AutoResponse
    @ApiOperation(value = "查询电子围栏已存在的车辆总数", response = Long.class, responseContainer = "Long")
    @RequestMapping(value = "mysql/totalNum", produces = { "application/json" }, method = RequestMethod.GET)
    public Object totalNum() {
        try {
            Long totalNum = mySqlService.totalNum();
            return totalNum;
        } catch (Throwable t) {
            log.error("failed to sql databases.\n", t);
            return null;
        }
    }
}