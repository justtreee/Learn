package com.treee.dao;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by treee on -2018/3/4-
 */

public class OrdersDao {
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private JdbcTemplate jdbcTemplate;

    //在dao层中，我们只做对数据库操作的方法

    //amy余额减少
    public void decrease(Integer num, String name){
        String sql = "update account set balance = balance - ? where username = ?";
        jdbcTemplate.update(sql, num, name);

    }

    //bob余额增加
    public void increase(Integer num, String name){
        String sql = "update account set balance = balance + ? where username = ?";
        jdbcTemplate.update(sql, num, name);
    }
}
