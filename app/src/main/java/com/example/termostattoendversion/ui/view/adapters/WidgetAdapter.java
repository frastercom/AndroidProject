package com.example.termostattoendversion.ui.view.adapters;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.termostattoendversion.R;
import com.example.termostattoendversion.ui.jobs.json.JsonStatusMessage;
import com.example.termostattoendversion.ui.jobs.json.JsonWidgetMessage;
import com.example.termostattoendversion.ui.view.selected_widgets.SelectedWidget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WidgetAdapter extends RecyclerView.Adapter<WidgetAdapter.ItemViewHolder> {

    private final LayoutInflater inflater;
    private List<JsonWidgetMessage> component = new ArrayList<>();
    private Map<String, JsonStatusMessage> statusMap = new HashMap<>();

    public WidgetAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public void addStatus(String topic, JsonStatusMessage status) {
        statusMap.put(topic, status);
        notifyDataSetChanged();
    }

    public void addWidget(JsonWidgetMessage component) {
        this.component.add(component);
        notifyItemInserted(Integer.parseInt(component.getOrder()));
        Log.w("ADD", "component: " + component.getWidget() + " ; size: " + this.component.size());
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d("PRINT VIEW", "PRINT i:" + i);
//        return new ItemViewHolder(SelectedWidget.getWidget(component.get(n), inflater, viewGroup));
        return new ItemViewHolder(inflater.inflate(R.layout.recycle_view_contain, viewGroup, false), viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        Log.d("BIND VIEW", "BIND i:" + i);
//        itemViewHolder.view.
        itemViewHolder.view.addView(SelectedWidget.getWidget(component.get(i), statusMap.get(component.get(i).getTopic()),inflater, itemViewHolder.viewGroup));
    }

    @Override
    public int getItemCount() {
        return component.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout view;
        private ViewGroup viewGroup;

        public ItemViewHolder(View itemView, ViewGroup viewGroup) {
            super(itemView);
            view = itemView.findViewById(R.id.recycleLayout);
            this.viewGroup = viewGroup;
        }
    }
}
