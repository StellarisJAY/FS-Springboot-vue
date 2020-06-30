package com.jay.fs.service.user;

import com.jay.fs.bean.UserBean;

import java.util.Collection;

public interface UserService {

    Collection<UserBean> getAllUsers();

    UserBean getUserByName(String username);

    int addUser(UserBean user);

    int updateUser(UserBean user);
}
