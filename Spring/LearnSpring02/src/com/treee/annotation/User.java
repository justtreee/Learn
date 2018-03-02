package com.treee.annotation;

import org.springframework.stereotype.Component;

/**
 * Created by treee on -2018/3/2-
 */
@Component(value = "user")      //这一个注解相当与 <bean id="user" class=""/>
public class User {
    public void add(){
        System.out.println("add.........");
    }
}
