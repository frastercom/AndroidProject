package com.example.termostattoendversion.ui.view.widgets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.termostattoendversion.R;
import com.example.termostattoendversion.ui.jobs.json.JsonStatusMessage;

public class AnydataWidget {

    public static View getWidget(LayoutInflater inflater, JsonStatusMessage status, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.anydata_widget, viewGroup, false);
    }
}
