package com.example.termostattoendversion.ui.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProvider;

import com.example.termostattoendversion.databinding.FragmentHomeBinding;
import com.example.termostattoendversion.ui.jobs.device.Device;
import com.example.termostattoendversion.ui.jobs.json.JsonWidgetMessage;
import com.example.termostattoendversion.ui.jobs.mqtt.MqttConnection;
import com.example.termostattoendversion.ui.view.adapters.WidgetAdapter;

import org.eclipse.paho.client.mqttv3.MqttException;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(binding.recyclerView.getContext()));
        if (MqttConnection.getWidgetAdapter() == null) {
            WidgetAdapter widgetAdapter = new WidgetAdapter();
            recyclerView.setAdapter(widgetAdapter);
            MqttConnection.setWidgetAdapter(widgetAdapter);
            MqttConnection.connectMqtt(getActivity(), recyclerView, new Device("test:test", "test"));
        } else {
            recyclerView.setAdapter(MqttConnection.getWidgetAdapter());
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}