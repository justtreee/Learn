package com.treee.ioc;

        import org.junit.Test;
        import org.springframework.context.ApplicationContext;
        import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by treee on -2018/2/28-
 */

public class TestIOC {
    @Test
    public void testUser(){
        //1. 加载spring配置文件，根据配置文件创建对象
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        //2. 得到配置创建的对象
        User user = (User)context.getBean("user");
        System.out.println(user); //输出地址：com.treee.ioc.User@1c3a4799
        user.add(); //输出 add.....
    }
}
