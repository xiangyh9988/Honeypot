package com.honeypot.honeypot.dao;

import com.honeypot.honeypot.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;
@Mapper
public interface UserDao {
//    @Select("Select id,username,password from user")
    public List<User> queryUser();

}
