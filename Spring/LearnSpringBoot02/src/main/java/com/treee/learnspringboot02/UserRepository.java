package com.treee.learnspringboot02;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by treee on -2018/3/9-
 */

public interface UserRepository extends JpaRepository<User, Long>{


    //自定义的sql
    @Query("select u from User u where u.username=:username and u.password=:password")
    User withUsernameAndPasswordQuery(@Param("username") String username, @Param("password") String password);

}
