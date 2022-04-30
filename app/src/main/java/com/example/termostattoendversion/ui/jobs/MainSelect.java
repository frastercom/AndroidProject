package com.example.termostattoendversion.ui.jobs;

import android.support.v7.widget.RecyclerView;

import com.example.termostattoendversion.ui.jobs.json.JsonStatusMessage;
import com.example.termostattoendversion.ui.jobs.json.JsonWidgetMessage;

public class MainSelect {

    private final RecyclerView recyclerView;

    public MainSelect(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public void widgetMessage(JsonWidgetMessage jsonWidgetMessage) {
        recyclerView.getAdapter();
    }

    public void statusMessage(JsonStatusMessage jsonStatusMessage) {

    }
}
