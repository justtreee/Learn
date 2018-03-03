package com.treee.jdbc;

import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Created by treee on -2018/3/3-
 */

public class JdbcTemplateDemo1 {

    //1. 添加操作
    @Test
    public void add(){
        //设置数据库信息
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql:///jdbc");
        dataSource.setUsername("root");
        dataSource.setPassword("");

        //创建模板对象，设置数据源
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        //调用模板对象里面的方法实现操作
        //下面这行sql是创建一个表
        //jdbcTemplate.execute("create table tempSpring(id int primary key,name varchar(32))");
//        创建sql语句
        String sql = "insert into tempspring values (?,?)";
        int rows = jdbcTemplate.update(sql, "1","amy");
        //rows表示影响的行数
        System.out.println(rows);
    }
}
