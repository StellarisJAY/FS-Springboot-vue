package com.jay.fs.dao;

import com.jay.fs.bean.FileBean;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserFileDao {

    @Select("SELECT f.file_id, f.filename, " +
            "f.type, f.size, " +
            "u.user_id AS 'creator.user_id', u.username AS 'creator.username', " +
            "u.email AS 'creator.email', u.auth AS 'creator.auth', " +
            "f.createdate, f.url " +
            "FROM tb_file AS f, tb_user_file AS uf, tb_user AS u " +
            "WHERE f.file_id=uf.file_id AND " +
            "uf.user_id=#{user_id} AND " +
            "uf.parent=#{path} AND " +
            "u.user_id=f.creator")
    public List<FileBean> getUserFilesAtPath(@Param("user_id") int user_id, @Param("path")int path);

    @Delete("DELETE FROM tb_user_file WHERE file_id=#{file_id} AND user_id=#{user_id};" +
            "" +
            "DELETE FROM tb_file WHERE file_id=#{file_id};")
    public Integer deleteFile(Integer file_id, Integer user_id);


    @Insert("INSERT tb_file VALUES(#{file.file_id}, #{file.filename}, " +
            "#{file.type}, #{file.size}, #{file.creator.user_id}, #{file.createdate}, #{file.url});" +
            "" +
            "INSERT tb_user_file VALUES(#{user_id}, #{file.file_id}, #{path});")
    public Integer addFile(@Param("file") FileBean fileBean, @Param("user_id") Integer user_id, @Param("path") Integer path);

    @Select("SELECT f.file_id, f.filename, " +
            "f.type, f.size, " +
            "u.user_id AS 'creator.user_id', " +
            "u.username AS 'creator.username', u.email AS 'creator.email', " +
            "u.auth AS 'creator.auth', f.createdate, f.url " +
            "FROM tb_file AS f, tb_user AS u, tb_user_file AS uf " +
            "WHERE f.creator=u.user_id AND " +
            "f.file_id=#{file_id} AND " +
            "uf.user_id=#{user_id} AND " +
            "uf.file_id=f.file_id")
    public FileBean getFileById(int file_id, int user_id);

    @Select("SELECT MAX(file_id) FROM tb_file;")
    public Integer getMaxId();
}
