package com.jay.fs.service.user;

import com.jay.fs.bean.UserBean;
import com.jay.fs.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public Collection<UserBean> getAllUsers() {
        return null;
    }

    @Override
    public UserBean getUserByName(String username) {
        return userMapper.getUserByName(username);
    }

    @Override
    public int addUser(UserBean user) {
        return 0;
    }

    @Override
    public int updateUser(UserBean user) {
        return 0;
    }
}
