package com.treee.c3p0;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by treee on -2018/3/4-
 */

public class UserDao {
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private JdbcTemplate jdbcTemplate;

    public void add(){
        String sql = "insert into tempspring values (?,?)";
        jdbcTemplate.update(sql, "4", "david");
    }
}
