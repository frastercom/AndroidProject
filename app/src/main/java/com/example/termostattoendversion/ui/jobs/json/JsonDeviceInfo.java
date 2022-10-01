package com.example.termostattoendversion.ui.jobs.json;

public class JsonDeviceInfo {

    //не используется, создавался для хранения данных об устройстве

    private String id;
    private String name;
    private String login;
    private String password;
    private String type;
    private String homeTopic;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHomeTopic() {
        return homeTopic;
    }

    public void setHomeTopic(String homeTopic) {
        this.homeTopic = homeTopic;
    }
}
