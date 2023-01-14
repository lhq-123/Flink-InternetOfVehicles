package com.alex.webservice.service.impl;

import com.alex.webservice.dao.impl.MongoDao;
import com.alex.webservice.service.MongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Alex_liu
 * @commpany vehicle
 * @Date 2022/9/16 1:39
 * @Description TODO 编写mongo测试接口，service实现类
 */
@Service
public class MongoServiceImpl implements MongoService {

    @Autowired
    private MongoDao mongoDao;

    @Override
    public Long totalNum() {
        MongoTemplate mongoTemplate = mongoDao.getMongoTemplate();
        System.out.println(mongoTemplate);
        long totalNum =  mongoTemplate.getCollection("custom_rule_alarm").countDocuments();
        return totalNum;
    }

}