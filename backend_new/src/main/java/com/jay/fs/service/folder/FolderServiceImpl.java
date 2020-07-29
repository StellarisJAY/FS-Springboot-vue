package com.jay.fs.service.folder;

import com.jay.fs.bean.FileBean;
import com.jay.fs.bean.FileCreator;
import com.jay.fs.dao.FileDao;
import com.jay.fs.dao.FolderDao;
import com.jay.fs.dao.UserDao;
import com.jay.fs.dao.UserFileDao;
import com.jay.fs.service.file.UserFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

@Service
public class FolderServiceImpl implements FolderService{

    @Autowired
    private FolderDao folderDao;
    @Autowired
    private FileDao fileDao;
    @Autowired
    private UserFileDao userFileDao;
    @Autowired
    private UserFileService userFileService;
    @Autowired
    private UserDao userDao;
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

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Integer deleteFolder(Integer folder_id, Integer user_id) throws RuntimeException{
        int s1, s2;
        //解决方案：递归方式删除，先读取当前文件夹所有内容，然后按照id逐个删除，如果遇到folder类型，递归调用该方法
        List<FileBean> folderContent = userFileDao.getUserFilesAtPath(user_id, folder_id);
        for(FileBean file : folderContent){
            // 如果该文件是folder类型，递归调用当前方法删除内容
            if(file.getType().equals("folder")) {
                logger.info("递归调用：删除文件夹 " + file.getFilename());
                s1 = deleteFolder(file.getFile_id(), user_id);
            }
            else{
                int file_id = file.getFile_id();
                // 删除文件夹内容
                String fileUrl = userFileDao.getFileUrl(file_id);
                int fileSize = userFileDao.getFileSize(file_id);
                int dbStatus = userFileDao.deleteFile(file_id, user_id);

                // 如果删除文件失败，抛出RumtimeException，触发数据库回滚
                if(dbStatus == 0) throw new RuntimeException();

                // db删除成功，继续执行文件系统删除
                boolean fsStatus = false;

                File deletedFile = new File(fileUrl);
                // 从文件系统删除该文件
                if(deletedFile.exists() && dbStatus > 0){
                    fsStatus = deletedFile.delete();
                }
                // 如果文件系统删除失败，抛出RuntimeException，触发回滚
                if(fsStatus == false) throw new RuntimeException();

                // 更改用户空间用量
                int usedSpaceStatus = userDao.usedSpaceDecrease(user_id, fileSize);
                // 如果用户空间用量更改失败，回滚
                if(usedSpaceStatus==0) throw new RuntimeException();
            }
        }
        // 删除文件夹本身的记录
        s2 = userFileDao.deleteFile(folder_id, user_id);
        if(s2 == 0){
            throw new RuntimeException(); // 如果删除文件夹失败，抛出异常，自动回滚
        }
        return 1;
    }
}
