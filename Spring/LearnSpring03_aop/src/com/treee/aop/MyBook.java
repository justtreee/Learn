package com.treee.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * Created by treee on -2018/3/3-
 */

@Aspect
public class MyBook {

    //在方法上面使用注解完成增强配置
    @Before(value = "execution(* com.treee.aop.Book.*(..))")
    public void before1(){
        System.out.println("before.........");
    }
}