package com.example.termostattoendversion.ui.device;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProvider;

import com.example.termostattoendversion.AddingDeviceActivity;
import com.example.termostattoendversion.databinding.FragmentDeviceBinding;

public class DeviceFragment extends Fragment {

    private FragmentDeviceBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DeviceViewModel galleryViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DeviceViewModel.class);

        binding = FragmentDeviceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}