package com.example.termostattoendversion.ui.view.selected_widgets;

import android.support.v7.widget.RecyclerView;

import com.example.termostattoendversion.ui.jobs.json.JsonWidgetMessage;
import com.example.termostattoendversion.ui.view.widgets.AnydataWidget;
import com.example.termostattoendversion.ui.view.widgets.ChartWidget;
import com.example.termostattoendversion.ui.view.widgets.InputWidget;
import com.example.termostattoendversion.ui.view.widgets.SelectWidget;
import com.example.termostattoendversion.ui.view.widgets.ToggleWidget;

//выборка нужного виджета(компонента)
public class SelectedWidget {

    public void viewWidget(RecyclerView.ViewHolder viewHolder, JsonWidgetMessage message) {
        switch (ListWidgets.valueOf(message.getWidget())) {
            case select:
                new SelectWidget().getWidget(viewHolder, message);
                break;
            case input:
                new InputWidget().getWidget(viewHolder, message);
                break;
            case toggle:
                new ToggleWidget().getWidget(viewHolder, message);
                break;
            case chart:
                new ChartWidget().getWidget(viewHolder, message);
                break;
            case anydata:
                new AnydataWidget().getWidget(viewHolder, message);
                break;
        }
    }
}
