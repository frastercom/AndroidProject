package com.example.termostattoendversion.ui.jobs.device;

public class Device {

    private String userName;
    private String password;
    private String serial;

    public Device() {
    }

    public Device(String userName, String password) {
        this(userName, password, null);
    }

    public Device(String userName, String password, String serial) {
        this.userName = userName;
        this.password = password;
        this.serial = serial;
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

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
}
