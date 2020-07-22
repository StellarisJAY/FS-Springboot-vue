package com.jay.fs.dao;

import com.jay.fs.bean.FileBean;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface FolderDao {

    @Select("SELECT MAX(file_id) FROM tb_file")
    public Integer getNextId();

    @Insert("INSERT tb_file VALUES(#{folder.file_id}, " +
            "#{folder.filename}, #{folder.type}, #{folder.size}," +
            "#{folder.creator.user_id}, #{folder.createdate}, #{folder.url});" +
            "" +
            "INSERT tb_user_file VALUES(#{folder.creator.user_id}, " +
            "#{folder.file_id}, #{path})")
    public Integer addFolder(FileBean folder, @Param("path") Integer path);

    @Select("SELECT COUNT(f.file_id) " +
            "FROM tb_user_file AS uf, tb_file AS f " +
            "WHERE " +
            "uf.file_id=f.file_id AND " +
            "uf.user_id=#{user_id} AND " +
            "f.filename=#{name} AND " +
            "uf.parent=#{path}")
    public Integer checkName(String name, Integer user_id, Integer path);


}
