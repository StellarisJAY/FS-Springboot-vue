package com.jay.fs.dao;

import com.jay.fs.bean.UserBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserDao {

    @Select("SELECT * " +
            "FROM tb_user " +
            "WHERE " +
            "username=#{username}")
    public UserBean getUserByName(String username);

    @Select("SELECT * " +
            "FROM tb_user " +
            "WHERE " +
            "user_id=#{user_id}")
    public UserBean getUserById(int user_id);
}
