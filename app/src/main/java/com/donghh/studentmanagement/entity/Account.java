package com.donghh.studentmanagement.entity;

public class Account {
    private int id; //
    private String userName;
    private String password;//mật khẩu
    private int isManager;//1 là quản lí

    public Account(int id, String userName, String password, int isManager) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.isManager = isManager;
    }

    public Account(int id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    public Account(String userName, String password, int isManager) {
        this.userName = userName;
        this.password = password;
        this.isManager = isManager;
    }

    public int getIsManager() {
        return isManager;
    }

    public void setIsManager(int isManager) {
        this.isManager = isManager;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
