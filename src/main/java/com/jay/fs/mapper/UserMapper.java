package com.jay.fs.mapper;

import com.jay.fs.bean.UserBean;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    @Select("SELECT * FROM tb_user WHERE username=#{username}")
    UserBean getUserByName(String username);

    /**
     * 新用户注册后添加，user除了用户名、密码和email其他都是service层中生成的
     * @param user
     * @return
     */
    @Insert("INSERT tb_user VALUES(null, " +
            "#{username}, " +
            "#{password}, " +
            "#{max_space}, " +
            "#{used_space}, " +
            "#{email}, " +
            "#{status})")
    int addUser(UserBean user);

    /**
     * 更新用户信息，只允许更改用户名和email
     * @param user
     * @return
     */
    @Update("UPDATE tb_user SET " +
            "username=#{username}, " +
            "email=#{email} " +
            "WHERE user_id=#{user_id}")
    int updateUserInfo(UserBean user);


    /**
     * 更改密码，password为service层中加密的密码
     * @param user_id
     * @param password
     * @return
     */
    @Update("UPDATE tb_user SET " +
            "password=#{password} " +
            "WHERE user_id=#{user_id}")
    int changePassword(@Param("user_id") String user_id, @Param("password")String password);
}
