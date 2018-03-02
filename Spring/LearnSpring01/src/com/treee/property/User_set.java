package com.treee.property;

/**
 * Created by treee on -2018/3/1-
 */

public class User_set {
    private String username;
    //set方法注入的setter构造方法
    public void setUsername(String username) {
        this.username = username;
    }

    public void test1(){
        System.out.println("Username: "+ username);
    }
}
