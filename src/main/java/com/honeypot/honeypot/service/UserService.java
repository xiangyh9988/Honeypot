package com.honeypot.honeypot.service;

import com.honeypot.honeypot.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface UserService {
    /**
     * 获取所有用户账号和密码
     * @return
     */
    @Transactional
    List<User> getUserService();

}
