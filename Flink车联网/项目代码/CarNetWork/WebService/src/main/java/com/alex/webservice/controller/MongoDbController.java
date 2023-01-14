package com.alex.webservice.controller;

import com.alex.webservice.annotation.AutoResponse;
import com.alex.webservice.annotation.LogAudit;
import com.alex.webservice.service.MongoService;
import com.alex.webservice.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Alex_liu
 * @Date 2023-01-14 1:40
 * @Description Mongo数据源测试类，查询自定义告警规则结果总数后台数据服务接口
 */
@Slf4j
@RestController
@Api(value = "Mongo数据源测试")
public class MongoDbController {

    @Autowired
    private MongoService mongoService;

    @LogAudit
    @AutoResponse
    @ApiOperation(value = "Mongo测试-计算自定义告警规则集合总数", response = Long.class, responseContainer = "Long")
    @RequestMapping(value = "mongo/totalNum", produces = { "application/json" }, method = RequestMethod.GET)
    public Object totalNum() {
        try {
            Long totalNum = mongoService.totalNum();
            return totalNum;
        } catch (Throwable t) {
            log.error("failed to sql databases.\n", t);
            return ResponseUtil.buildResult("failed.", t.getMessage());
        }
    }
}
