package com.lhq;

import com.lhq.Streaming.Phoenix.PhoenixJDBCUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestPhoenix {
    public static void main(String[] args) throws SQLException {
        try {
            // 创建库
            PhoenixJDBCUtil.createSchema("CREATE SCHEMA IF NOT EXISTS TESTDB");
            // 创建表
            PhoenixJDBCUtil.create("CREATE TABLE user (id varchar PRIMARY KEY,name varchar ,passwd varchar)");
            // 插入单条数据
            PhoenixJDBCUtil.upsert("upsert into user(id,name,passwd) values(?,?,?)",new String[]{"2","lisi", "2222"});
            // 查询数据
            List<String[]> result = PhoenixJDBCUtil.select("select * from user");
            result.forEach(strings -> {
                System.out.println(strings[0] + "-" + strings[1] + "-" +strings[2]);
            });
            // 删除数据
            PhoenixJDBCUtil.delete("delete from user where ID = '1'");
            // 全量删除
            PhoenixJDBCUtil.delete("delete from user");
            // 批量插入
            ArrayList<String[]> paramList = new ArrayList();
            paramList.add(new String[]{"1","zs","111"});
            paramList.add(new String[]{"2","ls","222"});
            paramList.add(new String[]{"3","ww","333"});
            PhoenixJDBCUtil.upsertBatch("upsert into user(id,name,passwd) values(?,?,?)", paramList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}