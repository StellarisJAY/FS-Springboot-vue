package com.jay.fs.bean;

public class UserBean {
    private int user_id;
    private String username;
    private String password;
    private int max_space;
    private int used_space;
    private String email;
    private int auth;

    public UserBean() {
    }

    public UserBean(int user_id, String username, String password, int max_space, int used_space, String email, int auth) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.max_space = max_space;
        this.used_space = used_space;
        this.email = email;
        this.auth = auth;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMax_space() {
        return max_space;
    }

    public void setMax_space(int max_space) {
        this.max_space = max_space;
    }

    public int getUsed_space() {
        return used_space;
    }

    public void setUsed_space(int used_space) {
        this.used_space = used_space;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAuth() {
        return auth;
    }

    public void setAuth(int auth) {
        this.auth = auth;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", max_space=" + max_space +
                ", used_space=" + used_space +
                ", email='" + email + '\'' +
                ", auth=" + auth +
                '}';
    }
}
