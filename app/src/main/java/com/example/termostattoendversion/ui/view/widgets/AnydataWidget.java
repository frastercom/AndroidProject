package com.example.termostattoendversion.ui.view.widgets;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.termostattoendversion.R;
import com.example.termostattoendversion.ui.jobs.json.JsonStatusMessage;
import com.example.termostattoendversion.ui.jobs.json.JsonWidgetMessage;
import com.example.termostattoendversion.ui.jobs.statics.ISetStatus;
import com.example.termostattoendversion.ui.jobs.statics.StaticsStatus;

public class AnydataWidget implements ISetStatus {

    private TextView statusText;

    @Override
    public void setStatus(JsonStatusMessage status) {
        statusText.setText(status.getStatus());
    }

    @Override
    public void getWidget(RecyclerView.ViewHolder viewHolder, JsonWidgetMessage message) {
        ImageView imageView = viewHolder.itemView.findViewById(R.id.widget_anydata_image);
        if (message.getIcon() == null) {
            imageView.setVisibility(View.GONE);
        } else if (message.getIcon().equals("thermometer")) {
            imageView.setImageResource(R.drawable.ic_temperature_icon);
        } else if (message.getIcon().equals("alarm-outline")) {
            imageView.setImageResource(R.drawable.ic_alarm_outline_icon);
        } else if (message.getIcon().equals("")) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.GONE);
        }
        TextView name = viewHolder.itemView.findViewById(R.id.widget_anydata_name);
        statusText = viewHolder.itemView.findViewById(R.id.widget_anydata_status);
        name.setText(message.getDescr());
        StaticsStatus.add(message.getTopic(), this);
        viewHolder.itemView.findViewById(R.id.id_widget_select).setVisibility(View.GONE);
        viewHolder.itemView.findViewById(R.id.id_widget_chart).setVisibility(View.GONE);
        viewHolder.itemView.findViewById(R.id.id_widget_input).setVisibility(View.GONE);
        viewHolder.itemView.findViewById(R.id.switch_on_of).setVisibility(View.GONE);
        if (message.getStatus() instanceof JsonStatusMessage) {
            setStatus((JsonStatusMessage) message.getStatus());
        }
    }
}
