package com.honeypot.honeypot.controller;

import com.honeypot.honeypot.entity.User;
import com.honeypot.honeypot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/getuser")
    public List<User> getUser(){
        return userService.getUserService() ;
    }
}
