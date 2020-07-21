package com.jay.fs.bean;

import java.util.ArrayList;
import java.util.List;

public class UserFileBean {
    private int user_id;
    private String username;
    private List<FileBean> user_files;

    public UserFileBean() {
    }

    public UserFileBean(int user_id, String username, List<FileBean> user_files) {
        this.user_id = user_id;
        this.username = username;
        this.user_files = user_files;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<FileBean> getUser_files() {
        return user_files;
    }

    public void setUser_files(List<FileBean> user_files) {
        this.user_files = user_files;
    }
}
