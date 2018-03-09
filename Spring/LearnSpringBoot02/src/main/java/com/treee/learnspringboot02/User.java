package com.treee.learnspringboot02;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

/**
 * Created by treee on -2018/3/9-
 */

@Entity
@NamedQuery(name = "User.withUsernameAndPasswordQuery",
query = "select u from User u where u.username=?1 and u.password=?2")
public class User {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;

    public User(){

    }

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

}
