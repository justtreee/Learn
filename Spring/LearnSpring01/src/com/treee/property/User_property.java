package com.treee.property;

/**
 * Created by treee on -2018/3/1-
 */

public class User_property {
    private String username;

    //有参构造使用的构造方法
    public User_property(String username) {
        this.username = username;
    }

    public void test1(){
        System.out.println("Username: "+ username);
    }

}
