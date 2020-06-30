package com.jay.fs.mapper;

import com.jay.fs.bean.FileBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Mapper
@Repository
public interface FileMapper {

    @Select("SELECT f.file_id, f.filename, f.type, f.size, " +
            "u.user_id as 'creator.user_id', u.username as 'creator.username', " +
            "u.email as 'creator.email', u.auth as 'creator.auth', " +
            "f.createdate, f.url " +
            "FROM tb_file as f, tb_user_file as uf, tb_user as u " +
            "WHERE f.file_id=uf.file_id AND " +
            "      u.user_id=f.creator AND " +
            "      uf.parent=#{parent} AND " +
            "      uf.user_id=#{user_id}")
    Collection<FileBean> getUserFiles(@Param("user_id")int user_id, @Param("parent")int parent);

    @Select("SELECT uf.parent " +
            "FROM tb_file as f, tb_user_file as uf " +
            "WHERE f.file_id=uf.file_id " +
            "AND uf.user_id=#{user_id} " +
            "AND f.file_id=#{currentPath}")
    int getUpperPath(@Param("currentPath") int currentPath, @Param("user_id") int user_id);

    @Select("SELECT f.file_id, f.filename, f.type, f.size, " +
            "u.user_id as 'creator.user_id', u.username as 'creator.username', " +
            "u.email as 'creator.email', u.auth as 'creator.auth', " +
            "f.createdate, f.url " +
            "FROM tb_file as f, tb_user_file as uf, tb_user as u " +
            "WHERE f.file_id=uf.file_id AND " +
            "      u.user_id=f.creator AND " +
            "      uf.parent=#{parent} AND " +
            "      uf.user_id=#{user_id} AND " +
            "      f.filename=#{filename}")
    FileBean getUserFileAtPath(@Param("user_id")int user_id, @Param("parent")int parent, @Param("filename")String filename);

    @Select("SELECT f.file_id, f.filename, f.type, f.size, " +
            "u.user_id as 'creator.user_id', u.username as 'creator.username', " +
            "u.email as 'creator.email', u.auth as 'creator.auth', " +
            "f.createdate, f.url " +
            "FROM tb_file as f, tb_user_file as uf, tb_user as u " +
            "WHERE f.file_id=uf.file_id AND " +
            "      u.user_id=f.creator AND " +
            "      uf.parent=#{parent} AND " +
            "      uf.user_id=#{user_id} AND " +
            "      f.filename=#{foldername} AND " +
            "      f.type='folder'")
    FileBean getFolderAtPath(@Param("user_id")int user_id, @Param("parent")int parent, @Param("foldername")String foldername);

    int addFile(FileBean fileBean);
}
