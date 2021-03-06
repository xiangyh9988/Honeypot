package com.honeypot.honeypot.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.honeypot.honeypot.dao.UserDao;
import com.honeypot.honeypot.entity.Department;
import com.honeypot.honeypot.entity.User;
import com.honeypot.honeypot.entity.UserCriteria;
import com.honeypot.honeypot.service.DepartmentManagementService;
import com.honeypot.honeypot.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private DepartmentManagementService departmentManagementService;

    /**
     * 获取所有用户信息
     * 调用UserDao中的方法获得List<User>
     * 需要将user表中取得的department和authority由数字转换成对应的文字
     * 由UserCriteria完成 （名字按信工所代码取的）
     *
     * @return JSONArray
     * 根据前端要求，返回如下形式的JSONArray
     *       [{"id": xx, "username": xx, "realName": xx, "role": xx, "department": xx},
     *       ......,
     *       {"id": xx, "username": xx, "realName": xx, "role": xx, "department": xx}]
     */
    @Override
    public JSONArray getAllUsers() {
        List<User> users = userDao.getAllUsers();
        JSONArray array = new JSONArray();
        Map<Integer, String> roleMap = new HashMap<Integer, String>();
        roleMap.put(1, "系统管理员");
        roleMap.put(2, "安全审计管理员");
        roleMap.put(3, "普通用户");
        roleMap.put(4, "安全保密管理员");

        for (User user : users){
            JSONObject one = new JSONObject();
            one.put("username", user.getUsername());
            one.put("realName", user.getRealName());
            one.put("role", roleMap.get(user.getAuthority()));
            one.put("department", departmentManagementService.getDeptById(user.getDept()).getDepName());
            array.add(one);
        }
        return array;
    }

    /**
     * 接下来两个getUser的方法会在其他方法中用到
     * 也可以作为前端“查询”按钮对应的功能，视情况扩展
     */

    /**
     * 根据用户名获取单个用户信息，直接调用userDao中方法即可
     * @param username
     * @return User
     */
    @Override
    public User getUserByUsername(String username){
        return userDao.getUserByUsername(username);
    }

    /**
     * 根据id获取单个用户信息，直接调用userDao中方法即可
     * @param id
     * @return User
     */
    @Override
    public User getUserById(int id){
        return userDao.getUserById(id);
    }

    /**
     * 添加一个新用户，将json解析赋给一个User对象，再调用userDao中的方法添加
     * @param userJson 前端传来的json数据
     * @return result 一个json数据，用于显示添加是否成功
     */
    @Override
    public JSONObject addUser(JSONObject userJson){
        User newUser = new User();
        JSONObject result = new JSONObject();
        String passwordMD5 = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64Encoder = new BASE64Encoder();
            passwordMD5 = base64Encoder.encode(md5.digest(userJson.getString("password").getBytes("UTF-8")));
        } catch (Exception e)
        {
            System.out.println(e);
        }
        newUser.setId(userJson.getIntValue("id"));
        newUser.setUsername(userJson.getString("username"));
        newUser.setPassword(passwordMD5);
        newUser.setRealName(userJson.getString("realName"));
        newUser.setAuthority(userJson.getIntValue("authority"));
        newUser.setDept(userJson.getIntValue("dept"));

        if(userDao.addUser(newUser))
            result.put("result", "添加用户成功！");
        else
            result.put("result", "添加用户失败！");

        return result;
    }

    /**
     * 删除用户，前端勾选的用户个数不同，故按照JSONArray处理
     * 遍历array，对每一个JSONObject进行删除
     * 若其中有某个用户删除失败，则返回其ID并停止删除
     * 否则删除成功，返回"success"供前端处理
     * @param delArray
     * 前端传来jsonArray形式为
     * [{"id": 1}, {"id": 2}, ..., {"id": n}]
     * @return
     */
    @Override
    public JSONObject delUser(JSONArray delArray){
        JSONObject result = new JSONObject();
        for(int i = 0; i < delArray.size(); i++){
            JSONObject obj = delArray.getJSONObject(i);
            if(!userDao.delUser(obj.getString("username"))){
                result.put("result", obj.getString("username"));
                return result;
            }
        }
        result.put("result", "success");
        return result;
    }

    /**
     * 修改用户信息
     * @param updateJson
     * 前端传来json形式为
     * {
     *     "username": x,
     *     "oldpassword": xxxxx,
     *     "newpassword": xxxxxx,
     *     "newrealname": xxxx,
     *     "newdept": xx
     * }
     * 由id获得需要修改的用户对象，根据不同键对应的值是否为空来确定是否对该对象的对应属性进行修改
     * 至少有一项会被修改，故最后调用userDao的updateUser方法进行更新即可
     * @return result
     * 返回json供前端显示结果
     */
    @Override
    public JSONObject updateUser(JSONObject updateJson){
        JSONObject result = new JSONObject();
        User update = new User();
        update = getUserByUsername(updateJson.getString("username"));
        if(!updateJson.getString("newpassword").equals(""))
        {
            String newpasswordMD5 = new String();
            try{
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                BASE64Encoder base64Encoder = new BASE64Encoder();
                newpasswordMD5 = base64Encoder.encode(md5.digest(updateJson.getString("newpassword").getBytes("UTF-8")));
            } catch(Exception e){
                System.out.print(e);
            }
            update.setPassword(newpasswordMD5);
        }
        if(!updateJson.getString("newrealname").equals(""))
            update.setRealName(updateJson.getString("newrealname"));
        if(!updateJson.getString("newdept").equals(""))
            update.setDept(updateJson.getIntValue("newdept"));

        if(userDao.updateUser(update))
            result.put("result", "修改用户成功！");
        else
            result.put("result", "修改用户失败！");

        return result;
    }
}
