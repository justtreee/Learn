package com.treee.aop;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * Created by treee on -2018/3/2-
 */

//增强类（把Book类增强）
public class MyBook {
    public void before1(){
        System.out.println("前置增强.....");
    }//预计先输出这个，再输出Book中的test

    public void after1(){
        System.out.println("后置增强.....");
    }//预计先输出test，再输出后置

    //环绕通知
    public void around1(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //方法之前
        System.out.println("方法之前...");

        //执行被增强的方法
        proceedingJoinPoint.proceed();

        //方法之后
        System.out.println("方法之后...");

    }
}
