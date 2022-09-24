package com.example.termostattoendversion.ui.view.selected_widgets;

import android.support.v7.widget.RecyclerView;

import com.example.termostattoendversion.ui.jobs.json.JsonWidgetMessage;
import com.example.termostattoendversion.ui.view.widgets.AnydataWidget;
import com.example.termostattoendversion.ui.view.widgets.ChartWidget;
import com.example.termostattoendversion.ui.view.widgets.InputWidget;
import com.example.termostattoendversion.ui.view.widgets.SelectWidget;
import com.example.termostattoendversion.ui.view.widgets.ToggleWidget;

public class SelectedWidget {

    public void viewWidget(RecyclerView.ViewHolder viewHolder, JsonWidgetMessage message) {
        switch (ListWidgets.valueOf(message.getWidget())) {
            case SELECT:
                new SelectWidget().getWidget(viewHolder, message);
                break;
            case INPUT:
                new InputWidget().getWidget(viewHolder, message);
                break;
            case TOGGLE:
                new ToggleWidget().getWidget(viewHolder, message);
                break;
            case CHART:
                new ChartWidget().getWidget(viewHolder, message);
                break;
            case ANYDATA:
                new AnydataWidget().getWidget(viewHolder, message);
                break;
        }
    }
}
