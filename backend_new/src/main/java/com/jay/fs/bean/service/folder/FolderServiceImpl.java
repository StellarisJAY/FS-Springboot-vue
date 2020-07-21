package com.jay.fs.bean.service.folder;

import com.jay.fs.bean.FileBean;
import com.jay.fs.bean.FileCreator;
import com.jay.fs.dao.FolderDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FolderServiceImpl implements FolderService{

    @Autowired
    private FolderDao folderDao;

    // logger
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Integer createFolder(String foldername, Integer path, Integer user_id) {
        FileBean folder = new FileBean();
        FileCreator creator = new FileCreator();
        creator.setUser_id(user_id);

        // 初始化属性值
        folder.setFilename(foldername);
        folder.setCreatedate(LocalDate.now().toString());
        folder.setCreator(creator);
        folder.setType("folder");
        folder.setUrl("/");
        folder.setSize(0);

        // 生成新文件id
        int id = folderDao.getNextId() + 1;
        folder.setFile_id(id);
        // 数据库创建新记录
        int status = folderDao.addFolder(folder, path);
        // logger记录
        logger.info("创建文件夹：name=" + foldername + " ; path=" + path + ";user_id=" + user_id);
        logger.info("创建文件夹状态码：status: " + status);
        return status;
    }
}
