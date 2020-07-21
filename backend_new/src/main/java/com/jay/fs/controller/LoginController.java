package com.jay.fs.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jay.fs.bean.UserBean;
import com.jay.fs.dao.UserDao;
import com.jay.fs.util.TokenUtil;
import jdk.nashorn.internal.parser.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    private UserDao userDao;

    @RequestMapping(value="/login", method= RequestMethod.POST)
    @ResponseBody
    public void login(String username, String password, HttpServletResponse response) throws IOException {
        System.out.println("接收登录请求：username=" + username + "password=" + password);
        response.setContentType("application/json;charset=utf-8");
        // 从数据库获取 user信息
        UserBean user = userDao.getUserByName(username);

        Map<String, String> map = new HashMap<>();
        if(user == null){
            map.put("status", "-1");
            map.put("message", "用户不存在");
        }
        else if(!user.getPassword().equals(password)){
            // 密码错误，json返回message
            map.put("status", "0");
            map.put("message", "密码错误");
        }
        else{
            // 记录用户登陆状态，写入 token
            String token = TokenUtil.getToken(username, password);
            TokenUtil.putToken(token, user.getUser_id());
            map.put("status", "1");
            map.put("token", token);
        }

        response.getWriter().write(JSONUtils.toJSONString(map));
    }
}
