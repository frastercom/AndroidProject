package com.example.termostattoendversion.ui.view.widgets;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Switch;

import com.example.termostattoendversion.R;
import com.example.termostattoendversion.ui.jobs.json.JsonStatusMessage;
import com.example.termostattoendversion.ui.jobs.json.JsonWidgetMessage;
import com.example.termostattoendversion.ui.jobs.mqtt.MqttConnection;
import com.example.termostattoendversion.ui.jobs.statics.ISetStatus;
import com.example.termostattoendversion.ui.jobs.statics.StaticsStatus;

public class ToggleWidget implements ISetStatus {

    private Switch switch_on_off;

    @Override
    public void setStatus(JsonStatusMessage status) {
        if (status.getStatus().equals("1")) {
            switch_on_off.setChecked(true);
        } else {
            switch_on_off.setChecked(false);
        }
    }

    @Override
    public void getWidget(RecyclerView.ViewHolder viewHolder, JsonWidgetMessage message) {
        switch_on_off = viewHolder.itemView.findViewById(R.id.switch_on_of);
        switch_on_off.setText(message.getDescr());
        switch_on_off.setVisibility(View.VISIBLE);
        switch_on_off.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked == false) {
                MqttConnection.outputMessage(message.getTopic().concat("/control"), "0");
            }
            else
            {
                MqttConnection.outputMessage(message.getTopic().concat("/control"), "1");
            }
        });

        StaticsStatus.add(message.getTopic(), this);
        viewHolder.itemView.findViewById(R.id.id_widget_anydata).setVisibility(View.GONE);
        viewHolder.itemView.findViewById(R.id.id_widget_chart).setVisibility(View.GONE);
        viewHolder.itemView.findViewById(R.id.id_widget_input).setVisibility(View.GONE);
        viewHolder.itemView.findViewById(R.id.id_widget_select).setVisibility(View.GONE);
        if (message.getStatus() instanceof JsonStatusMessage) {
            setStatus((JsonStatusMessage) message.getStatus());
        }
    }
}
