package com.jay.fs.controller;

import com.jay.fs.bean.FileBean;
import com.jay.fs.common.CommonResult;
import com.jay.fs.dao.UserFileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class TestApi {

    @Autowired
    private UserFileDao userFileDao;

    @GetMapping(value="/api/test")
    public CommonResult test(int user_id, int path){
        return CommonResult.success("test");
    }
}
