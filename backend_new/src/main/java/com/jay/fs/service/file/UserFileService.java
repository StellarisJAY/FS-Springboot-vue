package com.jay.fs.service.file;

import org.springframework.web.multipart.MultipartFile;

public interface UserFileService {


    public Integer addNewFile(MultipartFile file, int user_id, int path);
}
