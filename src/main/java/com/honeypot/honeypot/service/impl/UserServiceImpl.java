package com.honeypot.honeypot.service.impl;

import com.honeypot.honeypot.dao.UserDao;
import com.honeypot.honeypot.entity.User;
import com.honeypot.honeypot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 获取用户信息的Dao方法
     * @return
     */
    @Override
    public List<User> getUserService() {
        return userDao.queryUser();
    }
}
