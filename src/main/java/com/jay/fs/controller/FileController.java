package com.jay.fs.controller;

import com.jay.fs.bean.FileBean;
import com.jay.fs.service.file.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@CrossOrigin
public class FileController {

    @Autowired
    private FileService fileService;
    // 控制层日志logger
    private Logger logger = LoggerFactory.getLogger(getClass());

    // 处理获取所有文件请求
    @GetMapping("/user_files")
    public Collection<FileBean> getUserFiles(int user_id, int parent){
        logger.info("控制层截取请求：获取用户：user_id=" + user_id + "," + parent + "文件夹下所有文件");
        return fileService.getUserFiles(user_id, parent);
    }

    // 获取当前路径的上级目录
    @GetMapping("/upperpath")
    public int getUpperPath(int currentPath, int user_id){
        return fileService.getUpperPath(currentPath, user_id);
    }

    @GetMapping("/filename_path")
    public FileBean getUserFileByNameAtPath(int user_id, int parent, String filename){
        FileBean result = fileService.getUserFileByNameAtPath(user_id, parent, filename);

        logger.info("获取用户user_id=" + user_id + "  在目录： " + parent + " ， 文件名：" + filename);
        logger.info("获取结果：" + result);
        return result;
    }

    @GetMapping("/foldername_path")
    public FileBean getUserFolderByNameAtPath(int user_id, int parent, String foldername){
        FileBean result = fileService.getUserFolderByNameAtPath(user_id, parent, foldername);

        logger.info("获取用户user_id=" + user_id + "  在目录： " + parent + " ， 文件夹名：" + foldername);
        logger.info("获取结果：" + result);
        return result;
    }
}
