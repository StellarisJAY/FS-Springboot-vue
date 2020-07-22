// 该文件上传路径错误，请转到 /com/jay/fs/service
package com.jay.fs.bean.service.folder;

public interface FolderService {
    /**
     * 创建文件夹
     * @param foldername
     * @param path
     * @param user_id
     * @return
     */
    public Integer createFolder(String foldername, Integer path, Integer user_id);
}
