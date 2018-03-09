package com.treee.learnspringboot02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by treee on -2018/3/9-
 */
@RestController
public class DataController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping("save") //不需要斜杠
    public User save(String username, String password){
        User user = userRepository.save(new User(null, username, password));
        return user;
    }//http://localhost:8080/save?username=user1&password=user1

    @RequestMapping("query")
    public User query(String username, String password){
        User user = userRepository.withUsernameAndPasswordQuery(username, password);
        return user;
    }//http://localhost:8080/query?username=admin&password=admin

    @RequestMapping("queryall")
    @ResponseBody
    public List<User> queryAll(){
        List<User> list = new ArrayList<User>();
        list = userRepository.findAll();
        return list;
    }//http://localhost:8080/queryall
}
