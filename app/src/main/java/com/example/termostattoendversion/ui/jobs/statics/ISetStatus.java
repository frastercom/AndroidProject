package com.example.termostattoendversion.ui.jobs.statics;

import android.support.v7.widget.RecyclerView;

import com.example.termostattoendversion.ui.jobs.json.JsonStatusMessage;
import com.example.termostattoendversion.ui.jobs.json.JsonWidgetMessage;

public interface ISetStatus {

    void setStatus(JsonStatusMessage status);

    void getWidget(RecyclerView.ViewHolder viewHolder, JsonWidgetMessage message);
}
