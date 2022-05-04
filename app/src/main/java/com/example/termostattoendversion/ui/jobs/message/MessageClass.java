package com.example.termostattoendversion.ui.jobs.message;

import com.example.termostattoendversion.ui.view.widgets.IWidgetSetStatus;

import java.util.HashMap;
import java.util.Map;

public class MessageClass implements IMessageClass{

    private final Map<String, IWidgetSetStatus> statusMap = new HashMap<>();

    @Override
    public void addStatusItem(String topic, IWidgetSetStatus setStatus) {

    }

    @Override
    public void clear() {
        statusMap.clear();
    }
}
