package com.jay.fs.controller;

import com.jay.fs.bean.FileBean;
import com.jay.fs.dao.FileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class FileController {
    @Autowired
    private FileDao fileDao;

    @RequestMapping(value = "/file/id", method = RequestMethod.GET)
    public FileBean getFileById(int file_id){
        return fileDao.getFileById(file_id);
    }
}
