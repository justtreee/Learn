package com.treee.ioc;

/**
 * Created by treee on -2018/2/28-
 */

public class User {
    public void add(){
        System.out.println("add.....");
    }

    public static void main(String[] args) {
        //原始做法：
        User user = new User();
        user.add();
    }
}
