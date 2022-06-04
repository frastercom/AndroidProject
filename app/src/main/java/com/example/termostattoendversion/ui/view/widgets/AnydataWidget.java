package com.example.termostattoendversion.ui.view.widgets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.termostattoendversion.R;
import com.example.termostattoendversion.ui.jobs.json.JsonStatusMessage;
import com.example.termostattoendversion.ui.jobs.json.JsonWidgetMessage;

public class AnydataWidget {

    public static View getWidget(LayoutInflater inflater, JsonWidgetMessage jsonWidgetMessage, JsonStatusMessage status, ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.anydata_widget, viewGroup, false);
        TextView name = view.findViewById(R.id.widget_anydata_name);
        TextView statusText = view.findViewById(R.id.widget_anydata_status);
        if (status != null && status.getStatus() != null) {
            statusText.setText(status.getStatus().toString());
        }
        name.setText(jsonWidgetMessage.getDescr());
        return view;
    }
}
