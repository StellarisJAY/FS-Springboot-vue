package com.jay.fs.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.jay.fs.bean.FileBean;
import com.jay.fs.dao.UserDao;
import com.jay.fs.dao.UserFileDao;
import com.jay.fs.service.file.UserFileService;
import com.jay.fs.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class UserFileController {

    @Autowired
    private UserFileDao userFileDao;
    @Autowired
    private UserFileService userFileService;
    @Autowired
    private UserDao userDao;
    // logger
    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 获取用户在当前目录下的所有文件，
     * @param path
     * @param request
     * @return
     */
    @RequestMapping(value="/user_files", method = RequestMethod.GET)
    public List<FileBean> getUserFilesAtPath(int path, HttpServletRequest request){
        String token = request.getHeader("token");
        int user_id = TokenUtil.getUserId(token);

        logger.info("接收到获取文件请求：token=" + token + ";path=" + path);
        // 返回查询结果
        return userFileDao.getUserFilesAtPath(user_id, path);
    }

    @RequestMapping(value="/file/upload", method = RequestMethod.POST)
    public void uploadFile(MultipartFile file, Integer path, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String> map = new HashMap<>();
        String token = request.getHeader("token");
        int user_id = TokenUtil.getUserId(token);
        response.setContentType("application/json;charset=utf-8");

        if(file.isEmpty()){
            map.put("status", "-1");
            map.put("message", "文件为空");
        }
        // 获取文件名、后缀、文件大小
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf('.'));
        // 限定了最大的空间是 500MB ，文件大小不会超过int
        int size = (int)file.getSize();
        logger.info("来自token：" + token + ",上传文件: " + filename + "; 类型： " + suffix);

        // 文件大小检查
        int used_space = userDao.getUsedSpace(user_id);
        int max_space = userDao.getMaxSpace(user_id);
        if(used_space + size > max_space){
            map.put("status", "-2");
            map.put("message", "文件过大，超出网盘限制容量");
        }
        else{
            int status = userFileService.addNewFile(file, user_id, path);
            // 更新用户已用容量
            if(status > 0){
                userDao.setUsedSpace(used_space + size, user_id);
            }
            map.put("status", String.valueOf(status));
            map.put("message", status>0?"文件上传成功":"文件上传失败");
        }

        // 返回json
        response.getWriter().write(JSONUtils.toJSONString(map));
    }
}
