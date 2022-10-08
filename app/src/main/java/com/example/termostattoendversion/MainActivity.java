package com.example.termostattoendversion;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        // если пользователь закрыл запрос на разрешение, не дав ответа, массив grantResults будет пустым
        if (requestCode >= 100 && requestCode <= 110 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // разрешение было предоставлено
            // выполните здесь необходимые операции для включения функциональности приложения, связанной с запрашиваемым разрешением
        } else {
            // разрешение не было предоставлено
            // выполните здесь необходимые операции для выключения функциональности приложения, связанной с запрашиваемым разрешением
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Доступы");
            builder.setMessage("Вы не установили доступ к функции, приложение будет закрыто");
            builder.setCancelable(true);
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // Кнопка ОК
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss(); // Отпускает диалоговое окно
                    MainActivity.this.finish();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            //finishAffinity();
        }
    }

    public boolean checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
            //Отображение запроса на разрешение
            try {
                ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
            } catch (Exception e) {
                Log.e("РАЗРЕШЕНИЕ: ", "Не удалось отобразить пользователю");
            }
            Log.d("РАЗРЕШЕНИЕ", "не предовставлено " + permission);
            return true;
        }
        Log.d("РАЗРЕШЕНИЕ", "предоставлено " + permission);
        return false;
    }
}