package com.example.termostattoendversion.ui.jobs.device;

public class Device {

    private String userName;
    private String password;

    public Device() {
    }

    public Device(String userName, String password) {
        this.userName = userName;
        this.password = password;
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
