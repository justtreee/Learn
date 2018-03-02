package com.treee.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by treee on -2018/3/2-
 */

@Service("userService")  //可以不写value
public class UserService {
    //得到dao对象
    //定义dao类型属性

    //====第一种注解方式 @Autowired =======
    //在dao属性上面使用注解，完成对象注入
//    @Autowired
//    private UserDao userDao;
    //使用注解不需要setter方法

    //====第二种注解方式 @Resource =======
    @Resource(name = "userDao")  //注意内部为name
    private UserDao userDao;

    public void add(){
        System.out.println("Service......");
        userDao.add();
    }
}
