package com.jay.fs.service.file;

import com.jay.fs.bean.FileBean;
import com.jay.fs.mapper.FileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class FileServiceImpl implements FileService{

    @Autowired
    private FileMapper fileMapper;  // 数据库访问对象

    // 业务逻辑层的日志logger
    Logger logger = LoggerFactory.getLogger(getClass());

    // 获取当前用户在当前文件夹下的所有文件
    @Override
    public Collection<FileBean> getUserFiles(int user_id, int parent) {
        Collection<FileBean> files = fileMapper.getUserFiles(user_id, parent);
        logger.info("file业务层------获取所有文件结果：" + files);

        return files;
    }

    @Override
    public int getUpperPath(int currentPath, int user_id) {
        return fileMapper.getUpperPath(currentPath,user_id);
    }

    @Override
    public FileBean getUserFileByNameAtPath(int user_id, int parent, String filename) {
        return fileMapper.getUserFileAtPath(user_id, parent, filename);
    }

    @Override
    public FileBean getUserFolderByNameAtPath(int user_id, int parent, String foldername) {
        return fileMapper.getFolderAtPath(user_id, parent, foldername);
    }
}
