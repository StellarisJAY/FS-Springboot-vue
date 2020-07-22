package com.jay.fs.dao;

import com.jay.fs.bean.UserBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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

    @Select("SELECT used_space FROM tb_user WHERE user_id=#{user_id}")
    public Integer getUsedSpace(Integer user_id);

    @Select("SELECT max_space FROM tb_user WHERE user_id=#{user_id}")
    public Integer getMaxSpace(Integer user_id);

    @Select("SELECT username FROM tb_user WHERE user_id=#{user_id}")
    public String getUsername(Integer user_id);

    @Update("UPDATE tb_user SET used_space=#{used_space} WHERE user_id=#{user_id}")
    public Integer setUsedSpace(@Param("used_space") Integer used_space, @Param("user_id") Integer user_id);
}
