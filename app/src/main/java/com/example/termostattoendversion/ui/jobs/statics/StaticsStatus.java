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
        Log.d("STATUS", String.format("Добавляем ссылку на компонент со статусом. Ключ: %s", key.concat("/status")));
        statusMap.put(key.concat("/status"), status);
    }

    public static void clear() {
        Log.d("STATUS", "Чистка статусов");
        statusMap.clear();
    }

    public static void setStatus(String key, JsonStatusMessage status) {
        Log.d("STATUS", String.format("Присвоение статуса () по ключу ()", status.getStatus(), key));
        if (statusMap.get(key) != null) {
            statusMap.get(key).setStatus(status);
        }
        widgetAdapter.getComponent().stream().filter(o -> key.contains(o.getTopic().concat("/status"))).forEach(o -> o.setStatus(status));
    }
}
