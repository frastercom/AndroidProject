package com.example.termostattoendversion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.support.design.widget.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

import com.example.termostattoendversion.databinding.ActivityMainBinding;

/**
 * Главный класс, который являеться точкой входа
 */
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    //стандартный метод определяющий работу меню и приложения
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_device, R.id.nav_help).setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //Вызов активности (вью), которое отвечает за добавление нового устройства
    public void addDevice(View view) {
        Intent intent = new Intent(this, AddingDeviceActivity.class);
        try {
            startActivity(intent);
        } catch (Exception e) {
            Log.d("ADDING DEVICE", "Окно добавления устройсва не открыто, внутренняя ошибка");
        }
    }

    //Вызов активности (вью), которое отвечает за добавление уже активированного устройства
    public void addLastDevice(View view) {
        Intent intent = new Intent(this, AddingDeviceLastActivity.class);
        try {
            startActivity(intent);
        } catch (Exception e) {
            Log.d("ADDING DEVICE", "Окно добавления уже добавлено устройсва не открыто, внутренняя ошибка");
        }
    }
}