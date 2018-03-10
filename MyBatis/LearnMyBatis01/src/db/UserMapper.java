package db;

import bean.User;
import org.apache.ibatis.annotations.Select;

/**
 * Created by treee on -2018/3/10-
 */

public interface UserMapper {
    @Select(value = "select * from user where id=#{id}")
    public User getUser(Long id);
}
