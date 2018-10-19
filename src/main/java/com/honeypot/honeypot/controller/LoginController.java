package com.honeypot.honeypot.controller;

import com.alibaba.fastjson.JSONObject;
import com.honeypot.honeypot.entity.User;
import com.honeypot.honeypot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Map<String,Object> login(@RequestBody JSONObject updateJson, HttpSession session){
        Map<String,Object> modelMap = new HashMap<>();
        String passwordMD5 = null;
        String password = updateJson.getString("password");
        String userName = updateJson.getString("username");
        if (password.equals("") || userName.equals("")) {
            modelMap.put("success", false);
            return modelMap;
        }
        try{
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64Encoder = new BASE64Encoder();
            passwordMD5 = base64Encoder.encode(md5.digest(password.getBytes("UTF-8")));
        } catch(Exception e){
            System.out.print(e);
        }
        User nowUser = userService.getUserByUsername(updateJson.getString("username"));
        // 与数据库中相应用户对象的密码md5对比
        if( nowUser != null && passwordMD5.equals(userService.getUserByUsername(updateJson.getString("username")).getPassword()))
        {
            modelMap.put("success", true);
            modelMap.put("authority", nowUser.getAuthority());
            return modelMap;

        }
        else{
            modelMap.put("success", false);
            return modelMap;
        }


    }
}
