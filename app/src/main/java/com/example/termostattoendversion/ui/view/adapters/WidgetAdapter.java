package com.example.termostattoendversion.ui.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.termostattoendversion.R;
import com.example.termostattoendversion.ui.jobs.json.JsonWidgetMessage;
import com.example.termostattoendversion.ui.jobs.mqtt.MqttConnection;
import com.example.termostattoendversion.ui.jobs.statics.StaticsStatus;
import com.example.termostattoendversion.ui.view.selected_widgets.SelectedWidget;

import java.util.ArrayList;
import java.util.List;

public class WidgetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<JsonWidgetMessage> component = new ArrayList<>();

    public WidgetAdapter() {
        StaticsStatus.setWidgetAdapter(this);
    }

    public void addWidget(JsonWidgetMessage component) {
        MqttConnection.topic(component.getTopic());
        this.component.add(component);
        notifyItemInserted(Integer.parseInt(component.getOrder()));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RecyclerView.ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_widget, viewGroup, false)) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        new SelectedWidget().viewWidget(viewHolder, component.get(i));
    }

    @Override
    public int getItemCount() {
        return component.size();
    }

    public List<JsonWidgetMessage> getComponent() {
        return component;
    }
}
