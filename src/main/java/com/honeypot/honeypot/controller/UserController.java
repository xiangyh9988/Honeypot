/**
 * 处理配置管理-用户管理页面的请求
 */
package com.honeypot.honeypot.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.honeypot.honeypot.entity.User;
import com.honeypot.honeypot.entity.UserCriteria;
import com.honeypot.honeypot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/honeycontrol")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getAllUsers")
    public JSONArray getAllUsers(){
        return userService.getAllUsers() ;
    }

    /**
     * 接收前端添加新用户的请求
     * 先判断新用户的用户名（和id）是否被占用  （关于id，目前前端用随机数赋值，最终处理方法需要修改）
     * 之后若为被占用则调用userService的addUser方法添加用户
     * @param userJson 前端传来的json数据，包括一个User的各项信息，形式如下
     *        {
     *            "id": x,
     *            "username": xx,
     *            "password": xx,
     *            "realName": xx,
     *            "authority": xx,
     *            "dept": xx
     *        }
     *        其中，"authority"和"dept"为相应角色和部门对应的数值
     * @return result 返回一个json对象，表示状态。
     *         若已有该用户，则提示被占用；若无，则返回userService的addUser方法返回添加结果
     */
    @ResponseBody
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public JSONObject addUser(@RequestBody JSONObject userJson){
        JSONObject result = new JSONObject();
        boolean usernameOccupied = false, idOccupied = false;
        // 若用户名被占用
        if(userService.getUserByUsername(userJson.getString("username")) != null)
            usernameOccupied = true;
        // 若id被占用  （此处应该不是这么处理的，存疑）
        if(userService.getUserById(userJson.getIntValue("id")) != null)
            idOccupied = true;
        if(usernameOccupied && idOccupied)
        {
            result.put("result", "用户名和id都已经被使用");
            return result;
        }
        if(usernameOccupied && !idOccupied)
        {
            result.put("result", "用户名已经被使用");
            return result;
        }
        if(!usernameOccupied && idOccupied)
        {
            result.put("result", "id已经被使用");
            return result;
        }

        return userService.addUser(userJson);
    }

    /**
     * 删除用户，前端可能勾选多个，故按JSONArray处理
     * @param delArray
     * [{"id": 1}, {"id": 2}, ..., {"id": x}]
     * @return result
     * eg. {"result": "success"}
     */
    @ResponseBody
    @RequestMapping(value = "/delUser", method = RequestMethod.POST)
    public JSONObject delUser(@RequestBody JSONArray delArray){
        return userService.delUser(delArray);
    }

    /**
     * 修改用户信息，首先由"id"获得要修改的用户对象
     * 然后核对输入的原始密码的md5与数据库中的md5是否一致，一致即可修改
     * @param updateJson
     * {
     *   "id": x,
     *   "oldpassword": xxxxx,
     *   "newpassword": xxxxxx,
     *   "newrealname": xxxx,
     *   "newdept": xx
     * }
     * @return result
     */
    @ResponseBody
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public JSONObject updateUser(@RequestBody JSONObject updateJson){
        JSONObject result = new JSONObject();
        String passwordMD5 = null;
        // 获得前端传来的原始密码的md5
        try{
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64Encoder = new BASE64Encoder();
            passwordMD5 = base64Encoder.encode(md5.digest(updateJson.getString("oldpassword").getBytes("UTF-8")));
        } catch(Exception e){
            System.out.print(e);
        }
        // 与数据库中相应用户对象的密码md5对比
        if(!passwordMD5.equals(userService.getUserByUsername(updateJson.getString("username")).getPassword()))
        {
            result.put("result", "原密码错误，不能修改！");
            return result;
        }

        return userService.updateUser(updateJson);
    }
}
