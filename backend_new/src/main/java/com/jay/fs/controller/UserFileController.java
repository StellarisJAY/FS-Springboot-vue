package com.jay.fs.controller;

import com.jay.fs.bean.FileBean;
import com.jay.fs.dao.UserFileDao;
import com.jay.fs.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin
public class UserFileController {

    @Autowired
    private UserFileDao userFileDao;

    @RequestMapping(value="/user_files", method = RequestMethod.GET)
    public List<FileBean> getUserFilesAtPath(int path, HttpServletRequest request){
        int user_id = TokenUtil.getUserId(request.getHeader("token"));

        return userFileDao.getUserFilesAtPath(user_id, path);
    }
}
