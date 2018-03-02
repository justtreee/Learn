package com.treee.annotation;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by treee on -2018/3/2-
 */

public class TestAnno {

    //测试User类
    @Test
    public void testUser(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        User user = (User) context.getBean("user");
        System.out.println(user);
        user.add();

//        com.treee.annotation.User@35fc6dc4
//        add.........
    }

    //测试UserService调用dao对象属性
    @Test
    public void testService(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = (UserService) context.getBean("userService");
        System.out.println(userService);
        userService.add();
//        com.treee.annotation.UserService@448ff1a8
//        Service......
//        Dao.....
    }
}
