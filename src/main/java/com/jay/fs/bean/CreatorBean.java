package com.jay.fs.bean;

public class CreatorBean {
    private int user_id;
    private String username;
    private String email;
    private int auth;

    public CreatorBean() {
        super();
    }

    public CreatorBean(int user_id, String username, String email, int auth) {
        this.user_id = user_id;
        this.username = username;
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
        return "CreatorBean{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", auth=" + auth +
                '}';
    }
}
