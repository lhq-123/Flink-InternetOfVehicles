package com.alex.webservice.service.impl;

import com.alex.webservice.bean.ElectricFenceBean;
import com.alex.webservice.mapper.ElectricFenceMapper;
import com.alex.webservice.service.ElectricFenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Alex_liu
 * @commpany vehicle
 * @Date 2022/9/16 2:05
 * @Description TODO 电子围栏后台数据服务接口，服务实现类
 */
@Service
public class ElectricFenceServiceImpl implements ElectricFenceService {

    @Autowired
    private ElectricFenceMapper electricFenceMapper;

    @Override
    public List<ElectricFenceBean> queryAll(Integer pageNo, Integer pageSize) {
        // todo 物理分页，mysql查询从索引'0'开始，所以用页码数减1
        if (pageNo <= 1) {
            pageNo = 0;
        } else {
            pageNo -= 1;
        }
        return electricFenceMapper.queryAll(pageNo * pageSize, pageSize);
    }

    @Override
    public Long totalNum() {
        return electricFenceMapper.totalNum();
    }
}
