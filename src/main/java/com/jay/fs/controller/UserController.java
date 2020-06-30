package com.jay.fs.controller;

import com.jay.fs.bean.UserBean;
import com.jay.fs.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;       // 用户的数据库访问层

    Logger logger = LoggerFactory.getLogger(getClass()); // 日志logger

    @GetMapping("/username")  // 通过用户名获取单个用户的请求处理
    public UserBean getUserByName(String username){
        UserBean user = userMapper.getUserByName(username);
        logger.info("通过用户名：" + username + "查找用户， 结果：" + user);

        return user;
    }

}
