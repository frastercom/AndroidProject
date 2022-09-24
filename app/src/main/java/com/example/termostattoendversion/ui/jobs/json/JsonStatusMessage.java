package com.example.termostattoendversion.ui.jobs.json;

public class JsonStatusMessage {

    private String status;

    public JsonStatusMessage() {
    }

    public JsonStatusMessage(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
