package com.example.termostattoendversion.ui.view.widgets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.termostattoendversion.R;

public class SelectWidget {

    public static View getWidget(LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.select_widget, viewGroup, false);
    }
}
