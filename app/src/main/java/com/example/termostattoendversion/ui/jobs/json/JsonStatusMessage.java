package com.example.termostattoendversion.ui.jobs.json;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonStatusMessage {

    private String status;

    public JsonStatusMessage(String message) {
        try {
            JSONObject o = new JSONObject(message);
            if (!o.isNull("status")) {
                status = o.getString("status");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
