package com.treee.learnspringboot01.Controller;

import com.treee.learnspringboot01.Bean.BookBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by treee on -2018/3/5-
 */
@RestController
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class BookController {
    @Autowired
    private BookBean bookBean;
    @RequestMapping("/book")
    public String book() {
        return "Hello Spring Boot! The BookName is "+bookBean.getName()+";and Book Author is "+bookBean.getAuthor()+";and Book price is "+bookBean.getPrice();
    }
}
