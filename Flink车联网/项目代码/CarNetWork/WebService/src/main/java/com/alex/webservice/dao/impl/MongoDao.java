package com.alex.webservice.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author Alex_liu
 * @Date 2023-01-14 1:24
 * @Description mongo使用mongoTemplate加载mongo服务访问数据
 */
@Repository
public class MongoDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

}