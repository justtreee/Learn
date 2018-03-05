package com.treee.learnspringboot01.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by treee on -2018/3/5-
 */
@RestController
@EnableAutoConfiguration
public class UserController {
    @Value(value = "${myuser.name}")
    private String userName;
    @Value(value = "${myuser.pinyin}")
    private String userPinYin;
    @Value(value = "${myuser.age}")
    private String userAge;

    @RequestMapping(value = "/",produces = "text/plain;charset=UTF-8")
    private String index(){
        return "Hello Spring Boot! The UserName is "+userName+";and PinYin is "+userPinYin+";and Age is "+userAge;
    }
}
