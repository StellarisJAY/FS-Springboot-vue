package com.jay.fs.controller;

import com.jay.fs.bean.UserBean;
import com.jay.fs.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserDao userDao;


    /**
     * 通过用户名获取用户信息，用户不存在返回null
     * 请求路径： /user/name ? username=
     * @param username
     * @return
     */
    @RequestMapping(value="/user/name", method= RequestMethod.GET)
    public UserBean getUserByName(String username){
        UserBean user = userDao.getUserByName(username);

        return user==null? null : user;
    }

    /**
     * 通过id获取用户信息，不存在返回null
     * 请求路径： /user/id ? user_id
     * @param user_id
     * @return
     */
    @RequestMapping(value="/user/id", method=RequestMethod.GET)
    public UserBean getUserById(int user_id){
        UserBean user = userDao.getUserById(user_id);

        return user;
    }
}
