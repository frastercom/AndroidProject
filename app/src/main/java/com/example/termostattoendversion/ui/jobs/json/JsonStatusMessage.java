package com.example.termostattoendversion.ui.jobs.json;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonStatusMessage {

    private Object status;

    public JsonStatusMessage(String message) {
        try {
            JSONObject o = new JSONObject(message);
            if (!o.isNull("status")) {
                status = o.get("status");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }
}
