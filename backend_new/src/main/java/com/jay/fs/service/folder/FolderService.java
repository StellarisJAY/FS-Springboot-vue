package com.jay.fs.service.folder;

public interface FolderService {
    /**
     * 创建文件夹
     * @param foldername
     * @param path
     * @param user_id
     * @return
     */
    public Integer createFolder(String foldername, Integer path, Integer user_id);

    /**
     * 删除文件夹
     * @param folder_id
     * @param user_id
     * @return
     */
    public Integer deleteFolder(Integer folder_id, Integer user_id);
}
