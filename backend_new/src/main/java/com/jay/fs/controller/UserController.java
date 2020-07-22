package com.jay.fs.controller;

import com.jay.fs.bean.UserBean;
import com.jay.fs.dao.UserDao;
import com.jay.fs.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserDao userDao;

    private final Logger logger = LoggerFactory.getLogger(getClass());

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

    @RequestMapping(value="/user/used_space", method = RequestMethod.GET)
    public Integer getUsedSpace(HttpServletRequest request){
        String token = request.getHeader("token");
        logger.info("接收到获取已使用空间请求：token=" + token);
        return userDao.getUsedSpace(TokenUtil.getUserId(token));
    }

    @RequestMapping(value="/user/max_space", method=RequestMethod.GET)
    public Integer getMaxSpace(HttpServletRequest request){
        String token = request.getHeader("token");
        logger.info("接收到获取总容量请求：token=" + token);
        return userDao.getMaxSpace(TokenUtil.getUserId(token));
    }
}
