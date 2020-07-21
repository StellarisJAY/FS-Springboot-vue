package com.jay.fs.dao;

import com.jay.fs.bean.FileBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface FileDao {

    @Select("SELECT f.file_id, f.filename, " +
            "f.type, f.size, " +
            "u.user_id AS 'creator.user_id', " +
            "u.username AS 'creator.username', u.email AS 'creator.email', " +
            "u.auth AS 'creator.auth', f.createdate, f.url " +
            "FROM tb_file AS f, tb_user AS u " +
            "WHERE f.creator=u.user_id AND " +
            "f.file_id=#{file_id}")
    public FileBean getFileById(int file_id);
}
