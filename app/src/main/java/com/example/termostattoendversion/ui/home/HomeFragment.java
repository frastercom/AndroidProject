package com.example.termostattoendversion.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProvider;

import com.example.termostattoendversion.MainActivity;
import com.example.termostattoendversion.databinding.FragmentHomeBinding;
import com.example.termostattoendversion.ui.jobs.device.Device;
import com.example.termostattoendversion.ui.jobs.mqtt.MqttConnection;
import com.example.termostattoendversion.ui.view.adapters.WidgetAdapter;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * активность для работы с виджетами (компонентами) с помощью recycleView
 */
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
        checkPermissions((MainActivity) inflater.getContext());
        mqttConnection();//пофиксить, проблема если разрешения нет, попробует ломануться (случай, когда разрешения не были запрошены)
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void mqttConnection() {
        if (MqttConnection.getWidgetAdapter() == null) {
            try {
                Device device = read();
                WidgetAdapter widgetAdapter = new WidgetAdapter();
                recyclerView.setAdapter(widgetAdapter);
                MqttConnection.setWidgetAdapter(widgetAdapter);
                MqttConnection.connectMqtt(getActivity(), recyclerView, device);
                Log.d("MQTT", "Создаем соединение");
            } catch (Exception e) {
                //заглушка
            }
        } else {
            Log.d("MQTT", "Используем существующее соединение");
            recyclerView.setAdapter(MqttConnection.getWidgetAdapter());
        }
    }

    private Device read() throws Exception {
        try {
            FileInputStream fis = getActivity().openFileInput("alice.csv");
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            String text = new String(bytes);
            if (text == null || text.isEmpty()) {
                throw new Exception("Данные устройства отсутствуют");
            }
            Log.d("DEVICE_LOAD", "Учетка устройсва загружена");
            return new Gson().fromJson(text, Device.class);
        } catch (IOException e) {
            Log.e("DEVICE_LOAD", "Произошла внутренняя ошибка чтения данных из хэш");
            throw new Exception("Данные отсутствуют или внутренняя ошибка");
        }
    }

    //проверка на доступы, запрос на разрешение доступа
    private void checkPermissions(MainActivity activity) {
        activity.checkPermission(Manifest.permission.INTERNET, 100);
        activity.checkPermission(Manifest.permission.READ_PHONE_STATE, 102);
        activity.checkPermission(Manifest.permission.ACCESS_NETWORK_STATE, 103);
        activity.checkPermission(Manifest.permission.WAKE_LOCK, 104);
        activity.checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION, 105);
        activity.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, 106);
        activity.checkPermission(Manifest.permission.CHANGE_NETWORK_STATE, 107);
        activity.checkPermission(Manifest.permission.CHANGE_WIFI_STATE, 108);
        activity.checkPermission(Manifest.permission.ACCESS_NETWORK_STATE, 109);
        activity.checkPermission(Manifest.permission.ACCESS_WIFI_STATE, 110);
    }
}