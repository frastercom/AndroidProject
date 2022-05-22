package com.example.termostattoendversion.ui.view.selected_widgets;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.termostattoendversion.ui.jobs.json.JsonStatusMessage;
import com.example.termostattoendversion.ui.jobs.json.JsonWidgetMessage;
import com.example.termostattoendversion.ui.view.widgets.AnydataWidget;
import com.example.termostattoendversion.ui.view.widgets.ChartWidget;
import com.example.termostattoendversion.ui.view.widgets.InputWidget;
import com.example.termostattoendversion.ui.view.widgets.SelectWidget;
import com.example.termostattoendversion.ui.view.widgets.ToggleWidget;

public class SelectedWidget {


    public static View getWidget(JsonWidgetMessage message, JsonStatusMessage status, LayoutInflater inflater, ViewGroup viewGroup) {
        Log.e("MESSAGE WIDGET", message.getWidget());
        switch (ListWidgets.valueOf(message.getWidget())) {
            case select:
                Log.i("ADD", "widget: SELECT");
                return SelectWidget.getWidget(inflater, status, viewGroup);
            case input:
                Log.i("ADD", "widget: INPUT");
                return InputWidget.getWidget(inflater, status, viewGroup);
            case toggle:
                Log.i("ADD", "widget: TOGGLE");
                return ToggleWidget.getWidget(inflater, status, viewGroup);
            case chart:
                Log.i("ADD", "widget: CHART");
                return ChartWidget.getWidget(inflater, status, viewGroup);
            case anydata:
                Log.i("ADD", "widget: ANYDATA");
                return AnydataWidget.getWidget(inflater, status, viewGroup);
        }
        return null;
    }
}
