package com.jay.fs.service.file;

import com.jay.fs.bean.FileBean;
import com.jay.fs.bean.FileCreator;
import com.jay.fs.dao.FileDao;
import com.jay.fs.dao.UserDao;
import com.jay.fs.dao.UserFileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

@Service
public class UserFileServiceImpl implements UserFileService{

    @Autowired
    private UserFileDao userFileDao;
    @Autowired
    private FileDao fileDao;
    @Autowired
    private UserDao userDao;

    @Override
    public Integer addNewFile(MultipartFile file, int user_id, int path) {
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf('.'));
        int size = (int)file.getSize();

        // 设置存储地址
        String username = userDao.getUsername(user_id);
        String storagePath = "D:\\fs_storage\\" + username + "\\" + filename;

        // 在该地址创建文件对象
        File dest = new File(storagePath);
        try {
            // 将文件写入路径中
            file.transferTo(dest);

            // 写入数据库
            FileBean fileBean = new FileBean();
            FileCreator creator = new FileCreator();
            creator.setUser_id(user_id);

            int file_id = userFileDao.getMaxId() + 1;
            fileBean.setFile_id(file_id);
            fileBean.setFilename(filename);
            fileBean.setType(suffix);
            fileBean.setSize(size);
            fileBean.setCreator(creator);
            fileBean.setCreatedate(LocalDate.now().toString());
            fileBean.setUrl("http://182.92.116.152:8080/fs_storage/" + username + "/" + filename);

            return userFileDao.addFile(fileBean, user_id, path);
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
