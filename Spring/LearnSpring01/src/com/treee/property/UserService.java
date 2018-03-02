package com.treee.property;

/**
 * Created by treee on -2018/3/1-
 */

public class UserService {
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    private UserDao userDao;

    public void getDaoData(){
        System.out.println("Service.......");
        userDao.getData();
    }
}
