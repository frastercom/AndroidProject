package com.example.termostattoendversion.ui.jobs.message;

import com.example.termostattoendversion.ui.jobs.IClear;
import com.example.termostattoendversion.ui.view.widgets.IWidgetSetStatus;

public interface IMessageClass extends IClear {

    public void addStatusItem(String topic, IWidgetSetStatus setStatus);
}
