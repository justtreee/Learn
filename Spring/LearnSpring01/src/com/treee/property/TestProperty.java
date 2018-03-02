package com.treee.property;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by treee on -2018/3/1-
 */

public class TestProperty {

    //有参构造 注入
    @Test
    public void testUser1(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        User_property user_property = (User_property) context.getBean("user2");

        user_property.test1();
    }

    //set方法注入
    @Test
    public void testUser2(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        User_set user_set = (User_set) context.getBean("user3");

        user_set.test1();
    }

    //对象的注入
    @Test
    public void testUser3(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = (UserService) context.getBean("userService");

        userService.getDaoData();
//        输出：
//        Service.......
//        get data
    }

    //复杂属性的注入
    @Test
    public void testUserc(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        User_c user_c = (User_c) context.getBean("userc");

        user_c.test();
    }
//    张三,李四,王五,
//    足球,篮球,乒乓球,
//    key: username  value: 张三,key: password  value: 123456,
//    赵六
}
