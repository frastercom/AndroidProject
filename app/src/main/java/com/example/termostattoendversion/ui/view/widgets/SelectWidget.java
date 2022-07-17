package com.example.termostattoendversion.ui.view.widgets;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.termostattoendversion.R;
import com.example.termostattoendversion.ui.jobs.json.JsonStatusMessage;
import com.example.termostattoendversion.ui.jobs.json.JsonWidgetMessage;
import com.example.termostattoendversion.ui.jobs.mqtt.MqttConnection;
import com.example.termostattoendversion.ui.jobs.statics.ISetStatus;
import com.example.termostattoendversion.ui.jobs.statics.StaticsStatus;

import org.json.JSONException;

public class SelectWidget implements ISetStatus {

    private Button status;
    private int index = 0;
    private String[] array;

    @Override
    public void setStatus(JsonStatusMessage status) {
        Log.d("STATUS", "Status Select Widget>>> " + status.getStatus());
        index = Integer.parseInt(status.getStatus());
        this.status.setText(array[index]);
    }

    @Override
    public void getWidget(RecyclerView.ViewHolder viewHolder, JsonWidgetMessage message) {
        TextView name = viewHolder.itemView.findViewById(R.id.widget_select_name);
        name.setText(message.getDescr());
        Log.d("SELECT WIDGET", "Select Widget>>>------------- " + message.getDescr());
        status = viewHolder.itemView.findViewById(R.id.widget_select_status);
        array = message.getOptions();
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;
                if (index >= array.length)
                    index = 0;
                status.setText(array[index]);
                MqttConnection.outputMessage(message.getTopic().concat("/control"), ((Integer) index).toString());
            }
        });
        StaticsStatus.add(message.getTopic(), this);
        viewHolder.itemView.findViewById(R.id.id_widget_anydata).setVisibility(View.GONE);
        viewHolder.itemView.findViewById(R.id.id_widget_chart).setVisibility(View.GONE);
        viewHolder.itemView.findViewById(R.id.id_widget_input).setVisibility(View.GONE);
        viewHolder.itemView.findViewById(R.id.switch_on_of).setVisibility(View.GONE);
        if (message.getStatus() instanceof JsonStatusMessage) {
            setStatus((JsonStatusMessage) message.getStatus());
        }
    }
}
