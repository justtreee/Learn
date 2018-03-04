package com.treee.c3p0;

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

        UserService service = (UserService) context.getBean("userService");
        service.add();

    }
}
