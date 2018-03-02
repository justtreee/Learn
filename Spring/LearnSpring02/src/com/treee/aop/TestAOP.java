package com.treee.aop;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by treee on -2018/3/2-
 */

public class TestAOP {

    @Test
    public void testAOP(){
        ApplicationContext context = new ClassPathXmlApplicationContext("aspecjAOP.xml");
        Book book = (Book) context.getBean("book");
        book.test();
    }
}
