package com.treee.service;

import com.treee.dao.OrdersDao;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by treee on -2018/3/4-
 */

@Transactional
public class OrdersService {
    public void setOrdersDao(OrdersDao ordersDao) {
        this.ordersDao = ordersDao;
    }

    private OrdersDao ordersDao;

    //调用dao的方法
    //业务逻辑层，写转账业务
    public void Transfer(){
        //amy转出1000
        ordersDao.decrease(1000,"amy");

        //异常
        //int i = 10/0;

        //bob收到1000
        ordersDao.increase(1000,"bob");
    }
}
