package com.example.termostattoendversion.ui.view.widgets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.termostattoendversion.R;
import com.example.termostattoendversion.ui.jobs.json.JsonStatusMessage;
import com.example.termostattoendversion.ui.jobs.json.JsonWidgetMessage;

public class SelectWidget {

    public static View getWidget(LayoutInflater inflater, JsonWidgetMessage jsonWidgetMessage, JsonStatusMessage status, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.select_widget, viewGroup, false);
    }
}
