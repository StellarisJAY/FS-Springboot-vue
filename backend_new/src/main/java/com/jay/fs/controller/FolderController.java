package com.jay.fs.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.jay.fs.common.CommonResult;
import com.jay.fs.service.folder.FolderService;
import com.jay.fs.dao.FolderDao;
import com.jay.fs.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class FolderController {
    @Autowired
    private FolderService folderService;
    @Autowired
    private FolderDao folderDao;
    // logger
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 处理创建文件夹请求
     * @param foldername
     * @param path
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value="/folder", method = RequestMethod.POST)
    public CommonResult createFolder(String foldername, Integer path, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getHeader("token");
        // logger 记录发送请求的token
        logger.info("接收到创建文件夹请求：token=" + token);

        // 用token获取userid
        int user_id = TokenUtil.getUserId(token);
        // 业务层逻辑
        try{
            int status = folderService.createFolder(foldername, path, user_id);
        }
        catch(RuntimeException e){
            return CommonResult.fail("创建文件夹失败");
        }

        return CommonResult.success("创建文件夹成功");
    }

    /**
     * 文件夹重名检查处理
     * @param name
     * @param path
     * @param request
     * @return
     */
    @RequestMapping(value = "/folder/check/name", method = RequestMethod.GET)
    public CommonResult checkFoldername(String name, Integer path, HttpServletRequest request){
        String token = request.getHeader("token");
        int user_id = TokenUtil.getUserId(token);
        logger.info("接收到文件夹重名检查请求：token=" + token);

        return folderDao.checkName(name, user_id, path) > 0 ? CommonResult.success("文件夹重名").addDataItem("duplicate", true) :
                CommonResult.success("文件夹没有重名").addDataItem("duplicate", false);
    }

    /**
     * 删除文件夹接口, ./folder DELETE
     * @param folder_id
     * @param request
     * @return
     */
    @RequestMapping(value="/folder", method=RequestMethod.DELETE)
    public CommonResult deleteFolder(Integer folder_id, HttpServletRequest request){
        String token = request.getHeader("token");
        int user_id = TokenUtil.getUserId(token);

        logger.info("接收到删除文件夹请求：folder_id=" + folder_id + ";token=" + token);
        try{
            int status = folderService.deleteFolder(folder_id, user_id);
        }
        catch(RuntimeException e){
            return CommonResult.fail("删除文件夹失败");
        }

        return CommonResult.success("删除文件夹成功");
    }

}
