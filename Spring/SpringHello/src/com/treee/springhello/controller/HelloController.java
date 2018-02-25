package com.treee.springhello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by treee on -2018/2/24-
 */

@Controller
@RequestMapping(value = "/hello", method = RequestMethod.GET)
public class HelloController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String printHello(ModelMap model) {
        model.addAttribute("msg", "Spring MVC Hello World");
        model.addAttribute("name", "treee");
        return "hello";
    }
}

/*
1.@Controller注解：采用注解的方式定义该类为处理请求的Controller类；
2.@RequestMapping解：用于定义一个请求映射，value为请求的url，method用以指定该请求类型，一般为get和post，代码中要调用printHello方法，即可访问 hello/hello
3.return “hello”：处理完该请求后返回的页面，此请求返回 hello.jsp页面。
*/
