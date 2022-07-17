package com.example.termostattoendversion.ui.jobs.statics;

import android.util.Log;

import com.example.termostattoendversion.ui.jobs.json.JsonStatusMessage;
import com.example.termostattoendversion.ui.view.adapters.WidgetAdapter;

import java.util.HashMap;
import java.util.Map;

public class StaticsStatus {

    private static Map<String, ISetStatus> statusMap = new HashMap<>();
    private static WidgetAdapter widgetAdapter;

    public static void setWidgetAdapter(WidgetAdapter adapter) {
        widgetAdapter = adapter;
    }

    public static void add(String key, ISetStatus status) {
        Log.d("STATUS", "Status key ------->>> " + key.concat("/status"));
        statusMap.put(key.concat("/status"), status);
    }

    public static void clear() {
        statusMap.clear();
    }

    public static void setStatus(String key, JsonStatusMessage status) {
        Log.d("STATUS", "Status ------->>> " + status.getStatus());
        if (statusMap.get(key) != null) {
            statusMap.get(key).setStatus(status);
            return;
        }
        widgetAdapter.getComponent().stream().filter(o -> key.contains(o.getTopic())).forEach(o -> o.setStatus(status));
    }
}
