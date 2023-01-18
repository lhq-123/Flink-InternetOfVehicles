package com.alex.webservice.service.impl;

import com.alex.webservice.bean.ElectronicFenceVinsBean;
import com.alex.webservice.dao.impl.MysqlJdbcDao;
import com.alex.webservice.service.MySqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Alex_liu
 * @Date 2023-01-15 1:33
 * @Description 编写mysql测试接口，service实现类
 */
@Service
public class MySqlServiceImpl implements MySqlService {

    @Autowired
    private MysqlJdbcDao mysqlJdbcDao;

    @Override
    public List<ElectronicFenceVinsBean> queryAll(int pageNo, int pageSize) {
        if (pageNo <= 1) {
            pageNo = 1;
        }
        JdbcTemplate jdbcTemplate = mysqlJdbcDao.getJdbcTemplate();
        // todo 物理分页
        String sql = "select * from electronic_fence_vins limit " + (pageNo - 1) + "," + pageSize;
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);
        ElectronicFenceVinsBean electronicFenceVinsBean = null;
        List<ElectronicFenceVinsBean> vinsBeanList = new ArrayList<ElectronicFenceVinsBean>();
        for (Map<String, Object> stringObjectMap : mapList) {
            electronicFenceVinsBean =new ElectronicFenceVinsBean();
            int settingId = Integer.parseInt(stringObjectMap.get("setting_id").toString());
            String vin = stringObjectMap.get("vin").toString();
            electronicFenceVinsBean.setSettingId(settingId);
            electronicFenceVinsBean.setVin(vin);
            vinsBeanList.add(electronicFenceVinsBean);
        }
        return vinsBeanList;
    }

    @Override
    public Long totalNum() {
        JdbcTemplate jdbcTemplate = mysqlJdbcDao.getJdbcTemplate();
        String sql = "select count(1) total from electronic_fence_vins";
        Long totalNum = jdbcTemplate.queryForObject(sql, Long.class);
        return totalNum;
    }
}
