package com.treee.jdbc;

import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by treee on -2018/3/3-
 */

public class JdbcTemplateDemo2 {
    //1. 查询表中有多少条记录
    @Test
    public void testCount(){
        //设置数据库信息
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql:///jdbc");
        dataSource.setUsername("root");
        dataSource.setPassword("");

        //创建模板对象，设置数据源
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        //sql
        String sql = "SELECT COUNT(*) FROM tempspring";

        //调用方法
        int cnt = jdbcTemplate.queryForObject(sql, Integer.class);
        System.out.println(cnt);
    }

    //2. 查询返回对象
    @Test
    public void testObj(){
        //设置数据库信息
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql:///jdbc");
        dataSource.setUsername("root");
        dataSource.setPassword("");

        //创建模板对象，设置数据源
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        //sql,根据name查询
        String sql = "SELECT * FROM tempspring WHERE name=?";

        //调用方法,
        // 第二个参数是接口，需要自己写类实现接口，自己做数据封装
        User user = jdbcTemplate.queryForObject(sql, new MyRowMapper(),"amy");
        System.out.println(user);
    }

}

//2. 查询返回对象 写类实现接口，自己做数据封装
class MyRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        //1. 从结果集里面吧数据得到
        Integer id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        //2. 把数据封装到对象里面
        User user = new User();
        user.setId(id);
        user.setName(name);

        return user;
    }
}