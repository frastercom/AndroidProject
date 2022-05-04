package com.example.termostattoendversion.ui.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.termostattoendversion.R;
import com.example.termostattoendversion.ui.jobs.json.JsonWidgetMessage;
import com.example.termostattoendversion.ui.view.selected_widgets.SelectedWidget;

import java.util.ArrayList;
import java.util.List;

public class WidgetAdapter extends RecyclerView.Adapter<WidgetAdapter.ItemViewHolder> {

    private final LayoutInflater inflater;
    private List<JsonWidgetMessage> component = new ArrayList<>();

    public WidgetAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public void addWidget(JsonWidgetMessage component) {
        this.component.add(component);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ItemViewHolder(SelectedWidget.getWidget(component.get(i), inflater, viewGroup));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return component.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private View view;

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
