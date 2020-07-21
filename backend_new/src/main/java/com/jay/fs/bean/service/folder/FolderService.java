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
