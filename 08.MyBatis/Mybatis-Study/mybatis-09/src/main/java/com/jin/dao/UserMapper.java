package com.jin.dao;

import com.jin.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    //根据id查询用户
    User queryUserById(@Param("id") int id);

    int updateUser(User user);
}
