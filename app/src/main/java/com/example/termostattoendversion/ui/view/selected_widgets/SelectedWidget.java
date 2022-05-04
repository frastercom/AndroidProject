package com.example.termostattoendversion.ui.view.selected_widgets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.termostattoendversion.ui.jobs.json.JsonWidgetMessage;
import com.example.termostattoendversion.ui.view.widgets.AnydataWidget;
import com.example.termostattoendversion.ui.view.widgets.ChartWidget;
import com.example.termostattoendversion.ui.view.widgets.InputWidget;
import com.example.termostattoendversion.ui.view.widgets.SelectWidget;
import com.example.termostattoendversion.ui.view.widgets.ToggleWidget;

public class SelectedWidget {


    public static View getWidget(JsonWidgetMessage message, LayoutInflater inflater, ViewGroup viewGroup) {
        switch (ListWidgets.valueOf(message.getWidget())) {
            case select:
                return SelectWidget.getWidget(inflater, viewGroup);
            case input:
                return InputWidget.getWidget(inflater, viewGroup);
            case toggle:
                return ToggleWidget.getWidget(inflater, viewGroup);
            case chart:
                return ChartWidget.getWidget(inflater, viewGroup);
            case anydata:
                return AnydataWidget.getWidget(inflater, viewGroup);
        }
        return null;
    }
}
