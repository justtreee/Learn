package com.treee.annotation;

import org.springframework.stereotype.Component;

/**
 * Created by treee on -2018/3/2-
 */

@Component(value = "userDao")
public class UserDao {
    public void add(){
        System.out.println("Dao.....");
    }
}
