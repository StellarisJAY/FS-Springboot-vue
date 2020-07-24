package com.jay.fs.service.file;

import com.jay.fs.bean.FileBean;
import org.springframework.web.multipart.MultipartFile;

public interface UserFileService {


    public Integer addNewFile(MultipartFile file, int user_id, int path);

    public FileBean getFileById(int file_id, int user_id);
}
