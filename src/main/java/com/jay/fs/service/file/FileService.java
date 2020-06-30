package com.jay.fs.service.file;

import com.jay.fs.bean.FileBean;

import java.util.Collection;

public interface FileService {

    Collection<FileBean> getUserFiles(int user_id, int parent);

    int getUpperPath(int currentPath, int user_id);

    FileBean getUserFileByNameAtPath(int user_id, int parent, String filename);

    FileBean getUserFolderByNameAtPath(int user_id, int parent, String foldername);
}
