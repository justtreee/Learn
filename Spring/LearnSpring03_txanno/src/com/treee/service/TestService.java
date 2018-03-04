package com.treee.service;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by treee on -2018/3/4-
 */

public class TestService {
    @Test
    public void testDemo(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        OrdersService service = (OrdersService) context.getBean("ordersService");
        service.Transfer();
    }

}
