package com.treee.c3p0;

/**
 * Created by treee on -2018/3/4-
 */

public class UserService {
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    private UserDao userDao;

    public void add(){
        userDao.add();
    }
}
